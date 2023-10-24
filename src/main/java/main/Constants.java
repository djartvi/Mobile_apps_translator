package main;

public class Constants {
    // Платформа, для которой выполняем перевод ("ios" или "android")
    public static final String PLATFORM = "ios";

    /*
    Языки для перевода c английского. По умолчанию:
    "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh"
     */
    public static final String LANGUAGES = "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh";

    // Язык, с которого выполняем перевод
    public static final String LANG_FROM = "en";

    // Директории и файлы
    public static final String BASE_PATH = "/Users/alex/Desktop/";
    public static final String RESULT_PATH = BASE_PATH + "TRANSLATE_RESULT/";
    public static final String VALUES_PATH = BASE_PATH + "values/";
    public static final String XML_FILE = "strings.xml";
    public static final String LOCALIZABLE_FILE = "Localizable.strings";
    public static final String LOCALIZABLE_PATH = BASE_PATH + "Localizable/";
    public static final String FROM_PATH = BASE_PATH + "1/";
    public static final String TO_PATH = BASE_PATH + "2/";
    public static final String MERGED_PATH = BASE_PATH + "3/";
    public static final String REPLACED_DIR_POSTFIX = "_REPLACED/";
    public static final String SLASH = "/";
}
