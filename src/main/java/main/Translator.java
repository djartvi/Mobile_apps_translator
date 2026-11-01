package main;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Translator {
    private static final String URL = "https://translate.googleapis.com/translate_a/single?client=gtx&dt=t";
    // Больше 50 строк в одном запросе — риск, что Google вернёт ошибку
    private static final int CHUNK_SIZE = 50;
    // Без ключа Google пускает порядка 100 запросов в час, при частых запросах блокирует IP (HTTP 429)
    private static final long PAUSE_MS = 2000;
    // %@, %d, %1$@, %.2f, %lld, %% и \n должны совпасть в оригинале и переводе
    private static final Pattern PLACEHOLDER = Pattern.compile("%%|%(\\d+\\$)?\\d*(\\.\\d+)?(ll|l)?[@dsfiuxc]|\\\\n");
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void translate(String platform, String langFrom, String languages, String inputDir, String outputDir)
            throws IOException, InterruptedException {
        if (!platform.equals("ios") && !platform.equals("android")) {
            throw new IllegalArgumentException("PLATFORM должен быть \"ios\" или \"android\", а не \"" + platform + "\"");
        }
        boolean android = platform.equals("android");
        List<StringsFile.Entry> source = StringsFile.read(StringsFile.sourceFile(android, inputDir));
        List<String> langs = Arrays.stream(languages.split(",")).map(String::trim).toList();

        for (String lang : langs) {
            List<Path> files = StringsFile.outputFiles(android, outputDir, lang);
            // Дозапуск: уже переведённые ключи (и ручные правки в них) берём из прошлого результата
            Map<String, String> translated = new HashMap<>();
            if (Files.exists(files.get(0))) {
                StringsFile.read(files.get(0)).forEach(e -> translated.put(e.key(), e.value()));
            }
            List<StringsFile.Entry> missing = source.stream().filter(e -> !translated.containsKey(e.key())).toList();

            String error = null;
            try {
                for (int i = 0; i < missing.size(); i += CHUNK_SIZE) {
                    List<StringsFile.Entry> chunk = missing.subList(i, Math.min(i + CHUNK_SIZE, missing.size()));
                    List<String> lines = translateChunk(chunk.stream().map(StringsFile.Entry::value).toList(), langFrom, lang);
                    for (int j = 0; j < chunk.size(); j++) {
                        translated.put(chunk.get(j).key(), lines.get(j));
                    }
                }
            } catch (IOException e) {
                error = e.getMessage();
            }

            List<StringsFile.Entry> result = new ArrayList<>();
            int problems = 0;
            for (StringsFile.Entry original : source) {
                String text = translated.get(original.key());
                if (text == null) {
                    continue;
                }
                result.add(new StringsFile.Entry(original.key(), text));
                if (!placeholders(original.value()).equals(placeholders(text))) {
                    System.out.printf("  проверь %s: \"%s\" -> \"%s\"%n", original.key(), original.value(), text);
                    problems++;
                }
            }
            for (Path file : files) {
                StringsFile.write(file, result);
            }
            System.out.printf("%s: переведено %d из %d (новых %d), проверить: %d%n",
                    lang, result.size(), source.size(), result.size() - (source.size() - missing.size()), problems);

            if (error != null) {
                System.err.println(lang + ": " + error);
                System.err.println("Не переведены до конца: " + String.join(",", langs.subList(langs.indexOf(lang), langs.size()))
                        + ". Переведённое сохранено, повторный запуск продолжит с этого места");
                return;
            }
        }
    }

    // Перевод сопоставляется с ключами по номеру строки: если Google склеил или разбил строки, переводим по одной
    private static List<String> translateChunk(List<String> texts, String from, String to)
            throws IOException, InterruptedException {
        List<String> lines = List.of(request(from, to, String.join("\n", texts)).split("\n", -1));
        if (lines.size() == texts.size()) {
            return lines;
        }
        System.out.printf("  %s: Google вернул %d строк вместо %d, перевожу эту пачку по одной%n", to, lines.size(), texts.size());
        List<String> single = new ArrayList<>();
        for (String text : texts) {
            single.add(request(from, to, text));
        }
        return single;
    }

    private static String request(String from, String to, String text) throws IOException, InterruptedException {
        if (text.isBlank()) {
            return text;
        }
        Thread.sleep(PAUSE_MS);
        URI uri = URI.create(URL + "&sl=" + from + "&tl=" + to + "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.newBuilder(uri).header("User-Agent", "Mozilla/5.0").build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 429) {
            throw new IOException("Google временно ограничил запросы (HTTP 429), перезапусти позже");
        }
        if (response.statusCode() != 200) {
            throw new IOException("Google Translate ответил HTTP " + response.statusCode());
        }

        StringBuilder result = new StringBuilder();
        JSONArray sentences = new JSONArray(response.body()).getJSONArray(0);
        for (int i = 0; i < sentences.length(); i++) {
            result.append(sentences.getJSONArray(i).optString(0));
        }
        // Google меняет % на арабский ٪ и полноширинный ％ (ja, zh) и ломает этим %@ и %d
        return result.toString().replace('٪', '%').replace('％', '%');
    }

    private static List<String> placeholders(String text) {
        return PLACEHOLDER.matcher(text).results().map(MatchResult::group).sorted().toList();
    }
}
