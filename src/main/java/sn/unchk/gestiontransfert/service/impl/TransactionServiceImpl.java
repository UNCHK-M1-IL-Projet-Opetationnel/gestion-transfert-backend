package sn.unchk.gestiontransfert.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.gestiontransfert.model.TransactionModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.TypeTransaction;
import sn.unchk.gestiontransfert.repository.AbonnementRepository;
import sn.unchk.gestiontransfert.repository.TransactionRepository;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;
import sn.unchk.gestiontransfert.service.TransactionService;
import sn.unchk.gestiontransfert.service.dto.request.TransferRequestDto;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AbonnementRepository abonnementRepository;

    private static final BigDecimal TAUX_FRAIS = new BigDecimal("0.01"); // 1%

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UtilisateurRepository utilisateurRepository,
                                  AbonnementRepository abonnementRepository) {
        this.transactionRepository = transactionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.abonnementRepository = abonnementRepository;
    }

    private BigDecimal calculerFrais(BigDecimal montant) {
        return montant.multiply(TAUX_FRAIS).setScale(2, RoundingMode.HALF_UP);
    }

    private TransactionDto toDto(TransactionModel transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId().intValue());
        dto.setMontant(transaction.getMontant());
        dto.setFrais(transaction.getFrais());
        dto.setType(transaction.getType());
        dto.setDate(transaction.getDate());
        dto.setExpediteurTelephone(transaction.getExpediteur() != null ? transaction.getExpediteur().getTelephone() : null);
        dto.setDestinataireTelephone(transaction.getDestinataire() != null ? transaction.getDestinataire().getTelephone() : null);
        return dto;
    }

    private BigDecimal getTauxFrais(UtilisateurModel utilisateur) {
        // Vérifier si l’utilisateur a un abonnement actif
        return abonnementRepository.findByUtilisateurAndDateFinAfter(utilisateur, LocalDate.now())
                .stream()
                .findFirst()
                .map(abonnement -> {
                    switch (abonnement.getType()) {
                        case STANDARD -> { return new BigDecimal("0.008"); } // 0.8%
                        case PREMIUM  -> { return new BigDecimal("0.005"); } // 0.5%
                        case VIP      -> { return new BigDecimal("0.002"); } // 0.2%
                        default       -> { return new BigDecimal("0.01"); }  // par défaut 1%
                    }
                })
                .orElse(new BigDecimal("0.01")); // aucun abonnement actif → 1%
    }

    private BigDecimal calculerFrais(BigDecimal montant, UtilisateurModel utilisateur) {
        BigDecimal taux = getTauxFrais(utilisateur);
        return montant.multiply(taux).setScale(2, RoundingMode.HALF_UP);
    }

    //  Transfert entre utilisateurs
    @Override
    @Transactional
    public TransactionDto effectuerTransfert(UtilisateurModel expediteur, TransferRequestDto request) {
        if (request.getMontant() == null || request.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant du transfert doit être positif");
        }

        UtilisateurModel destinataire = utilisateurRepository.findByTelephone(request.getDestinataireTelephone())
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));

        BigDecimal frais = calculerFrais(request.getMontant(), expediteur);
        BigDecimal montantTotal = request.getMontant().add(frais);

        if (expediteur.getSolde().compareTo(montantTotal) < 0) {
            throw new RuntimeException("Solde insuffisant");
        }

        // Créer transaction
        TransactionModel transaction = new TransactionModel();
        transaction.setMontant(request.getMontant());
        transaction.setFrais(frais);
        transaction.setType(TypeTransaction.TRANSFERT);
        transaction.setDate(LocalDateTime.now());
        transaction.setExpediteur(expediteur);
        transaction.setDestinataire(destinataire);

        // Mise à jour des soldes
        expediteur.setSolde(expediteur.getSolde().subtract(montantTotal));
        destinataire.setSolde(destinataire.getSolde().add(request.getMontant()));

        utilisateurRepository.save(expediteur);
        utilisateurRepository.save(destinataire);
        transactionRepository.save(transaction);

        return toDto(transaction);
    }


    //  Dépôt d’argent
    @Override
    @Transactional
    public TransactionDto effectuerDepot(UtilisateurModel utilisateur, BigDecimal montant) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant du dépôt doit être positif");
        }

        utilisateur.setSolde(utilisateur.getSolde().add(montant));
        utilisateurRepository.save(utilisateur);

        TransactionModel transaction = new TransactionModel();
        transaction.setMontant(montant);
        transaction.setFrais(BigDecimal.ZERO);
        transaction.setType(TypeTransaction.DEPOT);
        transaction.setDate(LocalDateTime.now());
        transaction.setDestinataire(utilisateur);

        transactionRepository.save(transaction);

        return toDto(transaction);
    }

    //  Retrait d’argent
    @Override
    @Transactional
    public TransactionDto effectuerRetrait(UtilisateurModel utilisateur, BigDecimal montant) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant du retrait doit être positif");
        }
        if (utilisateur.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant pour le retrait");
        }

        utilisateur.setSolde(utilisateur.getSolde().subtract(montant));
        utilisateurRepository.save(utilisateur);

        TransactionModel transaction = new TransactionModel();
        transaction.setMontant(montant);
        transaction.setFrais(BigDecimal.ZERO);
        transaction.setType(TypeTransaction.RETRAIT);
        transaction.setDate(LocalDateTime.now());
        transaction.setExpediteur(utilisateur);

        transactionRepository.save(transaction);

        return toDto(transaction);
    }

    // ✅ Détails d’une transaction
    @Override
    public TransactionDto getTransactionById(Long id) {
        TransactionModel transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée"));
        return toDto(transaction);
    }

    //  Liste de toutes les transactions
    @Override
    public Page<TransactionDto> getAllTransactions(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );
        return transactionRepository.findAll(sortedPageable).map(this::toDto);
    }

    //  Transactions par utilisateur
    @Override
    public Page<TransactionDto> getTransactionsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );
        return transactionRepository.findByExpediteurOrDestinataire(utilisateur, utilisateur, sortedPageable)
                .map(this::toDto);
    }

    //  Transactions par téléphone
    @Override
    public Page<TransactionDto> getTransactionsByTelephone(String telephone, Pageable pageable) {
        UtilisateurModel utilisateur = utilisateurRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return getTransactionsByUtilisateur(utilisateur, pageable);
    }
}
