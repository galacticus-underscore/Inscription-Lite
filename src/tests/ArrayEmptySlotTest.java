package tests;

class Foo {
    private int value;

    public Foo(int n) {
        this.value = n;
    }
    
    public int getValue() {
        return this.value;
    }
}

public class ArrayEmptySlotTest {
    public static void main(String[] args) {
        Foo[] test = new Foo[4];

        test[0] = new Foo(1);
        test[1] = new Foo(2);
        test[3] = new Foo(4);

        for (Foo test_object : test) {
            try {
                System.out.println(test_object.getValue());
            }
            catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
