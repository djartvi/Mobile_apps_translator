package translator;

import javax.script.ScriptException;
import java.io.*;

public class JavaScriptReader {

    String execFile = "/Users/alex/Desktop/any_replacer/src/main/resources/xml_translator/main.js";

    public void runScript() throws IOException, InterruptedException {
        // Создаем объект ProcessBuilder для запуска команды
        ProcessBuilder processBuilder = new ProcessBuilder("node", "main.js");

        // Устанавливаем рабочую директорию (необязательно)
        processBuilder.directory(new File("/Users/alex/Desktop/any_replacer/src/main/resources/xml_translator/"));

        // Запускаем команду
        Process process = processBuilder.start();

        // Получаем результаты выполнения команды
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Считываем и выводим результат
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // Ожидаем завершения процесса
        process.waitFor();

        // Выводим код завершения процесса
        System.out.println("Exit code: " + process.exitValue());
    }
}
