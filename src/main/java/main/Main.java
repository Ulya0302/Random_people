package main;

import table.MyTable;

public class Main {
    public static void main(String[] args) {
        MyTable t = new MyTable();
        t.fillDataFromFiles();
//        t.saveTableToExcel();
        t.saveTableToPdf();

        MyTable t2 = new MyTable();
        t2.fillTableFromApi();
        t2.saveTableToExcel();
        t2.saveTableToDB();

    }
}
