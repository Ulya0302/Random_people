package instruments;

import table.MyRow;
import table.MyTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FillData {

    private static Map<String, String> paths = new HashMap<>() ;
    static {
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

    public static void fillDataFromApi(MyTable tbl, int count) {
        ApiWorker api = new ApiWorker();
        int n = count;
        try {
            for (int i = 0; i < count; i++, n--)
                tbl.getTable().add(api.callApi());
            DataBaseWorker dtb = new DataBaseWorker();
            dtb.saveTableIntoDB(tbl);
            System.out.println("Rows was successfully added into database.");
            dtb.close();

        }
        catch (StackOverflowError err) {
            System.out.println("Ooops. There is no connection to Internet.. " +
                    "Trying to connect to database and get data from its...");
            try {
                DataBaseWorker dtb = new DataBaseWorker();
                for (int i = 0; i < n; i++)
                    tbl.add(dtb.getMyRowFromDB());
                dtb.close();
            }
            catch (SQLException ex) {
                System.out.println("Can't get data from database.");
            }
        }
        catch (SQLException ex) {
            System.out.println("Can't save rows to database.");
        }
        for (MyRow item: tbl.getTable()) {
            item.genApartment();
            item.genITN();
            item.genDataBirth();
        }
    }

    /**
     * Метод заполняет таблицу записями MyRow через API, а затем добавляет их в БД
     * Если при попытке получения данных отсутствует подключение к интернету
     * Метод начинает брать данные из БД
     * Если в БД нету записей или невозможно к ней подключиться, заполнение таблицы прекращается
     * Если какие-либо записи были сгененрировны, для них заполняются оставшиеся поля
     */
    public static void fillDataFromFiles(MyTable tbl, int count) {
        createRows(tbl, count);
        setRows(tbl);
    }

    /*
     * Метод создает заданное количество записей
     */
    private static void createRows(MyTable tbl, int count) {
        for (int i = 0; i < count; i++) {
            tbl.add(new MyRow());
        }
    }

    /*
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
    private static void setRows(MyTable table) {
        for (MyRow item: table.getTable()) {
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
                for (MyRow item : table.getTable()) {
                    item.genValue(path, values, paths.get(path));
                }
            }
            catch (IOException err) {
                err.printStackTrace();
            }
        }
    }


}
