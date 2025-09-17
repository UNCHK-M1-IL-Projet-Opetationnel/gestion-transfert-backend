package sn.unchk.gestiontransfert.model;

import jakarta.persistence.*;
import sn.unchk.gestiontransfert.model.enumeration.TypeTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
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

    // Getters
    public BigDecimal getMontant() {
        return montant;
    }
    public BigDecimal getFrais() {
        return frais;
    }
    public TypeTransaction getType() {
        return type;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public UtilisateurModel getDestinataire() {
        return destinataire;
    }
    public UtilisateurModel getExpediteur() {
        return expediteur;
    }

    // Setters
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    public void setFrais(BigDecimal frais) {
        this.frais = frais;
    }
    public void setType(TypeTransaction type) {
        this.type = type;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setDestinataire(UtilisateurModel destinataire) {
        this.destinataire = destinataire;
    }
    public void setExpediteur(UtilisateurModel expediteur) {
        this.expediteur = expediteur;
    }
}
