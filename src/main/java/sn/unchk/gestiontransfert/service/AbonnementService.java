package sn.unchk.gestiontransfert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;

public interface AbonnementService {

    AbonnementModel activerAbonnement(UtilisateurModel utilisateur, TypeAbonnement type);

    Page<AbonnementModel> getAllAbonnements(Pageable pageable);

    Page<AbonnementModel> getAbonnementsActifs(Pageable pageable);

    Page<AbonnementModel> getAbonnementsActifsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable);

    AbonnementModel getAbonnementById(Long id);
}
