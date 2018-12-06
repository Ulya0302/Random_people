# fin_hw3

Fintech, Homework 3

Версия JDK: 1.8.0_181

Проект создает таблицы в Excel и PDF, заполненные случайными данными. Созданные файлы игнорируются Git'ом.
По умолчанию данные берутся из API.
При отсутствии подключения к интернету, данные берутся из базы данных.
Данные для подключения расположены в src/main/recources/ConnectionData.txt
В формате:
user:{login}
password:{passwd}
url:{url}

В работе программы создается таблица "people", которая включает в себя:
human_id, name, surname, midname, sex, country, region, town, street, ind


Чтобы запустить проект из консоли:
1. Убедитесь, что существует системная (или пользовательская) переменная JAVA_HOME в которой указан путь к JDK Пример: JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181
2. В переменную PATH должен быть добавлен путь к mvn.cmd и к java По умолчанию: C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.4\plugins\maven\lib\maven3\bin C:\Program Files\Java\jdk1.8.0_181\bin
3. Запустить командную строку, перейти в папку с проектом
4. Выполнить команду mvn compile
5. Выполнить команду mvn exec:java -Dexec.mainClass="main.Main"
