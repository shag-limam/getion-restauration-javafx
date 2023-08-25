package com.gd.pdfgenerator;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.gd.model.Commande;

public class PDFGenerator {

    public static void generatePDF(Commande commande, String fileName) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Détails de la commande :");
            contentStream.newLine();
            contentStream.showText("Date de commande: " + commande.getDateCommande());
            contentStream.newLine();
            contentStream.showText("Montant total: " + commande.getMontantTotal());
            contentStream.newLine();
            // Ajoutez plus de détails ici...

            contentStream.endText();
            contentStream.close();

            document.save(new File(fileName));
            document.close();

            System.out.println("PDF généré et enregistré sous : " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
