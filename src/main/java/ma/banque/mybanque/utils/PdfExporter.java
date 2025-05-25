package ma.banque.mybanque.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import ma.banque.mybanque.entity.DemandeCredit;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PdfExporter {

    private List<DemandeCredit> listeDemandes;

    public PdfExporter(List<DemandeCredit> listeDemandes) {
        this.listeDemandes = listeDemandes;
    }

    private void ecrireEnteteTable(PdfPTable table) {
        PdfPCell cellule = new PdfPCell();
        cellule.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellule.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);

        cellule.setPhrase(new Phrase("ID", font));
        table.addCell(cellule);

        cellule.setPhrase(new Phrase("Nom Client", font));
        table.addCell(cellule);

        cellule.setPhrase(new Phrase("Montant", font));
        table.addCell(cellule);

        cellule.setPhrase(new Phrase("Date", font));
        table.addCell(cellule);

        cellule.setPhrase(new Phrase("Statut", font));
        table.addCell(cellule);
    }

    private void ecrireDonneesTable(PdfPTable table) {
        for (DemandeCredit demande : listeDemandes) {
            table.addCell(String.valueOf(demande.getId()));
            table.addCell(demande.getUser().getNom());
            table.addCell(String.valueOf(demande.getMontant()));
            table.addCell(String.valueOf(demande.getDate()));
            table.addCell(demande.getStatut());
        }
    }

    public void exporter(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitre.setSize(18);
        fontTitre.setColor(BaseColor.DARK_GRAY);

        Paragraph titre = new Paragraph("Liste des Demandes de Crédit", fontTitre);
        titre.setAlignment(Element.ALIGN_CENTER);
        titre.setSpacingAfter(20);
        document.add(titre);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.2f, 3.5f, 2.5f, 3.0f, 2.0f});
        table.setSpacingBefore(10);

        ecrireEnteteTable(table);
        ecrireDonneesTable(table);

        document.add(table);
        document.close();
    }
}
