package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.example.Paths.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // задать платформу (ios, android), действие (замена строк, перевод),

//        new Replacer().replace(new ListOfDirs().returnList());
//        new ReplacerToHtml().replaceBrackets(new ListOfDirsToHtml().returnList());
//        new iOSReplacer().replaceToXml();
//        new iOSReplacer().replaceToStrings(new ListOfDirs().returnList(MERGED_PATH));
            new FromOneToAnother().replaceFromTo(new ListOfDirs().returnList(FROM_PATH), XML_FILE);
//    new JavaScriptReader().runScript();

    }
}