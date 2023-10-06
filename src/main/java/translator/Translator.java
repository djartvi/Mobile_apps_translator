package translator;

import org.json.JSONArray;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static files.Paths.*;


public class Translator {

    String resultPath;

    public String callUrlAndParseResult(String langFrom, String langTo, String text) throws Exception {
        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private String parseResult(String inputJson) {
        StringBuilder result = new StringBuilder();

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3;

        for (int i = 0; i < jsonArray2.length(); i++) {
            jsonArray3 = (JSONArray) jsonArray2.get(i);
            result.append(jsonArray3.get(0).toString());
        }

        return result.toString();
    }

    public void translate(String platform, String langFrom, String languages) throws Exception {
        String[] langList = languages.split(",");

        switch (platform) {
            case "android":
                translateToXml(langFrom, langList);
                break;
            case "ios":
                translateToIOS(langFrom, langList);
                break;
            default:
                System.err.println("Не задана платформа для перевода (\"android\" или \"ios\")");
        }
    }

    public void translateToXml(String langFrom, String[] languages) throws Exception {
        TranslateSerializer translateSerializer = new TranslateSerializer().parseXml();
        List<String> toTranslate = translateSerializer.splitValues;

        for (String language : languages) {
            StringBuilder result = new StringBuilder();

            for (String s : toTranslate) {
                String request = callUrlAndParseResult(langFrom, language, s);
                result.append(request).append("\n");
            }
            resultPath = VALUES_PATH + "values_" + language + "/" + XML_FILE;
            translateSerializer.buildXmlFile(resultPath, result.toString());
            System.err.println(language + "----------");
            System.out.println(result.toString());
        }
    }

    public void translateToIOS(String langFrom, String[] languages) throws Exception {
        TranslateSerializer translateSerializer = new TranslateSerializer().parseIOS();
        List<String> toTranslate = translateSerializer.splitValues;

        for (String language : languages) {
            StringBuilder result = new StringBuilder();

            for (String s : toTranslate) {
                String request = callUrlAndParseResult(langFrom, language, s);
                result.append(request).append("\n");
            }
            resultPath = LOCALIZABLE_PATH + language + ".lproj/" + LOCALIZABLE_FILE;
            translateSerializer.buildIOSFile(resultPath, result.toString());
            System.err.println(language + "----------");
            System.out.println(result.toString());
        }
    }
}
