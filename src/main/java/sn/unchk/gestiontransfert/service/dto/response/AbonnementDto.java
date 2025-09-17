package sn.unchk.gestiontransfert.service.dto.response;

import sn.unchk.gestiontransfert.model.enumeration.TypeAbonnement;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AbonnementDto {
    private Integer id;
    private TypeAbonnement type;
    private BigDecimal cout;
    private String avantages;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Getters
    public Integer getId() {
        return id;
    }
    public TypeAbonnement getType() {
        return type;
    }
    public BigDecimal getCout() {
        return cout;
    }
    public String getAvantages() {
        return avantages;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    public void setType(TypeAbonnement type) {
        this.type = type;
    }
    public void setCout(BigDecimal cout) {
        this.cout = cout;
    }
    public void setAvantages(String avantages) {
        this.avantages = avantages;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
