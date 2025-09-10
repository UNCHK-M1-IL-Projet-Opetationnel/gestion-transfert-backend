package sn.unchk.gestiontransfert.service;

import sn.unchk.gestiontransfert.model.UtilisateurModel;

import java.util.Optional;

public interface UtilisateurService {
    Optional<UtilisateurModel> getUserProfile(String telephone);
}
