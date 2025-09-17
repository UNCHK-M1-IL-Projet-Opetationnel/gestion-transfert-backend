package sn.unchk.gestiontransfert.service.dto.request;

import java.math.BigDecimal;

public class TransferRequestDto {
    private String destinataireTelephone;
    private BigDecimal montant;

    public String getDestinataireTelephone() {
        return destinataireTelephone;
    }

    public void setDestinataireTelephone(String destinataireTelephone) {
        this.destinataireTelephone = destinataireTelephone;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
}
