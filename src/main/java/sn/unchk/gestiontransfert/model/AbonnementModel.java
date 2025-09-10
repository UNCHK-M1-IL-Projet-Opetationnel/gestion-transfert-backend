package sn.unchk.gestiontransfert.model;

import jakarta.persistence.*;
import lombok.Data;
import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "abonnement")
@Data
public class AbonnementModel extends AbstractModel {

    @Enumerated(EnumType.STRING)
    private TypeAbonnement type;

    @Column(precision = 10, scale = 2)
    private BigDecimal cout;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String avantages;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private UtilisateurModel utilisateur;
}
