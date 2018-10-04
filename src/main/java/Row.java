import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Row {

    private static Random gen = new Random();
    int age, ITN, index, apartment;
    String sex;
    String[] gend = {"M", "F"};
    Map<String, String> vars = new HashMap<String, String>();



    public void genSex() {
        sex = gend[gen.nextInt(2)];
    }

    public void genApartment() {
        apartment = gen.nextInt(500) + 1;
    }
    public void genIndex() {
        index = gen.nextInt(899999) + 100000;
    }

    public void genValue(String path, ArrayList<String> vals, String variable) {
        if ((variable.equals("name")) || (variable.equals("surname")) || (variable.equals("midName"))) {
            //System.out.println(path);
            String buf = path.split("[.]")[0];
            String gender = "";
            if (buf.endsWith("M")) {
                gender = "M";
            } else {
                gender = "F";
            }
            if (gender.equals(sex)) {
                String value = vals.get(gen.nextInt(vals.size()));
                vars.put(variable, value);
            }
        } else {
            String value = vals.get(gen.nextInt(vals.size()));
            vars.put(variable, value);
        }
    }

    public void printRow() {
        System.out.print(vars.get("surname") + " " + vars.get("name") + " " + vars.get("midName") + " " + vars.get("country") + " ");
        System.out.print(vars.get("region") + " " + vars.get("town") + " " + vars.get("street") + " ");
        System.out.println(sex + " " + " " + index + " " + apartment);
    }
}