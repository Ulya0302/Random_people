package instruments;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public ArrayList<String> startRead() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null)
                lines.add(line);
        }
    return lines;
    }
}
