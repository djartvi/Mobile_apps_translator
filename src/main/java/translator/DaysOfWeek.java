package translator;

import java.util.HashMap;
import java.util.Map;

public class DaysOfWeek {

    // Создание основной карты
     Map<String, Map<Byte, String>> languageMap = new HashMap<>();

        // Добавление значений для каждого языка, кроме английского. Дни недели начинаются с понедельника для всех языков
    public Map<String, Map<Byte, String>> getLanguageMap() {
        languageMap.put("ar", createDayMap("الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت", "الأحد"));
        languageMap.put("ca", createDayMap("Dl", "Dt", "Dc", "Dj", "Dv", "Ds", "Dg"));
        languageMap.put("cs", createDayMap("Po", "Út", "St", "Čt", "Pá", "So", "Ne"));
        languageMap.put("da", createDayMap("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø"));
        languageMap.put("de", createDayMap("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"));
        languageMap.put("el", createDayMap("Δε", "Τρ", "Τε", "Πε", "Πα", "Σα", "Κυ"));
        languageMap.put("es", createDayMap("Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do"));
        languageMap.put("fi", createDayMap("Ma", "Ti", "Ke", "To", "Pe", "La", "Su"));
        languageMap.put("fr", createDayMap("Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di"));
        languageMap.put("hi", createDayMap("मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि", "सोम"));
        languageMap.put("hr", createDayMap("Po", "Ut", "Sr", "Če", "Pe", "Su", "Ne"));
        languageMap.put("hu", createDayMap("Hé", "Ke", "Sze", "Cs", "Pé", "Szo", "Va"));
        languageMap.put("id", createDayMap("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"));
        languageMap.put("it", createDayMap("Lu", "Ma", "Me", "Gi", "Ve", "Sa", "Do"));
        languageMap.put("he", createDayMap("יום א'", "יום שבת", "יום ו'", "יום ה'", "יום ד'", "יום ג'", "יום ב'"));
        languageMap.put("ja", createDayMap("月", "火", "水", "木", "金", "土", "日"));
        languageMap.put("ko", createDayMap("월", "화", "수", "목", "금", "토", "일"));
        languageMap.put("ms", createDayMap("Is", "Se", "Ra", "Kh", "Ju", "Sa", "Ah"));
        languageMap.put("nl", createDayMap("Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"));
        languageMap.put("no", createDayMap("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø"));
        languageMap.put("pl", createDayMap("Po", "Wt", "Śr", "Cz", "Pt", "So", "Nd"));
        languageMap.put("pt", createDayMap("Se", "Te", "Qua", "Qui", "Sex", "Sáb", "Dom"));
        languageMap.put("ro", createDayMap("Lu", "Ma", "Mi", "Jo", "Vi", "Sâ", "Du"));
        languageMap.put("ru", createDayMap("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"));
        languageMap.put("sk", createDayMap("Po", "Ut", "St", "Št", "Pi", "So", "Ne"));
        languageMap.put("sv", createDayMap("Må", "Ti", "On", "To", "Fr", "Lö", "Sö"));
        languageMap.put("th", createDayMap("จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์", "เสาร์", "อาทิตย์"));
        languageMap.put("tr", createDayMap("Pa", "Pt", "Sa", "Ça", "Pe", "Cu", "Ct"));
        languageMap.put("uk", createDayMap("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"));
        languageMap.put("vi", createDayMap("T2", "T3", "T4", "T5", "T6", "T7", "CN"));
        languageMap.put("zh", createDayMap("一", "二", "三", "四", "五", "六", "日"));

        return languageMap;
    }

    // Вспомогательный метод для создания вложенной карты для дней недели
    private static Map<Byte, String> createDayMap(String... daysOfWeek) {
        Map<Byte, String> dayMap = new HashMap<>();
        for (byte i = 1; i <= daysOfWeek.length; i++) {
            dayMap.put(i, daysOfWeek[i - 1]);
        }
        return dayMap;
    }
}
