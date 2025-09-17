package sn.unchk.gestiontransfert.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.TransactionModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;
import sn.unchk.gestiontransfert.service.UtilisateurService;
import sn.unchk.gestiontransfert.service.dto.response.AbonnementDto;
import sn.unchk.gestiontransfert.service.dto.response.ProfilUtilisateurDTO;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public ProfilUtilisateurDTO getProfilUtilisateurConnecte() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String telephone = auth.getName();

        // 1. Charger l'utilisateur
        UtilisateurModel user = utilisateurRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 2. Charger les abonnements
        List<AbonnementModel> abonnements = utilisateurRepository.findAbonnementsByUtilisateurTelephone(telephone);

        // 3. Charger les transactions
        List<TransactionModel> transactions = utilisateurRepository.findTransactionsByUtilisateurTelephone(telephone);

        // Mapper en DTO
        ProfilUtilisateurDTO dto = new ProfilUtilisateurDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setSolde(user.getSolde());
        dto.setQrcode(user.getQrcode());

        // Mapper abonnements actifs
        List<AbonnementDto> abonnementsActifs = abonnements.stream()
                .filter(ab -> ab.getDateFin() == null || ab.getDateFin().isAfter(LocalDate.now()))
                .map(this::mapToAbonnementDto)
                .collect(Collectors.toList());
        dto.setAbonnementsActifs(abonnementsActifs);

        // Trier et mapper transactions
        List<TransactionDto> transactionsTriees = transactions.stream()
                .sorted(Comparator.comparing(TransactionModel::getDate).reversed())
                .map(this::mapToTransactionDto)
                .collect(Collectors.toList());
        dto.setTransactions(transactionsTriees);

        return dto;
    }

    private AbonnementDto mapToAbonnementDto(AbonnementModel model) {
        AbonnementDto dto = new AbonnementDto();
        dto.setId(model.getId());
        dto.setType(model.getType());
        dto.setCout(model.getCout());
        dto.setAvantages(model.getAvantages());
        dto.setDateDebut(model.getDateDebut());
        dto.setDateFin(model.getDateFin());
        return dto;
    }

    private TransactionDto mapToTransactionDto(TransactionModel model) {
        TransactionDto dto = new TransactionDto();
        dto.setId(model.getId());
        dto.setMontant(model.getMontant());
        dto.setFrais(model.getFrais());
        dto.setType(model.getType());
        dto.setDate(model.getDate());
        dto.setExpediteurTelephone(model.getExpediteur() != null ? model.getExpediteur().getTelephone() : null);
        dto.setDestinataireTelephone(model.getDestinataire() != null ? model.getDestinataire().getTelephone() : null);
        return dto;
    }
}
