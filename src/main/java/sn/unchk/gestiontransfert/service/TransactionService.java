package sn.unchk.gestiontransfert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.service.dto.request.TransferRequestDto;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;

import java.math.BigDecimal;

public interface TransactionService {

    //  Transfert entre utilisateurs
    TransactionDto effectuerTransfert(UtilisateurModel expediteur, TransferRequestDto request);

    //  Dépôt d’argent sur le compte de l’utilisateur connecté
    TransactionDto effectuerDepot(UtilisateurModel utilisateur, BigDecimal montant);

    //  Retrait d’argent sur le compte de l’utilisateur connecté
    TransactionDto effectuerRetrait(UtilisateurModel utilisateur, BigDecimal montant);

    //  Détails d’une transaction
    TransactionDto getTransactionById(Long id);

    //  Liste paginée de toutes les transactions
    Page<TransactionDto> getAllTransactions(Pageable pageable);

    //  Transactions d’un utilisateur donné (par entité)
    Page<TransactionDto> getTransactionsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable);

    //  Transactions d’un utilisateur donné (par téléphone)
    Page<TransactionDto> getTransactionsByTelephone(String telephone, Pageable pageable);
}
