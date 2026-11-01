package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

// Сокращения дней недели Google переводит верно, только когда они идут подряд «Пн Вт Ср…», поэтому берём из словаря
public class DaysOfWeek {
    // С понедельника
    private static final Map<String, List<String>> DAYS = Map.ofEntries(
            entry("ar", List.of("الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت", "الأحد")),
            entry("ca", List.of("Dl", "Dt", "Dc", "Dj", "Dv", "Ds", "Dg")),
            entry("cs", List.of("Po", "Út", "St", "Čt", "Pá", "So", "Ne")),
            entry("da", List.of("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø")),
            entry("de", List.of("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")),
            entry("el", List.of("Δε", "Τρ", "Τε", "Πε", "Πα", "Σα", "Κυ")),
            entry("es", List.of("Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do")),
            entry("fi", List.of("Ma", "Ti", "Ke", "To", "Pe", "La", "Su")),
            entry("fr", List.of("Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di")),
            entry("hi", List.of("सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि")),
            entry("hr", List.of("Po", "Ut", "Sr", "Če", "Pe", "Su", "Ne")),
            entry("hu", List.of("Hé", "Ke", "Sze", "Cs", "Pé", "Szo", "Va")),
            entry("id", List.of("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")),
            entry("it", List.of("Lu", "Ma", "Me", "Gi", "Ve", "Sa", "Do")),
            entry("he", List.of("יום ב'", "יום ג'", "יום ד'", "יום ה'", "יום ו'", "יום שבת", "יום א'")),
            entry("ja", List.of("月", "火", "水", "木", "金", "土", "日")),
            entry("ko", List.of("월", "화", "수", "목", "금", "토", "일")),
            entry("ms", List.of("Is", "Se", "Ra", "Kh", "Ju", "Sa", "Ah")),
            entry("nl", List.of("Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo")),
            entry("no", List.of("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø")),
            entry("pl", List.of("Po", "Wt", "Śr", "Cz", "Pt", "So", "Nd")),
            entry("pt", List.of("Se", "Te", "Qua", "Qui", "Sex", "Sáb", "Dom")),
            entry("ro", List.of("Lu", "Ma", "Mi", "Jo", "Vi", "Sâ", "Du")),
            entry("ru", List.of("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")),
            entry("sk", List.of("Po", "Ut", "St", "Št", "Pi", "So", "Ne")),
            entry("sv", List.of("Må", "Ti", "On", "To", "Fr", "Lö", "Sö")),
            entry("th", List.of("จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์", "เสาร์", "อาทิตย์")),
            entry("tr", List.of("Pt", "Sa", "Ça", "Pe", "Cu", "Ct", "Pa")),
            entry("uk", List.of("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд")),
            entry("vi", List.of("T2", "T3", "T4", "T5", "T6", "T7", "CN")),
            entry("zh", List.of("一", "二", "三", "四", "五", "六", "日")));

    public static void replace(String dir, List<String> keys) throws IOException {
        for (Path file : FileTools.listFiles(dir)) {
            if (!StringsFile.isStringsFile(file)) {
                continue;
            }
            List<String> days = DAYS.get(StringsFile.languageOf(file));
            if (days == null) {
                System.out.println("Пропущено, нет дней недели для этой папки: " + file);
                continue;
            }

            boolean android = StringsFile.isAndroid(file);
            List<String> lines = new ArrayList<>();
            for (String line : FileTools.readText(file).lines().toList()) {
                StringsFile.Entry entry = StringsFile.parseLine(line, android);
                int day = entry == null ? -1 : keys.indexOf(entry.key());
                if (day >= 0) {
                    String indent = line.substring(0, line.length() - line.stripLeading().length());
                    line = indent + StringsFile.formatLine(new StringsFile.Entry(entry.key(), days.get(day)), android);
                }
                lines.add(line);
            }
            Files.write(FileTools.replacedPath(dir, file), lines);
        }
    }
}
