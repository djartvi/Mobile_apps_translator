package main;

import replacers.Find;
import replacers.Replacer;
import translator.Translator;

import java.io.File;

import static main.Constants.*;

public class Main {
    public static void main(String[] args) throws Exception {

//        System.out.println(new Translator().callUrlAndParseResult("en", "fr", "Mo Tu We Th Fr Sa Su"));

        // Задаём чётное кол-во значений. Каждое нечётное значение - заменяемый элемент, а каждое чётное - заменяющий.
//        String[] replacedValues = {" = ", "", ";", "ododood"};
//        new Replacer().replace(LOCALIZABLE_PATH, replacedValues);
//          new Replacer().replaceDaysOfWeek(LOCALIZABLE_PATH, "ios");
//        new Translator().translate(PLATFORM, LOCALIZABLE_PATH, LANG_FROM, LANGUAGES);
//
        new Find().find(LOCALIZABLE_PATH, "u200B");
//
//        new Replacer().replace(new DirsAndFiles().createListOfDirs(FROM_PATH), XML_FILE, XML_FILE);
//        new ReplacerToHtml().replaceBrackets(new ListOfDirsToHtml().returnList());
//        new FromOneToAnother().addFromOneDirsToAnother(Paths.FROM_PATH, Paths.LOCALIZABLE_FILE, Paths.TO_PATH, Paths.LOCALIZABLE_FILE);
//        new iOSReplacer().replaceToXml(LOCALIZABLE_PATH, LOCALIZABLE_FILE);
//        new iOSReplacer().replaceToStrings(FROM_PATH);
//        new FromOneToAnother().replaceFromFileToFile(new DirsAndFiles().createListOfDirs(FROM_PATH),
//        new DirsAndFiles().createListOfFiles(TO_PATH));
//        new JavaScriptReader().runScript();
//        new Compare().compareLinesInFiles("Compare", "Localizable.strings", "Localizable2.strings");

    }
}