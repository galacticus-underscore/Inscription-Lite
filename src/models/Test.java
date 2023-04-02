/*
 * Test.java
 * 
 * The testing grounds for the model classes we made and some Java concepts we
 * wanted to use in this project.
 */

package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

// import java.util.ArrayList;

// import models.interfaces.SigilEffect;

// class Demo {
// 	int x;
  	
//   	Demo(int num) {
//       x = num;
//     }
  
// 	int display() {
// 		System.out.println("x = " + x);
// 		return 0;
// 	}
// }

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

        // effects.add(new SigilEffect() {
        //     String output = "test successful";
        //     public void applyEffect() {
        //         System.out.println(output);
        //     }
        // });

        // effects.get(0).applyEffect();

        // // returns null
        // Card[] arr = new Card[4];
        // arr[1] = new Character(1, "path", 1, 1);
        // System.out.println(arr[1]);

        // ArrayList<Card> arraylist = new ArrayList<Card>();
        // System.out.println(arraylist.get(0));

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/static/data/card_data.csv")));
            String line = "";
            String[] tempArr;

            while ((line = br.readLine()) != null) {
                tempArr = Arrays.stream(line.split(",")).filter(x -> !x.isEmpty()).toArray(String[]::new);
                for(String tempStr : tempArr) {
                    System.out.print(tempStr + " ");
                }
                System.out.println("\n");
            }

            br.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
