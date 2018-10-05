import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Table {
    static Random gen = new Random();
    static int quant = gen.nextInt(20) + 10;
    static ArrayList<MyRow> table = new ArrayList<MyRow>();
    static Map<String, String> paths = new HashMap<String, String>();

    public Table() {
        paths.put("src/main/resources/namesM.txt", "name");
        paths.put("src/main/resources/namesF.txt", "name");
        paths.put("src/main/resources/surnamesM.txt", "surname");
        paths.put("src/main/resources/surnamesF.txt", "surname");
        paths.put("src/main/resources/midnamesM.txt", "midname");
        paths.put("src/main/resources/midnamesF.txt", "midname");
        paths.put("src/main/resources/countries.txt", "country");
        paths.put("src/main/resources/regions.txt", "region");
        paths.put("src/main/resources/towns.txt", "town");
        paths.put("src/main/resources/streets.txt", "street");
    }

    public static void createTable() {
        createValues();
        setValues();
    }

    private static void createValues() {
        for (int i = 0; i < quant; i++) {
            table.add(new MyRow());
        }
    }

    private static void setValues() {
        for (MyRow item: table) {
            item.genSex();
            item.genIndex();
            item.genApartment();
            item.genITN();
            item.genDataBirth();
        }
        for (String path: paths.keySet()) {
            ArrayList<String> values;
            ReadFileByLine buf = new ReadFileByLine(path);
            values = buf.startRead();
            for (MyRow item: table) {
                item.genValue(path, values, paths.get(path));
            }
        }
    }

    public static void printTable() {
        for (MyRow item: table) {
            item.printRow();
        }
    }

    public void saveTableToExcel() {
        CreateExcelFile f = new CreateExcelFile(table);
        f.save();
    }
}
