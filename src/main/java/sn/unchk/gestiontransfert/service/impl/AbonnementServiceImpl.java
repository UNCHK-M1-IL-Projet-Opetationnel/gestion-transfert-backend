package sn.unchk.gestiontransfert.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;
import sn.unchk.gestiontransfert.repository.AbonnementRepository;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;
import sn.unchk.gestiontransfert.service.AbonnementService;
import sn.unchk.gestiontransfert.service.dto.response.AbonnementDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AbonnementServiceImpl implements AbonnementService {

    private final AbonnementRepository abonnementRepository;
    private final UtilisateurRepository utilisateurRepository;

    public AbonnementServiceImpl(AbonnementRepository abonnementRepository, UtilisateurRepository utilisateurRepository) {
        this.abonnementRepository = abonnementRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private AbonnementDto toDto(AbonnementModel abonnement) {
        AbonnementDto dto = new AbonnementDto();
        dto.setId(abonnement.getId());
        dto.setType(abonnement.getType());
        dto.setCout(abonnement.getCout());
        dto.setAvantages(abonnement.getAvantages());
        dto.setDateDebut(abonnement.getDateDebut());
        dto.setDateFin(abonnement.getDateFin());
        return dto;
    }

    @Override
    public AbonnementDto activerAbonnement(UtilisateurModel utilisateur, TypeAbonnement type) {
        AbonnementModel abonnement = new AbonnementModel();
        abonnement.setUtilisateur(utilisateur);
        abonnement.setType(type);
        abonnement.setDateDebut(LocalDate.now());

        BigDecimal cout;
        switch (type) {
            case STANDARD -> {
                cout = BigDecimal.valueOf(1000);
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.8%");
            }
            case PREMIUM -> {
                cout = BigDecimal.valueOf(2000);
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.5%");
            }
            case VIP -> {
                cout = BigDecimal.valueOf(3000);
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.2%");
            }
            default -> throw new IllegalArgumentException("Type d’abonnement inconnu : " + type);
        }

        if (utilisateur.getSolde().compareTo(cout) < 0) {
            throw new RuntimeException("Solde insuffisant pour activer l’abonnement " + type);
        }

        utilisateur.setSolde(utilisateur.getSolde().subtract(cout));
        utilisateurRepository.save(utilisateur);
        abonnement.setCout(cout);

        AbonnementModel saved = abonnementRepository.save(abonnement);
        return toDto(saved);
    }

    @Override
    public Page<AbonnementDto> getAllAbonnements(Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findAll(sorted).map(this::toDto);
    }

    @Override
    public Page<AbonnementDto> getAbonnementsActifs(Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findByDateFinAfter(LocalDate.now(), sorted).map(this::toDto);
    }

    @Override
    public Page<AbonnementDto> getAbonnementsActifsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findByUtilisateurAndDateFinAfter(utilisateur, LocalDate.now(), sorted)
                .map(this::toDto);
    }

    @Override
    public AbonnementDto getAbonnementById(Long id) {
        AbonnementModel abonnement = abonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé avec l’ID : " + id));
        return toDto(abonnement);
    }
}
