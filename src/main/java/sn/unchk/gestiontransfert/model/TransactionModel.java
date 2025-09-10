package sn.unchk.gestiontransfert.model;

import jakarta.persistence.*;
import lombok.Data;
import sn.unchk.gestiontransfert.model.enumeration.TypeTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class TransactionModel extends AbstractModel {

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montant;

    @Column(precision = 10, scale = 2)
    private BigDecimal frais;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private UtilisateurModel destinataire;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private UtilisateurModel expediteur;
}
