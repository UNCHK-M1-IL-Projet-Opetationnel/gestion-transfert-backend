package sn.unchk.gestiontransfert.service.dto.response;

import sn.unchk.gestiontransfert.model.enumeration.TypeTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto {
    private Integer id;
    private BigDecimal montant;
    private BigDecimal frais;
    private TypeTransaction type;
    private LocalDateTime date;
    private String expediteurTelephone;
    private String destinataireTelephone;

    // Getters
    public Integer getId() {
        return id;
    }
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
    public String getExpediteurTelephone() {
        return expediteurTelephone;
    }
    public String getDestinataireTelephone() {
        return destinataireTelephone;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
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
    public void setExpediteurTelephone(String expediteurTelephone) {
        this.expediteurTelephone = expediteurTelephone;
    }
    public void setDestinataireTelephone(String destinataireTelephone) {
        this.destinataireTelephone = destinataireTelephone;
    }
}
