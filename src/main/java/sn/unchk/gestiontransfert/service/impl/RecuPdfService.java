package sn.unchk.gestiontransfert.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;

import java.awt.*;
import java.io.ByteArrayOutputStream;

@Service
public class RecuPdfService {

    public byte[] genererRecu(TransactionDto transaction) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A5); // format ticket

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // ==== En-tête ====
            Paragraph header = new Paragraph("REÇU DE TRANSACTION",
                    new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLACK));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph separator = new Paragraph("--------------------------------------\n");
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);

            // ==== Tableau infos ====
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            addRow(table, "ID Transaction", String.valueOf(transaction.getId()));
            addRow(table, "Type", String.valueOf(transaction.getType()));
            addRow(table, "Montant", transaction.getMontant() + " FCFA");
            addRow(table, "Frais", transaction.getFrais() + " FCFA");
            addRow(table, "Date", transaction.getDate().toString());

            if (transaction.getExpediteurTelephone() != null) {
                addRow(table, "Expéditeur", transaction.getExpediteurTelephone());
            }
            if (transaction.getDestinataireTelephone() != null) {
                addRow(table, "Destinataire", transaction.getDestinataireTelephone());
            }

            document.add(table);

            // ==== Footer ====
            Paragraph footerSep = new Paragraph("--------------------------------------\n");
            footerSep.setAlignment(Element.ALIGN_CENTER);
            document.add(footerSep);

            Paragraph footer = new Paragraph("Merci d’avoir utilisé notre service.",
                    new Font(Font.HELVETICA, 10, Font.ITALIC, Color.DARK_GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }

    // 🔹 Méthode utilitaire pour ajouter une ligne clé/valeur
    private void addRow(PdfPTable table, String label, String value) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, new Font(Font.HELVETICA, 11, Font.BOLD)));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, new Font(Font.HELVETICA, 11)));

        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);

        table.addCell(cell1);
        table.addCell(cell2);
    }
}
