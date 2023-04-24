/*
 * StyleProcessor.java
 * 
 * This processor is in charge of processing the styles of the application
 * before it runs. All of the methods below should be called in the main
 * class' runner function before the launch command.
 */

package models.processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StyleProcessor {
    private static int font_size = 16;

    public static int getFontSize() {
        return font_size;
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
}
