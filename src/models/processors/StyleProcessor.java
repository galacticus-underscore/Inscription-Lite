/*
 * StyleProcessor.java
 * 
 * This processor is in charge of processing the styles of the application
 * before it runs. All of the methods below should be called in the main
 * class' runner function before the launch command.
 */

package models.processors;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class StyleProcessor {
    private static int font_size = 16;

    public static int getFontSize() {
        return font_size;
    }

    public static void writeCardStyles() {
        try {
            BufferedReader fr = new BufferedReader(new FileReader(new File("src/static/data/card_data.csv")));
            FileWriter fw = new FileWriter("src/static/styles/sass/_cards.scss");
            String line = "";
            String class_name;
            String[] card_data;
            boolean skipped_headers = false;

            fw.write("@use \"global\";\n\n");

            while ((line = fr.readLine()) != null) {
                if (!skipped_headers) {
                    skipped_headers = true;
                    continue;
                }
                
                card_data = Arrays.stream(line.split(","))
                    .filter(x -> !x.isEmpty())
                    .toArray(String[]::new);

                class_name = card_data[1].toLowerCase().replace(' ', '-').replace("\'", "");

                fw.write("." + class_name
                    + " {\n\t@include global.image(\""
                    + card_data[2]
                    + "\");\n}\n\n"
                );
            }
                        
            fr.close();
            fw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void compile() {
        LogProcessor.start("StyleProcessor", "compile");

        LogProcessor.log("Initializing batch script");
        ProcessBuilder processBuilder = new ProcessBuilder("./src/scripts/compile.bat");
        	
        try {
            LogProcessor.log("Running batch script");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                LogProcessor.success(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        writeCardStyles();
    }
}
