package models;

import java.util.ArrayList;

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
        Demo[] D = {new Demo(1), new Demo(2)};
        Demo[] test = {null, null, null, null, null, null, null, null, null, null};
        Demo D1 = D[1];

		System.out.println(test[0]);
		System.out.println(D1.display());

		enum TestEnum {
            P1, P2, A
        }

        TestEnum test2 = TestEnum.P1;
        System.out.println(test2 == TestEnum.P1);

        Card[] cards = new Card[40];
        System.out.println(cards[0]);

        int[] testing = {1, 2, 3, 4, 5};
        int index = 0;
        int testingg = testing[index];
        System.out.println(testingg);
        index += 1;
        testingg = testing[index];
        System.out.println(testingg);

        App backend = new App();
        System.out.println(backend.getClass().getSimpleName());

        ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

        effects.add(new SigilEffect() {
            String output = "test successful";
            public void applyEffect() {
                System.out.println(output);
            }
        });

        effects.get(0).applyEffect();

        // returns null
        Card[] arr = new Card[4];
        arr[1] = new Character(1, 1, 1);
        System.out.println(arr[1]);

        ArrayList<Card> arraylist = new ArrayList<Card>();
        System.out.println(arraylist.get(0));
    }
}
