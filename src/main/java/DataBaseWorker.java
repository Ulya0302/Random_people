import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseWorker {

    private static String url;
    private static String user = "root";
    private static String passwd = "root";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet resSet = null;
    //private static Scanner in = new Scanner(System.in);

    public DataBaseWorker() throws SQLException {
        File f = new File("src/main/resourses/ConnectionData.txt");
        try {
            ReadFileByLine file = new ReadFileByLine("src/main/resources/ConnectionData.txt");
            ArrayList<String> data = file.startRead();
            user = data.get(0).split(":")[1];
            passwd = data.get(1).split(":")[1];
            url = "jdbc:mysql://" + data.get(2).split(":", 2)[1] + "" +
                    "?serverTimezone=Europe/Moscow&autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF8";

        }
        catch (FileNotFoundException ex) {
            //ex.printStackTrace();
            System.out.println("File " + f.getName() +  " not found. Please, find in README, how to create it");
            throw new SQLException();
        }
        catch (IOException ex)  {
            //ex.printStackTrace();
            System.out.println("Error while reading file. Please check file and their data and try again");
            throw new SQLException();
        }
        setConnection();
    }

    private void setConnection() throws SQLException {
        con = DriverManager.getConnection(url, user, passwd);
        stmt = con.createStatement();
        stmt.executeQuery("SET NAMES utf8");
        createTable();
    }

    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS people (" +
                "human_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(60) NOT NULL,\n" +
                "surname VARCHAR(60) NOT NULL,\n" +
                "midname VARCHAR(60) NOT NULL,\n" +
                "sex VARCHAR(1) NOT NULL,\n" +
                "country VARCHAR(60) NOT NULL,\n" +
                "region VARCHAR(60) NOT NULL,\n" +
                "town VARCHAR(60) NOT NULL,\n" +
                "street VARCHAR(60) NOT NULL,\n" +
                "ind INT(6) NOT NULL)\n" +
                "DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;";
        stmt.executeUpdate(query);
    }

    public void saveTableIntoDB(MyTable table) throws SQLException {
        for (MyRow row : table.getTable()) {
            addRowIntoDB(row);
        }
    }

    public void addRowIntoDB(MyRow row) throws SQLException {
        String query;
        query = "SELECT id, name, surname, midname FROM people WHERE " +
                "name="+row.getName()+", " +
                "surname="+row.getSurname() + ", " +
                "midname="+row.getMidname() + ";";
        resSet = stmt.executeQuery(query);
        if (resSet.next()) {
            query = "UPDATE people SET " +
                    "country='"+row.getCountry() + "', " +
                    "region='"+row.getRegion() + "', " +
                    "town='"+row.getTown() + "', " +
                    "street='"+row.getTown() + "', " +
                    "index='"+row.getTown() + "' " +
                    "WHERE human_id="+resSet.getInt(1)+";";
        }
        else {
            query = "INSERT INTO people" +
                    "(name, surname, midname, sex, country, region, town, street, ind) " +
                    "VALUES (\"" +
                    row.getName() + "\", \"" +
                    row.getSurname() + "\", \"" +
                    row.getMidname() + "\", \"" +
                    row.getSex() + "\", \"" +
                    row.getCountry() + "\", \"" +
                    row.getRegion() + "\", \"" +
                    row.getTown() + "\", \"" +
                    row.getStreet() + "\", " +
                    row.getIndex() + ");";
        }
        stmt.executeUpdate(query);
    }

    public MyRow getMyRowFromDB() throws SQLException {
        if (resSet == null) {
            resSet = stmt.executeQuery("SELECT count(*) FROM people");
            resSet.next();
            int count = resSet.getInt(1);
            if (count == 0) {
                System.out.println("In database no rows.");
                throw new SQLException();
            }
            resSet = stmt.executeQuery("SELECT * FROM people");
        }
        resSet.next();
        String name = resSet.getString(2);
        String surname = resSet.getString(3);
        String midname = resSet.getString(4);
        String sex = resSet.getString(5);
        String country = resSet.getString(6);
        String region = resSet.getString(7);
        String town = resSet.getString(8);
        String street = resSet.getString(9);
        int index = resSet.getInt(10);
        return new MyRow(name, surname, midname, sex,
                country, region,
                town, street, index);

    }
    public void close() throws SQLException {
        con.close();
    }



}
