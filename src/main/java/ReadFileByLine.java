import java.io.*;
import java.util.ArrayList;

/**
 * Класс предназначен для построчного считания файла
 * В конструктор принимает путь к файлу
 * В методе startRead() файл открывается, читается
 * И метод возвращает список строк из файла
 */

public class ReadFileByLine {
    private String path;
    public ReadFileByLine(String path) {
        this.path = path;
    }

    public ArrayList<String> startRead() {
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "utf-8"))) {
            String line;
            while ((line = reader.readLine()) != null)
                lines.add(line);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    return lines;
    }
}
