import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс предназначен для создания PDF-файла из таблицы
 * В конструктор пердетается список объектов MyRow
 */

public class CreatePdfFile {
    private ArrayList<MyRow> table;

    public CreatePdfFile(ArrayList<MyRow> table) {
        this.table = table;
    }

    public void save() {
        try {
            File f = new File("GeneratedPDF.pdf");
            PdfWriter writer = new PdfWriter("GeneratedPDF.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4.rotate());
            Table tbl = new Table(14);
            PdfFont font = PdfFontFactory.createFont("c:/windows/fonts/times.ttf", "cp1251",
                                                        BaseFont.EMBEDDED);
            tbl = writeHeader(tbl, font);
            tbl = writeRows(tbl, font);
            document.add(tbl);
            System.out.println("Файл успешно создан. Путь к файлу: "+ f.getAbsolutePath());
            document.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Table writeHeader(Table tbl, PdfFont font) {
        for (String str : MyRow.header) {
            tbl.addCell(new Cell().add(new Paragraph(str).setFont(font)));
        }
        return tbl;
    }

    public Table writeRows(Table tbl, PdfFont font) {
        for (MyRow row : table) {
            tbl.addCell(new Cell().add(new Paragraph(row.getName()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getSurname()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getMidname()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(String.valueOf(row.getAge())).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getSex()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getBirth()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(String.valueOf(row.getITN())).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(String.valueOf(row.getIndex())).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getCountry()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getRegion()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getTown()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(row.getStreet()).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(String.valueOf(row.getHome())).setFont(font)));
            tbl.addCell(new Cell().add(new Paragraph(String.valueOf(row.getApart())).setFont(font)));
        }
        return tbl;

    }
}
