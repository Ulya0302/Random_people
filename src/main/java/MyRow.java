import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class MyRow{
    public static String[] header = {"Имя", "Фамилия", "Отчетство", "Возраст", "Пол", "Дата рождения",
            "ИНН", "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};
    private static Random gen = new Random();
    private int age, index, apartment, home;
    private String sex, ITN;
    private GregorianCalendar dateBir;
    private String[] gend = {"М", "Ж"};
    private Map<String, String> vars = new HashMap<String, String>();
    
    public String getName() {
        return vars.get("name");
    }

    public String getSurname() {
        return vars.get("surname");
    }

    public String getMidname() {
        return vars.get("midname");
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
    
    public String getBirth() {
        String s1="", s2="";
        if (dateBir.get(dateBir.DAY_OF_MONTH) < 10) {
            s1 += "0" + dateBir.get(dateBir.DAY_OF_MONTH);
        } else {
            s1 += dateBir.get(dateBir.DAY_OF_MONTH);
        }
        if (dateBir.get(dateBir.MONTH) < 10) {
            s2 += "0" + dateBir.get(dateBir.MONTH);
        } else {
            s2 += dateBir.get(dateBir.MONTH);
        }
        return s1 + "-" + s2 + "-" + dateBir.get(dateBir.YEAR);
    }

    public String getITN() {
        return ITN;
    }

    public int getIndex() {
        return index;
    }

    public String getCountry() {
        return vars.get("country");
    }
    
    public String getRegion() {
        return vars.get("region");
    }
    
    public String getTown() {
        return vars.get("town");
    }
    
    public String getStreet() {
        return vars.get("street");
    }
    
    public int getHome() {
        return home;
    }

    public int getApart() {
        return apartment;
    }
    
    public void genDataBirth() {
        int genYear = gen.nextInt(80) + 1920 ;
        int genMonth = gen.nextInt(12);
        int genDayYear = gen.nextInt(365) + 1;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(gc.YEAR, genYear);
        gc.set(gc.DAY_OF_YEAR, genDayYear);
        dateBir = gc;
        setAge();
    }

    public void setAge() {
        Date curDate = new Date();
        //System.out.println(curDate.getDay());
        age = curDate.getYear() - dateBir.get(dateBir.YEAR) + 1900;
        if (curDate.getMonth() <  dateBir.get(dateBir.MONTH)) {
            age -= 1;
        }
        else if (curDate.getMonth() ==  dateBir.get(dateBir.MONTH)) {
            if ((curDate.getDay() <  dateBir.get(dateBir.DAY_OF_MONTH))) {
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
        home = gen.nextInt(500) + 1;
    }

    public void genIndex() {
        index = gen.nextInt(899999) + 100000;
    }

    public void genValue(String path, ArrayList<String> vals, String variable) {
        if ((variable.equals("name")) || (variable.equals("surname")) || (variable.equals("midname"))) {
            String buf = path.split("[.]")[0];
            String gender = "";
            if (buf.endsWith("M")) {
                gender = "М";
            } else {
                gender = "Ж";
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
        System.out.print(vars.get("surname") + " " + vars.get("name") + " " + vars.get("midname"));
        System.out.printf(" %02d-%02d-%d %d",dateBir.get(dateBir.DAY_OF_MONTH), dateBir.get(dateBir.MONTH)+1, dateBir.get(dateBir.YEAR), age);
        System.out.print(" " + vars.get("country") + " ");
        System.out.print(vars.get("region") + " " + vars.get("town") + " " + vars.get("street") + " ");
        System.out.print(ITN + " ");
        System.out.println(sex + " " + " " + index + " " + apartment);
    }
}