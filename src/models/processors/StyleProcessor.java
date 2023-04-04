/*
 * StyleProcessor.java
 * 
 * This processor is in charge of processing the styles of the application
 * before it runs. All of the methods below should be called in the main
 * class' runner function before the launch command.
 */

package models.processors;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class StyleProcessor {
    public static void compile() {
        ProcessBuilder processBuilder = new ProcessBuilder("./src/scripts/compile.bat");
        	
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(output);
                
            }

            System.exit(exitVal);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void writeScreenVars() {
        // TODO: write root font size variable to SCSS file depending on the sceern width and height
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        try {
            FileWriter myWriter = new FileWriter("src/static/styles/sass/_screen.scss");
            myWriter.write("$screen-width: " + width + ";\n");
            myWriter.write("$screen-height: " + height + ";\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
