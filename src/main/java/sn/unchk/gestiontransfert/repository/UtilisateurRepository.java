package sn.unchk.gestiontransfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.gestiontransfert.model.UtilisateurModel;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<UtilisateurModel, Long> {
    Optional<UtilisateurModel> findByTelephone(String telephone);
}

