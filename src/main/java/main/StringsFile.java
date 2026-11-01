package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Строки локализации iOS (Localizable.strings) и Android (strings.xml): чтение, запись, папки языков
public class StringsFile {
    public record Entry(String key, String value) {}

    private static final String IOS_FILE = "Localizable.strings";
    private static final String ANDROID_FILE = "strings.xml";

    // "key" = "value";  или  key = "value";  — внутри кавычек допустимы экранированные \"
    private static final Pattern IOS_LINE =
            Pattern.compile("^\\s*(?:\"((?:[^\"\\\\]|\\\\.)*)\"|([\\w.]+))\\s*=\\s*\"((?:[^\"\\\\]|\\\\.)*)\"\\s*;");
    private static final Pattern ANDROID_LINE = Pattern.compile("<string name=\"([^\"]+)\"([^>]*)>(.*)</string>");

    // Языки, у которых папка iOS не совпадает с кодом Google
    private static final Map<String, List<String>> IOS_FOLDERS = Map.of(
            "no", List.of("nb", "nn"),
            "zh", List.of("zh-Hans"));

    public static Path sourceFile(boolean android, String dir) {
        return Path.of(dir, android ? ANDROID_FILE : IOS_FILE);
    }

    public static List<Path> outputFiles(boolean android, String dir, String lang) {
        if (android) {
            return List.of(Path.of(dir, "values-" + lang, ANDROID_FILE));
        }
        return IOS_FOLDERS.getOrDefault(lang, List.of(lang)).stream()
                .map(folder -> Path.of(dir, folder + ".lproj", IOS_FILE))
                .toList();
    }

    // de.lproj → de, values-de → de, nb.lproj → no; null — если файл лежит не в языковой папке
    public static String languageOf(Path file) {
        String folder = file.toAbsolutePath().getParent().getFileName().toString();
        String lang;
        if (folder.endsWith(".lproj")) {
            lang = folder.substring(0, folder.length() - ".lproj".length());
        } else if (folder.startsWith("values-")) {
            lang = folder.substring("values-".length());
        } else {
            return null;
        }
        for (var entry : IOS_FOLDERS.entrySet()) {
            if (entry.getValue().contains(lang)) {
                return entry.getKey();
            }
        }
        return lang;
    }

    public static boolean isStringsFile(Path file) {
        String name = file.getFileName().toString();
        return name.equals(IOS_FILE) || name.equals(ANDROID_FILE);
    }

    public static boolean isAndroid(Path file) {
        return file.getFileName().toString().endsWith(".xml");
    }

    public static List<Entry> read(Path file) throws IOException {
        boolean android = isAndroid(file);
        List<Entry> entries = new ArrayList<>();
        for (String line : FileTools.readText(file).lines().toList()) {
            Entry entry = parseLine(line, android);
            if (entry != null) {
                entries.add(entry);
            } else if (looksLikeString(line, android)) {
                System.out.println("Пропущено, формат не поддерживается: " + line.trim());
            }
        }
        return entries;
    }

    public static void write(Path file, List<Entry> entries) throws IOException {
        boolean android = isAndroid(file);
        List<String> lines = new ArrayList<>();
        if (android) {
            lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            lines.add("<resources>");
        }
        for (Entry entry : entries) {
            lines.add((android ? "    " : "") + formatLine(entry, android));
        }
        if (android) {
            lines.add("</resources>");
        }
        Files.createDirectories(file.getParent());
        Files.write(file, lines);
    }

    // Значение возвращается без экранирования, чтобы Google видел обычные кавычки и апострофы
    public static Entry parseLine(String line, boolean android) {
        if (android) {
            Matcher m = ANDROID_LINE.matcher(line);
            if (!m.find() || m.group(2).contains("translatable=\"false\"")) {
                return null;
            }
            return new Entry(m.group(1), m.group(3).replace("\\'", "'").replace("\\\"", "\"").replace("&amp;", "&"));
        }
        Matcher m = IOS_LINE.matcher(line);
        if (!m.find()) {
            return null;
        }
        return new Entry(m.group(1) != null ? m.group(1) : m.group(2), m.group(3).replace("\\\"", "\""));
    }

    public static String formatLine(Entry entry, boolean android) {
        if (android) {
            String value = entry.value()
                    .replaceAll("&(?!#?\\w+;)", "&amp;")
                    .replace("'", "\\'")
                    .replace("\"", "\\\"");
            return "<string name=\"" + entry.key() + "\">" + value + "</string>";
        }
        return "\"" + entry.key() + "\" = \"" + entry.value().replace("\"", "\\\"") + "\";";
    }

    // Похоже на строку перевода, но не разобрано: многострочное значение, string-array, plurals
    private static boolean looksLikeString(String line, boolean android) {
        if (android) {
            return line.matches("\\s*<(string|plurals)\\b.*") && !line.contains("translatable=\"false\"");
        }
        return line.contains("=") && !line.trim().startsWith("/");
    }

    // Каждый Localizable.strings и strings.xml из папки переписывает в противоположный формат
    public static void convert(String inputDir, String outputDir) throws IOException {
        for (Path file : FileTools.listFiles(inputDir)) {
            if (!isStringsFile(file)) {
                continue;
            }
            boolean toAndroid = !isAndroid(file);
            List<Entry> entries = read(file).stream()
                    .map(e -> new Entry(e.key(), toAndroid
                            ? e.value().replaceAll("%(\\d+\\$)?@", "%$1s")
                            : e.value().replaceAll("%(\\d+\\$)?s", "%$1@")))
                    .toList();
            String lang = languageOf(file);
            List<Path> targets = lang == null ? List.of(sourceFile(toAndroid, outputDir)) : outputFiles(toAndroid, outputDir, lang);
            for (Path target : targets) {
                write(target, entries);
                System.out.println(file + " -> " + target);
            }
        }
    }
}
