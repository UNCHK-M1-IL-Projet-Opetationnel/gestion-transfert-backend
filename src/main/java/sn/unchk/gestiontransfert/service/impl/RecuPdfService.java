package sn.unchk.gestiontransfert.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.awt.Color;


@Service
public class RecuPdfService {

    public ByteArrayInputStream generatePdf(TransactionDto transaction) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Ajouter un titre en rouge
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.RED);
            Paragraph title = new Paragraph("Reçu de Transaction", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Ajouter les détails de la transaction
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
            document.add(new Paragraph("ID: " + transaction.getId(), contentFont));
            document.add(new Paragraph("Type: " + transaction.getType(), contentFont));
            document.add(new Paragraph("Montant: " + transaction.getMontant() + " FCFA", contentFont));
            document.add(new Paragraph("Frais: " + transaction.getFrais() + " FCFA", contentFont));
            document.add(new Paragraph("Date: " + transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), contentFont));
            document.add(new Paragraph("Expéditeur: " + transaction.getExpediteurTelephone(), contentFont));
            document.add(new Paragraph("Destinataire: " + transaction.getDestinataireTelephone(), contentFont));

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
