package groep4.MagazijnApplicatie;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import groep4.MagazijnApplicatie.database.DatabaseManager;
import groep4.MagazijnApplicatie.entity.Box;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PDFFactory {
    private final Box products;
    private final String customerId;
    private final String name;
    private final String address;
    private final String sendDate;
    private final String orderId;
    private final DatabaseManager databaseManager;
    private final int currentBoxNumber;
    private final int maxBoxNumber;

    public PDFFactory(Box products, String customerId, String name, String address, String sendDate, String orderId, DatabaseManager databaseManager, int currentBoxNumber, int maxBoxNumber) {
        this.products = products;
        this.customerId = customerId;
        this.name = name;
        this. address = address;
        this.sendDate = sendDate;
        this. orderId = orderId;
        this.databaseManager = databaseManager;
        this.currentBoxNumber = currentBoxNumber;
        this.maxBoxNumber = maxBoxNumber;

        Document document = new Document();
        try {
            File file = new File(System.getProperty("user.dir") + "/pakbonnen/");
            if (!file.exists()) {
                file.mkdirs();
            }
            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/pakbonnen/" + orderId + "(" + currentBoxNumber + "," + maxBoxNumber + ").pdf"));
            document.open();

            addLogo(document);
            addPakbonHeader(document);
            addCustomerInfo(document);
            addOrderDetails(document);
            addProductTable(document);

            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void addLogo(Document document) throws DocumentException, IOException {
        Path path = Paths.get("src/main/resources/Logo_NerdyGadgets.png");
        Image image = Image.getInstance(path.toAbsolutePath().toString());
        image.scalePercent(3);
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorderWidth(0);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2});
        table.addCell(imageCell);
        table.addCell(getPdfPCell());

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
    }

    private void addPakbonHeader(Document document) throws DocumentException {
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph pakbon = new Paragraph("PAKBON " + currentBoxNumber + "/" + maxBoxNumber, boldFont);
        pakbon.setAlignment(Element.ALIGN_CENTER);
        document.add(pakbon);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
    }

    private void addCustomerInfo(Document document) throws DocumentException {
        document.add(new Paragraph(customerId));
        document.add(new Paragraph(name));
        document.add(new Paragraph(address));
        document.add(new Paragraph("\n"));
    }

    private void addOrderDetails(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        addOrderDetailRow(table, "Verzenddatum:", sendDate);
        addOrderDetailRow(table, "Bestelnummer:", String.valueOf(orderId));

        document.add(table);
        document.add(new Paragraph("\n"));
    }

    private void addOrderDetailRow(PdfPTable table, String label, String value) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(label));
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setPhrase(new Phrase(value));
        cell.setBorder(0);
        table.addCell(cell);
    }

    private void addProductTable(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        addTableHeader(table);
        addRows(table);

        document.add(table);
    }

    private static PdfPCell getPdfPCell() {
        Font whiteFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(255, 255, 255));
        Phrase info = new Phrase("""
                Nerdygadgets.com
                Nerdyweg 69
                4200 NG Gadgetsdorp
                0612345678
                nerdygadgets@gmail.com
                www.nerdygadgets.com
                Btw: 0345234223
                Kvk: 234234234""", whiteFont);

        PdfPCell pdfPCell = new PdfPCell(info);
        pdfPCell.setBorderWidth(0);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pdfPCell.setBackgroundColor(new BaseColor(34, 34, 47));
        pdfPCell.setPadding(5);
        return pdfPCell;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Aantal", "Product code", "Omschrijving")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    Phrase p1 = new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD));
                    header.setPhrase(p1);
                    header.setBorderWidth(0);
                    header.setBorderWidthBottom(2);
                    header.setPaddingBottom(10);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        for (int i = 0; i < products.getProductlist().size(); i++) {
            PdfPCell cell = new PdfPCell(new Phrase("1"));
            cell.setBorderWidth(0);
            cell.setPaddingBottom(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(products.getProductlist().get(i).stockItemID())));
            cell.setBorderWidth(0);
            cell.setPaddingBottom(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(databaseManager.getProductName(products.getProductlist().get(i).stockItemID())));
            cell.setBorderWidth(0);
            cell.setPaddingBottom(5);
            table.addCell(cell);
        }
        for (int i = 0; i < 3; i++) {
            PdfPCell cell = new PdfPCell(new Phrase());
            cell.setBorderWidth(0);
            cell.setBorderWidthTop(1);
            table.addCell(cell);
        }
    }
}
