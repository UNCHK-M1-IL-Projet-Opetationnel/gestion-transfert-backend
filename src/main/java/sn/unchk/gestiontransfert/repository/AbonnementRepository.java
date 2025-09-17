package sn.unchk.gestiontransfert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;

import java.time.LocalDate;
import java.util.List;

public interface AbonnementRepository extends JpaRepository<AbonnementModel, Long> {

    // Tous les abonnements actifs (dateFin >= aujourd'hui)
    Page<AbonnementModel> findByDateFinAfter(LocalDate now, Pageable pageable);

    // Abonnements actifs d’un utilisateur donné
    Page<AbonnementModel> findByUtilisateurAndDateFinAfter(UtilisateurModel utilisateur, LocalDate now, Pageable pageable);

    List<AbonnementModel> findByUtilisateurAndDateFinAfter(UtilisateurModel utilisateur, LocalDate date);

    AbonnementModel getAbonnementById(Long id);

}
