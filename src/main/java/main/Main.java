package main;

import static main.Settings.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Translator.translate(PLATFORM, LANG_FROM, LANGUAGES, INPUT_DIR, OUTPUT_DIR);

        // Остальные задачи — раскомментировать нужную. replace и DaysOfWeek пишут результат в папку INPUT_DIR + "_REPLACED"
//        FileTools.find(INPUT_DIR, "u200B", "%@");
//        FileTools.replace(INPUT_DIR, " = ", "", ";", "test");  // пары: что заменить, на что заменить
//        DaysOfWeek.replace(INPUT_DIR, WEEK_DAYS_KEYS);
//        FileTools.compare(INPUT_DIR + "/Localizable1.strings", INPUT_DIR + "/Localizable2.strings");
//        StringsFile.convert(INPUT_DIR, OUTPUT_DIR);  // .strings ↔ strings.xml для всех языковых папок
    }
}
