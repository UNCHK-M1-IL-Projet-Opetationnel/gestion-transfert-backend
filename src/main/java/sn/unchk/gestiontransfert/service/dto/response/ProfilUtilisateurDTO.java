package sn.unchk.gestiontransfert.service.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ProfilUtilisateurDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private BigDecimal solde;
    private String qrcode;

    private List<TransactionDto> transactions;
    private List<AbonnementDto> abonnementsActifs;

    // Getters
    public Integer getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getTelephone() {
        return telephone;
    }
    public BigDecimal getSolde() {
        return solde;
    }
    public String getQrcode() {
        return qrcode;
    }
    public List<TransactionDto> getTransactions() {
        return transactions;
    }
    public List<AbonnementDto> getAbonnementsActifs() {
        return abonnementsActifs;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
    public void setAbonnementsActifs(List<AbonnementDto> abonnementsActifs) {
        this.abonnementsActifs = abonnementsActifs;
    }
}
