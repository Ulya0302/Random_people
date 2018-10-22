import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс представляет собой абстрактное представление таблицы
 * Все записи (объекта класса MyRow) хранятся в списке
 * При создании объекта класса генерируется количество записей
 */

public class MyTable {
    static Random gen = new Random();
    private int quant = gen.nextInt(20) + 10;
    private ArrayList<MyRow> table = new ArrayList<MyRow>();
    static Map<String, String> paths = new HashMap<String, String>();

    public MyTable() {
        /**
         * При вызове конструкта заполняется словарь в виде "path":"nameField"
         */
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

    public ArrayList<MyRow> getTable() {
        return table;
    }

    public void createTableFromAPI() {
        ApiWorker api = new ApiWorker();
        int n = quant;
        try {
            for (int i = 0; i < quant; i++, n--)
                table.add(api.callApi());
            DataBaseWorker dtb = new DataBaseWorker();
            dtb.saveTableIntoDB(this);
            System.out.println("Rows was successfully added into database.");
            dtb.close();

        }
        catch (StackOverflowError err) {
            Scanner scn = new Scanner(System.in);
            System.out.println("Ooops. There is no connection to Internet.. " +
                    "Trying to connect to database and get data from its...");
            try {
                DataBaseWorker dtb = new DataBaseWorker();
                for (int i = 0; i < n; i++)
                    table.add(dtb.getMyRowFromDB());
                dtb.close();
            }
            catch (SQLException ex) {
                System.out.println("Can't get data from database.");
            }
        }
        catch (SQLException ex) {
            System.out.println("Can't save rows to database.");
            ex.printStackTrace();
        }
        for (MyRow item: table) {
            item.genApartment();
            item.genITN();
            item.genDataBirth();
        }
    }

    public void createTableFromFile() {
        createRows();
        setRows();
    }

    private void createRows() {
        /**
         * Метод создает заданное количество записей
         */
        for (int i = 0; i < quant; i++) {
            table.add(new MyRow());
        }
    }

    private void setRows() {
        /**
         * Метод заполняет поля записей
         * Стоит отметить, что заполненение записей ведется не построчно
         * В первую очередь для всех записей заполняются поля пол, индекс, ИНН, дом и квартира, дата рождения
         * После происходит заполнение полей, связанных с чтением из файла
         * В первом цикле происходит итерация по всем файлам
         * Файл открывается, из него считываются значения и эти значения сохраняются в values
         * Далее происходит проход по всем записям
         * Вызывается метод, который во всех записях заполняет определенное поле (имя, фамилия ...)
         * Наименование поля и путь к файлу также передаются в метод
         */
        for (MyRow item: table) {
            item.genSex();
            item.genIndex();
            item.genApartment();
            item.genITN();
            item.genDataBirth();
        }
        for (String path: paths.keySet()) {
            try {
                ArrayList<String> values;
                ReadFileByLine buf = new ReadFileByLine(path);
                values = buf.startRead();
                for (MyRow item : table) {
                    item.genValue(path, values, paths.get(path));
                }
            }
            catch (IOException err) {
                err.printStackTrace();
            }
        }
    }

    public void saveTableToExcel() {
        CreateExcelFile f = new CreateExcelFile(table);
        f.save();
    }

    public void saveTableToPdf() {
        CreatePdfFile fl = new CreatePdfFile(table);
        fl.save();
    }
}
