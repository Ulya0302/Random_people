import java.util.*;

class Row {
    private static Random gen = new Random();
    private int age, index, apartment;
    private String sex, ITN;
    private GregorianCalendar dateBirth;
    private String[] gend = {"M", "F"};
    private Map<String, String> vars = new HashMap<String, String>();

    public void genDataBirth() {
        int genYear = gen.nextInt(80) + 1920 ;
        int genMonth = gen.nextInt(12);
        int genDayYear = gen.nextInt(365) + 1;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(gc.YEAR, genYear);
        gc.set(gc.DAY_OF_YEAR, genDayYear);
        dateBirth = gc;
        setAge();
    }

    public void setAge() {
        Date curDate = new Date();
        //System.out.println(curDate.getDay());
        age = curDate.getYear() - dateBirth.get(dateBirth.YEAR) + 1900;
        if (curDate.getMonth() <  dateBirth.get(dateBirth.MONTH)) {
            age -= 1;
        }
        else if (curDate.getMonth() ==  dateBirth.get(dateBirth.MONTH)) {
            if ((curDate.getDay() <  dateBirth.get(dateBirth.DAY_OF_MONTH))) {
                age -=1;
            }
        }
    }


    public void genITN() {
        String s = "77";
        int k = gen.nextInt(51) + 1;
        if (k < 10) {
            s += "0" + k;
        } else {
            s += k;
        }
        s += gen.nextInt(899999) + 100000;
        s = countControlSum1(s);
        ITN = s;
    }

    private String countControlSum1(String s) {
        int sum = 0;
        char[] symb = s.toCharArray();
        int[] numbs = new int[10];
        int[] multNumbs = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
        int k = 0;
        for (char c: symb) {
            numbs[k] = Character.getNumericValue(c);
            k++;
        }
        for (int i = 0; i < 10; i++) {
            sum += numbs[i] * multNumbs[i];
        }
        s += sum % 11 % 10;
        return countControlSum2(s);
    }

    private String countControlSum2(String s) {
        int sum = 0;
        char[] symb = s.toCharArray();
        int[] multNumbs = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
        int[] numbs = new int[11];
        int k = 0;
        for (char c: symb) {
            numbs[k] = Character.getNumericValue(c);
            k++;
        }
        for (int i = 0; i < 11; i++) {
            sum += numbs[i] * multNumbs[i];
        }
        s += sum % 11 % 10;
        return s;

    }

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
        System.out.print(vars.get("surname") + " " + vars.get("name") + " " + vars.get("midName"));
        System.out.printf(" %02d-%02d-%d %d",dateBirth.get(dateBirth.DAY_OF_MONTH), dateBirth.get(dateBirth.MONTH)+1, dateBirth.get(dateBirth.YEAR), age);
        System.out.print(" " + vars.get("country") + " ");
        System.out.print(vars.get("region") + " " + vars.get("town") + " " + vars.get("street") + " ");
        System.out.print(ITN + " ");
        System.out.println(sex + " " + " " + index + " " + apartment);
    }
}