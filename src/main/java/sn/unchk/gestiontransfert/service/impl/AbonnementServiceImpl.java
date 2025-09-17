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
import sn.unchk.gestiontransfert.service.AbonnementService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AbonnementServiceImpl implements AbonnementService {

    private final AbonnementRepository abonnementRepository;

    public AbonnementServiceImpl(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    @Override
    public AbonnementModel activerAbonnement(UtilisateurModel utilisateur, TypeAbonnement type) {
        AbonnementModel abonnement = new AbonnementModel();
        abonnement.setUtilisateur(utilisateur);
        abonnement.setType(type);
        abonnement.setDateDebut(LocalDate.now());

        switch (type) {
            case GRATUIT -> {
                abonnement.setCout(BigDecimal.ZERO);
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Accès limité aux services de base (frais normaux 1%)");
            }
            case STANDARD -> {
                abonnement.setCout(BigDecimal.valueOf(1000));
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.8%");
            }
            case PREMIUM -> {
                abonnement.setCout(BigDecimal.valueOf(2000));
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.5%");
            }
            case VIP -> {
                abonnement.setCout(BigDecimal.valueOf(3000));
                abonnement.setDateFin(LocalDate.now().plusMonths(1));
                abonnement.setAvantages("Frais de transfert réduits à 0.2%");
            }
        }

        return abonnementRepository.save(abonnement);
    }

    @Override
    public Page<AbonnementModel> getAllAbonnements(Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findAll(sorted);
    }

    @Override
    public Page<AbonnementModel> getAbonnementsActifs(Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findByDateFinAfter(LocalDate.now(), sorted);
    }

    @Override
    public Page<AbonnementModel> getAbonnementsActifsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable) {
        Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt"));
        return abonnementRepository.findByUtilisateurAndDateFinAfter(utilisateur, LocalDate.now(), sorted);
    }

    @Override
    public AbonnementModel getAbonnementById(Long id) {
        return abonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé avec l’ID : " + id));
    }

}
