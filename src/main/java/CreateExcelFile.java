import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CreateExcelFile {

    ArrayList<MyRow> table;
    public CreateExcelFile(ArrayList<MyRow> table) {
        this.table = table;
    }
    public void save() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Лист");
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        for (int i = 0; i < MyRow.header.length; i++) {
            row.createCell(i).setCellValue(MyRow.header[i]);
        }
        for (MyRow bufMyRow : table) {
            Row tempRow = sheet.createRow(rowNum++);
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
        try {
            File f = new File("GeneratedTable.xlsx");
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            wb.write(out);
            System.out.println("Файл успешно создан. Путь к файлу: " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
