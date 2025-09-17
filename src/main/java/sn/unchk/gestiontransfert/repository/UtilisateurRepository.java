package sn.unchk.gestiontransfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.TransactionModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<UtilisateurModel, Long> {
    Optional<UtilisateurModel> findByTelephone(String telephone);

    @Query("SELECT a FROM AbonnementModel a WHERE a.utilisateur.telephone = :telephone")
    List<AbonnementModel> findAbonnementsByUtilisateurTelephone(String telephone);

    @Query("SELECT t FROM TransactionModel t WHERE t.expediteur.telephone = :telephone OR t.destinataire.telephone = :telephone")
    List<TransactionModel> findTransactionsByUtilisateurTelephone(String telephone);
}


