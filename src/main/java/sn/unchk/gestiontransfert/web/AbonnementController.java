package sn.unchk.gestiontransfert.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sn.unchk.gestiontransfert.model.AbonnementModel;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;
import sn.unchk.gestiontransfert.service.AbonnementService;
import sn.unchk.gestiontransfert.service.dto.response.AbonnementDto;
import sn.unchk.gestiontransfert.web.response.PageResponse;

@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {

    private final AbonnementService abonnementService;

    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }

    // Activer un abonnement
    @PostMapping("/activer")
    public AbonnementDto activerAbonnement(
            @AuthenticationPrincipal UtilisateurModel utilisateur,
            @RequestParam TypeAbonnement type
    ) {
        return abonnementService.activerAbonnement(utilisateur, type);
    }

    // Tous les abonnements (paginés, tri par updatedAt desc)
    @GetMapping
    public PageResponse<AbonnementDto> getAllAbonnements(Pageable pageable) {
        Page<AbonnementDto> page = abonnementService.getAllAbonnements(pageable);
        return PageResponse.from(page);
    }

    // Tous les abonnements actifs
    @GetMapping("/actifs")
    public PageResponse<AbonnementDto> getAbonnementsActifs(Pageable pageable) {
        Page<AbonnementDto> page = abonnementService.getAbonnementsActifs(pageable);
        return PageResponse.from(page);
    }

    // Abonnements actifs de l’utilisateur connecté
    @GetMapping("/me/actifs")
    public PageResponse<AbonnementDto> getMyAbonnementsActifs(
            @AuthenticationPrincipal UtilisateurModel utilisateur,
            Pageable pageable
    ) {
        Page<AbonnementDto> page = abonnementService.getAbonnementsActifsByUtilisateur(utilisateur, pageable);
        return PageResponse.from(page);
    }

    // Détails d’un abonnement spécifique
    @GetMapping("/{id}")
    public AbonnementDto getAbonnementById(@PathVariable Long id) {
        return abonnementService.getAbonnementById(id);
    }
}
