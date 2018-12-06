package table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс представляет собой абстрактное представление кортежа (записи) в таблице
 * Поля, значения для которых получают из файла, хранятся в словаре в виде "nameField":"valueField"
 * Поле даты рождения хранится в GregorianCalendar, при вызове геттера возвращается строка в формате "ДД-ММ-ГГГГ"
 * Поля индекс, возраст, квартира и дом хранятся целочисленно и возвращаются целочисленно,
 * Оставшиеся - в строковом формате
 */
public class MyRow{
    public static String[] header = {"Имя", "Фамилия", "Отчетство", "Возраст", "Пол", "Дата рождения",
            "ИНН", "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};
    private Random gen = new Random();
    private int age, index, apartment, home;
    private String sex, ITN;
    private GregorianCalendar dateBir = new GregorianCalendar();
    private String[] gend = {"М", "Ж"};
    private Map<String, String> vars = new HashMap<>();

    public MyRow(String name, String surname, String midname,
                 String sex, String country,
                 String region, String town,
                 String street, int index ) {
        vars.put("name", name);
        vars.put("surname", surname);
        vars.put("midname", midname);
        vars.put("country", country);
        vars.put("region", region);
        vars.put("town", town);
        vars.put("street", street);
        this.sex = sex;
        this.index = index;
    }

    public MyRow() {}
    
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

    /*
     * Метод преобразует dataBirth в строковое значение и возвращает его
     * Возвращается строка вида "ДД-ММ-ГГГГ"
     * Если день или месяц состоят из одной цифры (т.е. <10), то к ним добавляется 0 перед цифрой
     */
    public String getBirth() {
        String s1="", s2="";
        if (dateBir.get(Calendar.DAY_OF_MONTH) < 10) {
            s1 += "0" + dateBir.get(Calendar.DAY_OF_MONTH);
        } else {
            s1 += dateBir.get(Calendar.DAY_OF_MONTH);
        }
        if (dateBir.get(Calendar.MONTH) < 9) {
            s2 += "0" + (dateBir.get(Calendar.MONTH)+1);
        } else {
            s2 += (dateBir.get(Calendar.MONTH)+1);
        }
        return s1 + "-" + s2 + "-" + dateBir.get(Calendar.YEAR);
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

    //Дата генерируется в пределах от 01.01.1920 до 12.12.1999
    void genDataBirth() {
        int genYear = gen.nextInt(80) + 1920 ;
        int genDayYear = gen.nextInt(365) + 1;
        dateBir.set(Calendar.YEAR, genYear);
        dateBir.set(Calendar.DAY_OF_YEAR, genDayYear);
        setAge();
    }

    void genSex() {
        sex = gend[gen.nextInt(2)];
    }

    void genApartment() {
        apartment = gen.nextInt(500) + 1;
        home = gen.nextInt(500) + 1;
    }

    void genIndex() {
        index = gen.nextInt(899999) + 100000;
    }

    /*
     * Метод выбирает случайное значение из переданного набора и записывает в заданное поле
     * В качестве агрументов приннимаются путь к файлу, набор возможных значений и наименование поля (имя, город..)
     * Путь к файлу необходим только для определния пола (М или Ж) для полей ФИО для полей имя, фамилия, отчество
     * В полях, где пол не важен (страна, город...) просто происходит заполенение
     * В словарь объекта добавляется пара "nameField":"valueField"
     */
    void genValue(String path, ArrayList<String> vals, String variable) {
        if ((variable.equals("name")) || (variable.equals("surname")) || (variable.equals("midname"))) {
            String buf = path.split("[.]")[0];
            String gender;
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

    /*
     * Генерация ИНН
     * Первые две цифры - 77 (Московский регион)
     * Вторые две цифры - код ФНС, на текущий момент в Москве их 51
     * Затем случайные 6 цифр
     * Последние 2 цифры - контрольные суммы, вычисляются по алгоритму
     */
    void genITN() {
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

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String time = dateFormat.format(Calendar.getInstance().getTime());
    private String[] timeArr = time.split("/");
    /*
     * Единственное поле, которое не генерируется, а вычисляется
     * Вычисляется как разность текущей даты и даты рождения
     * Сначала вычислается кол-во лет, как разность годов
     * Потом проверяется, был ли у человека день рождения в этом году или нет
     * Если еще не был, то вычитается один год
     */
    private void setAge() {
        age = Integer.parseInt(timeArr[0]) - dateBir.get(Calendar.YEAR);
        int month = Integer.parseInt(timeArr[1]);
        int day = Integer.parseInt(timeArr[2]);
        if (month <  dateBir.get(Calendar.MONTH)+1)
            age--;
        else
        if ((dateBir.get(Calendar.MONTH)+1 == month) &&  (day <  dateBir.get(Calendar.DAY_OF_MONTH)))
            age--;
    }

}