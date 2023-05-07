package tests;

public class NullExceptionTest {
    public static Object foo() {
        return null;
    }
    public static void main(String[] args) {
        Object bar = foo();
        System.out.println(bar);
    }
}
