package main;

import java.util.List;

public class Settings {
    // "ios" — переводим INPUT_DIR/Localizable.strings, "android" — INPUT_DIR/strings.xml
    public static final String PLATFORM = "ios";

    public static final String LANG_FROM = "en";
    public static final String LANGUAGES = "ar,ca,cs,da,de,el,es,fi,fr,hi,hr,hu,id,it,he,ja,ko,ms,nl,no,pl,pt,ro,ru,sk,sv,th,tr,uk,vi,zh";

    public static final String INPUT_DIR = "to_do";
    public static final String OUTPUT_DIR = "translate_result";

    // Ключи строк с сокращениями дней недели, по порядку с понедельника
    public static final List<String> WEEK_DAYS_KEYS = List.of("plan_week_mon", "plan_week_tue", "plan_week_wed",
            "plan_week_thu", "plan_week_fri", "plan_week_sat", "plan_week_sun");
}
