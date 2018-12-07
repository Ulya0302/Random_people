package table;

import instruments.*;
import java.sql.SQLException;
import java.util.*;

import static instruments.FillData.fillDataFromApi;

/**
 * Класс представляет собой абстрактное представление таблицы
 * Все записи (объекта класса table.MyRow) хранятся в списке
 * При создании объекта класса генерируется количество записей
 */
public class MyTable {
    private Random gen = new Random();
    private int quant = gen.nextInt(20) + 10;
    private ArrayList<MyRow> table = new ArrayList<>();

    public ArrayList<MyRow> getTable() {
        return table;
    }

    public void fillDataFromFiles() {
        FillData.fillDataFromFiles(this, quant);
    }

    public void fillTableFromApi() {
        fillDataFromApi(this, quant);
    }

    public void saveTableToExcel() {
        CreateExcelFile f = new CreateExcelFile(this);
        f.save();
    }

    public void saveTableToPdf() {
        CreatePdfFile fl = new CreatePdfFile(this);
        fl.save();
    }

    public void saveTableToDB() {
        try {
            DataBaseWorker dtb = new DataBaseWorker();
            dtb.saveTableIntoDB(this);
            dtb.close();
            System.out.println("Rows was successfully added into database.");
        }
        catch (SQLException ex) {
            System.out.println("Can't save rows to database.");
        }
    }

    public void add(MyRow row) {
        table.add(row);
    }
}

