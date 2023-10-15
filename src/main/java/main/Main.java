package main;

import replacers.Replacer;

import static main.Constants.*;

public class Main {
    public static void main(String[] args) throws Exception {

        String[] replacedValues = {" = ", "", ";", "ododood"};
        new Replacer().replace(LOCALIZABLE_PATH, replacedValues);
//
//        new Translator().translate(PLATFORM, LANG_FROM, LANGUAGES);
//
//        new Replacer().replace(new DirsAndFiles().createListOfDirs(FROM_PATH), XML_FILE, XML_FILE);
//        new Find().find(new DirsAndFiles().createListOfDirs(FROM_PATH), XML_FILE, "paywall_price_lifetime\"");
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