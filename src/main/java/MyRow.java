import java.util.*;

/**
 * Класс представляет собой абстрактное представление кортежа (записи) в таблице
 * Поля, значения для которых получают из файла, хранятся в словаре в виде "nameField":"valueField"
 * Поле даты рождения хранится в GregorianCalendar, при вызове геттера возвращается строка в формате "ДД-ММ-ГГГГ"
 * Поля индекс, возраст, квартира и дом хранятся целочисленно и возвращаются целочисленно,
 * Оставшиеся - в строковом формате
 */

class MyRow{
    public static String[] header = {"Имя", "Фамилия", "Отчетство", "Возраст", "Пол", "Дата рождения",
            "ИНН", "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира"};
    private static Random gen = new Random();
    private int age, index, apartment, home;
    private String sex, ITN;
    private GregorianCalendar dateBir;
    private String[] gend = {"М", "Ж"};
    private Map<String, String> vars = new HashMap<String, String>();

    public MyRow() {}

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
        /**
         * Метод преобразует dataBirth в строковое значение и возвращает его
         * Возвращается строка вида "ДД-ММ-ГГГГ"
         * Если день или месяц состоят из одной цифры (т.е. <10), то к ним добавляется 0 перед цифрой
         */
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
        /**
         * Дата генерируется в пределах от 01.01.1920 до 12.12.1999
         * Не знаю, что у нас за мини База, но пусть лучше там будут совершенолетние с:
         */
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
        /**
         * Единственное поле, которое не генерируется, а вычисляется
         * Вычисляется как разность текущей даты и даты рождения
         * Сначала вычислается кол-во лет, как разность годов
         * Потом проверяется, был ли у человека день рождения в этом году или нет
         * Если еще не был, то вычитается один год
         */
        Date curDate = new Date();
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
        /**
         * Генерация ИНН
         * Первые две цифры - 77 (Московский регион)
         * Вторые две цифры - код ФНС, на текущий момент в Москве их 51
         * Затем случайные 6 цифр
         * Последние 2 цифры - контрольные суммы, вычисляются по алгоритму
         */
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
        /**
         * Метод считает 11-ую цифру ИНН
         * На вход ожидается строка, состоящая из 10 цифр
         * Вызывает подсчет 12-ой цифры и, соответственно, возвращает полный ИНН
         */
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
        /**
         * Метод считает 12-ую цифру ИНН
         * На вход ожидается строка, состоящая из 11 цифр
         * Возвращает полный ИНН из 12 цифр
         */
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
        /**
         * Метод выбирает случайное значение из переданного набора и записывает в заданное поле
         * В качестве агрументов приннимаются путь к файлу, набор возможных значений и наименование поля (имя, город..)
         * Путь к файлу необходим только для определния пола (М или Ж) для полей ФИО для полей имя, фамилия, отчество
         * В полях, где пол не важен (страна, город...) просто происходит заполенение
         * В словарь объекта добавляется пара "nameField":"valueField"
         */
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
}