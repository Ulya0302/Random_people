public class Main {
    public static void main(String[] args) {
        MyTable t = new MyTable();
        t.createTable();
        t.saveTableToExcel();
        t.saveTableToPdf();
    }
}
