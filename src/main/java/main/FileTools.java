package main;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileTools {

    public static void find(String dir, String... values) throws IOException {
        for (Path file : listFiles(dir)) {
            List<String> lines = readText(file).lines().toList();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (Stream.of(values).anyMatch(line::contains)) {
                    System.out.println(file + ":" + (i + 1) + "  " + line.trim());
                }
            }
        }
    }

    public static void replace(String dir, String... pairs) throws IOException {
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Нужно чётное число значений: что заменить, на что заменить");
        }
        for (Path file : listFiles(dir)) {
            String text = readText(file);
            for (int i = 0; i < pairs.length; i += 2) {
                text = text.replace(pairs[i], pairs[i + 1]);
            }
            Files.writeString(replacedPath(dir, file), text);
        }
    }

    // Результат — result.strings рядом с первым файлом; повторяющиеся строки учитываются по количеству
    public static void compare(String firstFile, String secondFile) throws IOException {
        List<String> first = readText(Path.of(firstFile)).lines().toList();
        List<String> second = readText(Path.of(secondFile)).lines().toList();
        List<String> onlyFirst = new ArrayList<>(first);
        second.forEach(onlyFirst::remove);
        List<String> onlySecond = new ArrayList<>(second);
        first.forEach(onlySecond::remove);

        List<String> result = new ArrayList<>();
        result.add("-----этих строк нет в первом файле-----");
        result.addAll(onlySecond);
        result.add("-----этих строк нет во втором файле-----");
        result.addAll(onlyFirst);
        Path resultFile = Path.of(firstFile).resolveSibling("result.strings");
        Files.write(resultFile, result);
        System.out.printf("Нет в первом: %d, нет во втором: %d. Результат: %s%n", onlySecond.size(), onlyFirst.size(), resultFile);
    }

    public static List<Path> listFiles(String dir) throws IOException {
        try (Stream<Path> files = Files.walk(Path.of(dir))) {
            return files.filter(Files::isRegularFile)
                    .filter(file -> !file.getFileName().toString().startsWith("."))
                    .sorted()
                    .toList();
        }
    }

    public static String readText(Path file) throws IOException {
        try {
            return Files.readString(file).replace("﻿", "");
        } catch (CharacterCodingException e) {
            throw new IOException("Файл не в UTF-8: " + file, e);
        }
    }

    // to_do/de.lproj/x → to_do_REPLACED/de.lproj/x
    public static Path replacedPath(String dir, Path file) throws IOException {
        Path result = Path.of(dir + "_REPLACED").resolve(Path.of(dir).relativize(file));
        Files.createDirectories(result.getParent());
        return result;
    }
}
