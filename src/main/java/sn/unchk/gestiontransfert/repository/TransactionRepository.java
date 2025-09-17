package sn.unchk.gestiontransfert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.gestiontransfert.model.TransactionModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    Page<TransactionModel> findByExpediteurOrDestinataire(UtilisateurModel expediteur, UtilisateurModel destinataire, Pageable pageable);
    TransactionModel findById (long id);
}
