package sn.unchk.gestiontransfert.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.gestiontransfert.service.dto.response.ProfilUtilisateurDTO;
import sn.unchk.gestiontransfert.service.impl.UtilisateurServiceImpl;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurServiceImpl utilisateurService;

    public UtilisateurController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/me/profil")
    public ResponseEntity<ProfilUtilisateurDTO> getProfil() {
        ProfilUtilisateurDTO profil = utilisateurService.getProfilUtilisateurConnecte();
        return ResponseEntity.ok(profil);
    }
}
