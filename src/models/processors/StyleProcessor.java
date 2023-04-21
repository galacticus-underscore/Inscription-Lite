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
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class StyleProcessor {
    private static int width, height, font_size = 16;

    public static int getWidth() {
        computeScreenVars();
        return width;
    }

    public static int getHeight() {
        computeScreenVars();
        return height;
    }

    public static int getFontSize() {
        computeScreenVars();
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

    public static void computeScreenVars() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle bounds = gd.getDefaultConfiguration().getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

        Rectangle safeBounds = new Rectangle(bounds);
        safeBounds.x += insets.left;
        safeBounds.y += insets.top;
        safeBounds.width -= (insets.left + insets.right);
        safeBounds.height -= (insets.top + insets.bottom);

        width = safeBounds.width;
        height = safeBounds.height;
    }

    public static void writeScreenVars() {
        LogProcessor.start("StyleProcessor", "writeScreenVars");

        LogProcessor.log("Getting maximized screen width and height");
        computeScreenVars();
        
        try {
            LogProcessor.log("Writing variables to _screen.scss");
            FileWriter myWriter = new FileWriter("src/static/styles/sass/_screen.scss");

            myWriter.write("$screen-width: " + width + "px;\n");
            LogProcessor.success("Written screen width to _screen.scss");

            myWriter.write("$screen-height: " + height + "px;\n");
            LogProcessor.success("Written screen height to _screen.scss");

            myWriter.write("$rem: " + font_size + "px;\n");
            LogProcessor.success("Written font size to _screen.scss");

            myWriter.close();
            LogProcessor.success("All variables written to _screen.scss");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(getWidth());
        System.out.println(getHeight());
    }
}
