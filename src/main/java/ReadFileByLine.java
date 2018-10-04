import java.io.*;
import java.util.ArrayList;

public class ReadFileByLine {
    static String path;
    public ReadFileByLine(String path) {
        this.path = path;
    }

    public ArrayList<String> startRead() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File f = new File(path);
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
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
