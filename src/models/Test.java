package models;
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
    }
}
