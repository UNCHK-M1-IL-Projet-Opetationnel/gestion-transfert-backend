package sn.unchk.gestiontransfert.service;

import sn.unchk.gestiontransfert.service.dto.response.ProfilUtilisateurDTO;

public interface UtilisateurService {

    /**
     * Récupère le profil de l’utilisateur connecté à partir du JWT.
     *
     * @return ProfilUtilisateurDTO contenant les informations de l’utilisateur
     */
    ProfilUtilisateurDTO getProfilUtilisateurConnecte();
}
