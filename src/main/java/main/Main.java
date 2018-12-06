package main;

import table.MyTable;

public class Main {
    public static void main(String[] args) {
        MyTable t = new MyTable();
        t.createTableFromFile();
        t.saveTableToExcel();
        t.saveTableToPdf();
    }
}
