package instruments;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import table.MyRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс предназначен для создания XLSX-файла из таблицы
 * В конструктор пердетается список объектов table.MyRow
 */

public class CreateExcelFile {

    private ArrayList<MyRow> table;

    public CreateExcelFile(ArrayList<MyRow> table) {
        this.table = table;
    }

    public void save() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Лист");
        writeHeader(sheet);
        writeRows(sheet);

        try {
            File f = new File("GeneratedTable.xlsx");
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            wb.write(out);
            System.out.println("File has been created successfully. Path: " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeHeader(XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < MyRow.header.length; i++)
            row.createCell(i).setCellValue(MyRow.header[i]);
    }

    private void writeRows(XSSFSheet sheet) {
        int rowNum = 0;
        for (MyRow bufMyRow : table) {
            Row tempRow = sheet.createRow(++rowNum);
            tempRow.createCell(0).setCellValue(bufMyRow.getName());
            tempRow.createCell(1).setCellValue(bufMyRow.getSurname());
            tempRow.createCell(2).setCellValue(bufMyRow.getMidname());
            tempRow.createCell(3).setCellValue(bufMyRow.getAge());
            tempRow.createCell(4).setCellValue(bufMyRow.getSex());
            tempRow.createCell(5).setCellValue(bufMyRow.getBirth());
            tempRow.createCell(6).setCellValue(bufMyRow.getITN());
            tempRow.createCell(7).setCellValue(bufMyRow.getIndex());
            tempRow.createCell(8).setCellValue(bufMyRow.getCountry());
            tempRow.createCell(9).setCellValue(bufMyRow.getRegion());
            tempRow.createCell(10).setCellValue(bufMyRow.getTown());
            tempRow.createCell(11).setCellValue(bufMyRow.getStreet());
            tempRow.createCell(12).setCellValue(bufMyRow.getHome());
            tempRow.createCell(13).setCellValue(bufMyRow.getApart());
        }
    }
}
