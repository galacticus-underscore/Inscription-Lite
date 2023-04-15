/*
 * Test.java
 * 
 * This is the testing grounds for the model classes we made and some Java
 * concepts wanted to use in this project.
 */

package tests;

// import models.processors.StyleProcessor;

// import java.io.InputStreamReader;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;

// import java.util.Arrays;
// import java.util.Stack;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

// import models.enums.EventTypes;
// import models.patterns.SigilEffect;

// import java.util.ArrayList;

class Demo {
	int x;
  	
  	Demo(int num) {
      x = num;
    }

	int display() {
		System.out.println("x = " + x);
		return 0;
	}
}

public class Test {
    public static void main(String[] args) throws Exception {
        // Demo[] D = {new Demo(1), new Demo(2)};
        // Demo[] test = {null, null, null, null, null, null, null, null, null, null};
        // Demo D1 = D[1];

		// System.out.println(test[0]);
		// System.out.println(D1.display());

		// enum TestEnum {
        //     P1, P2, A
        // }

        // TestEnum test2 = TestEnum.P1;
        // System.out.println(test2 == TestEnum.P1);

        // Card[] cards = new Card[40];
        // System.out.println(cards[0]);

        // int[] testing = {1, 2, 3, 4, 5};
        // int index = 0;
        // int testingg = testing[index];
        // System.out.println(testingg);
        // index += 1;
        // testingg = testing[index];
        // System.out.println(testingg);

        // App backend = new App();
        // System.out.println(backend.getClass().getSimpleName());

        // ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

        // effects.add(new TestSigilEffect());

        // effects.get(0).applyEffect(EventTypes.DRAW);

        // // returns null
        // Card[] arr = new Card[4];
        // arr[1] = new Character(1, "path", 1, 1);
        // System.out.println(arr[1]);

        // ArrayList<Card> arraylist = new ArrayList<Card>();
        // System.out.println(arraylist.get(0));

        // Stack<Card> deck = new Stack<Card>();

        // try {
        //     BufferedReader br = new BufferedReader(new FileReader(new File("src/static/data/card_data.csv")));
        //     String line = "";
        //     String[] card_data;
        //     boolean skipped_headers = false;

        //     while ((line = br.readLine()) != null) {
        //         if (!skipped_headers) {
        //             skipped_headers = true;
        //             continue;
        //         }
                
        //         card_data = Arrays.stream(line.split(","))
        //             .filter(x -> !x.isEmpty())
        //             .toArray(String[]::new);

        //         switch (card_data[0].charAt(0)) {
        //             case 'C':
        //                 deck.push(new Character(
        //                     card_data[1],
        //                     card_data[2],
        //                     Integer.parseInt(card_data[3]),
        //                     Integer.parseInt(card_data[4]),
        //                     Integer.parseInt(card_data[5])
        //                 ));
        //                 break;
        //             case 'S':
        //                 deck.push(new Sigil(
        //                     card_data[1],
        //                     card_data[2],
        //                     Integer.parseInt(card_data[3]),
        //                     Boolean.parseBoolean(card_data[4]),
        //                     Boolean.parseBoolean(card_data[5]),
        //                     Integer.parseInt(card_data[6])
        //                 ));
        //                 break;
        //         }
        //     }
        // }
        // catch (IOException ioe) {
        //     ioe.printStackTrace();
        // }

        // String path = "filename.scss";

        // try {
        //     File myObj = new File(path);
        //     if (myObj.createNewFile()) {
        //         System.out.println("File created: " + myObj.getName());
        //     }
        //     else {
        //         System.out.println("File already exists.");
        //     }
        // } catch (IOException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        // }

        // try {
        //     FileWriter myWriter = new FileWriter(path, true);
        //     myWriter.write("\n$color: red");
        //     myWriter.close();
        //     System.out.println("Successfully wrote to the file.");
        // }
        // catch (IOException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        // }

        // ProcessBuilder processBuilder = new ProcessBuilder("compile.bat");
					
        // try {

        //     Process process = processBuilder.start();

        //     StringBuilder output = new StringBuilder();

        //     BufferedReader reader = new BufferedReader(
        //             new InputStreamReader(process.getInputStream()));

        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         output.append(line + "\n");
        //     }

        //     int exitVal = process.waitFor();
        //     if (exitVal == 0) {
        //         System.out.println(output);
        //         System.exit(0);
        //     } else {
        //         //abnormal...
        //     }

        // } catch (IOException e) {
        //     e.printStackTrace();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // StyleProcessor.writeScreenVars();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Rectangle bounds = gd.getDefaultConfiguration().getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

        Rectangle safeBounds = new Rectangle(bounds);
        safeBounds.x += insets.left;
        safeBounds.y += insets.top;
        safeBounds.width -= (insets.left + insets.right);
        safeBounds.height -= (insets.top + insets.bottom);
        System.out.println(safeBounds.width);
        System.out.println(safeBounds.height);
    }
}
