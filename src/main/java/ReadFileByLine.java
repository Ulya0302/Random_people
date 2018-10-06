import java.io.*;
import java.util.ArrayList;

public class ReadFileByLine {
    private static String path;
    public ReadFileByLine(String path) {
        this.path = path;
    }

    public ArrayList<String> startRead() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), "utf-8"))) {
                String line;
                while ((line = reader.readLine()) != null)
                    lines.add(line);
            }
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
