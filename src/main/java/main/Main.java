package main;

import replacers.Compare;
import replacers.Find;
import replacers.Replacer;
import translator.Translator;

import static main.Settings.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Метод для переводов
        new Translator().translate("ios", LANG_FROM, LANGUAGES);

        // Метод для поиска строк в указанной директории, во всех вложенных файлах
        new Find().find(BASE_PATH, "u200B");

        // Задаём чётное кол-во значений. Каждое нечётное значение - заменяемый элемент, а каждое чётное - заменяющий.
        String[] replacedValues = {" = ", "", ";", "test"};
        // Передаём replacedValues в метод для замены значений
        new Replacer().replace(BASE_PATH, replacedValues);

        // Метод для замены аббревиатур дней недели на корректные переводы
        new Replacer().replaceDaysOfWeek(BASE_PATH, "ios", WEEK_DAYS_KEYS);

        // Метод, сравнивающий файлы построчно
        new Compare().compareLinesInFiles(BASE_PATH, "Localizable1.strings", "Localizable2.strings");
    }
}