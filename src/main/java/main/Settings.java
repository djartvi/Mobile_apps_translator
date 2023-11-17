package main;

public class Settings {
    // Платформа, для которой выполняем перевод ("ios" или "android")
    public static final String PLATFORM = "ios";

    /*
    Языки для перевода c английского. По умолчанию:
    "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh"
     */
    public static final String LANGUAGES = "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh";

    // Язык, с которого выполняем перевод
    public static final String LANG_FROM = "en";

    // Директории и файлы. Файлы для переводов/замены параметров кладём в директорию проекта, в папку /to_do
    public static final String BASE_PATH = "to_do/";
    public static final String RESULT_PATH = "translate_result/";
    public static final String XML_FILE = "strings.xml";
    public static final String LOCALIZABLE_FILE = "Localizable.strings";
    public static final String FROM_PATH = BASE_PATH + "1/";
    public static final String TO_PATH = BASE_PATH + "2/";
    public static final String MERGED_PATH = BASE_PATH + "3/";
    public static final String REPLACED_DIR_POSTFIX = "_REPLACED/";
    public static final String SLASH = "/";

    // ключи, по которым
    public static final String[] WEEK_DAYS_KEYS = {"plan_week_mon", "plan_week_tue", "plan_week_wed",
            "plan_week_thu", "plan_week_fri", "plan_week_sat", "plan_week_sun"};
}
