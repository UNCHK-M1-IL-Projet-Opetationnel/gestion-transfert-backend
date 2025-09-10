package sn.unchk.gestiontransfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.gestiontransfert.model.AbonnementModel;

import java.util.List;

public interface AbonnementRepository extends JpaRepository<AbonnementModel, Long> {
}
