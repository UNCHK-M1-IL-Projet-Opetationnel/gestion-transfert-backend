package sn.unchk.gestiontransfert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;
import sn.unchk.gestiontransfert.service.dto.response.AbonnementDto;

public interface AbonnementService {

    AbonnementDto activerAbonnement(UtilisateurModel utilisateur, TypeAbonnement type);

    Page<AbonnementDto> getAllAbonnements(Pageable pageable);

    Page<AbonnementDto> getAbonnementsActifs(Pageable pageable);

    Page<AbonnementDto> getAbonnementsActifsByUtilisateur(UtilisateurModel utilisateur, Pageable pageable);

    AbonnementDto getAbonnementById(Long id);
}
