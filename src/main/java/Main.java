public class Main {
    public static void main(String[] args) {
        MyTable t = new MyTable();
        t.createTableFromAPI();
        t.saveTableToExcel();
    }
}
