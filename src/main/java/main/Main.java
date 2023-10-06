package main;

import translator.Translator;

public class Main {
    public static void main(String[] args) throws Exception {

        String text = "<string name=\"week\">Пн Вт Ср Чт Пт Сб Вс</string>\n";

//        System.out.println(new Translator().callUrlAndParseResult("ru", "en", text));

        String languages = "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh";

        new Translator().translate("ios", "en", languages);
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