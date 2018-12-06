package table;

import instruments.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/*
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
        FillData.fillDataFromApi(this, quant);
    }

    public void saveTableToExcel() {
        CreateExcelFile f = new CreateExcelFile(table);
        f.save();
    }

    public void saveTableToPdf() {
        CreatePdfFile fl = new CreatePdfFile(table);
        fl.save();
    }

    public void add(MyRow row) {
        table.add(row);
    }


}

