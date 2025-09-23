package sn.unchk.gestiontransfert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sn.unchk.gestiontransfert.model.enumeration.Role;
import sn.unchk.gestiontransfert.model.enumeration.Statut;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
public class UtilisateurModel extends AbstractModel implements UserDetails {

    public Collection<? extends GrantedAuthority> getAuthorities() {

        return role != null ?
                List.of(new SimpleGrantedAuthority(role.name())) :
                List.of();
    }

    @Override
    public String getUsername() {
        return telephone;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    private String nom;
    private String prenom;
    private String email;

    @Column(unique = true, nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    private byte[] photo;

    private String numeroPiece;

    @Lob
    private byte[] photoPiece;

    @Column(precision = 10, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;

    private LocalDate dateCreation = LocalDate.now();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String qrcode;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.ACTIF;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AbonnementModel> abonnements;

    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TransactionModel> transactionsEnvoyees;

    @OneToMany(mappedBy = "destinataire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TransactionModel> transactionsRecues;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public byte[] getPhotoPiece() {
        return photoPiece;
    }

    public void setPhotoPiece(byte[] photoPiece) {
        this.photoPiece = photoPiece;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<AbonnementModel> getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(List<AbonnementModel> abonnements) {
        this.abonnements = abonnements;
    }

    public List<TransactionModel> getTransactionsEnvoyes() {
        return transactionsEnvoyees;
    }

    public void setTransactionsEnvoyes(List<TransactionModel> transactionsEnvoyes) {
        this.transactionsEnvoyees = transactionsEnvoyes;
    }

    public List<TransactionModel> getTransactionsRecues() {
        return transactionsRecues;
    }

    public void setTransactionsRecues(List<TransactionModel> transactionsRecues) {
        this.transactionsRecues = transactionsRecues;
    }
}
