package tests;

import java.lang.reflect.Method;
import models.Character;

public class GetClassMethods {
    public static void main(String args[]) {
        try {
            Class<Character> thisClass = Character.class;
            Method[] methods = thisClass.getDeclaredMethods();

            for (int i = 0; i < methods.length; i++)
                System.out.println(methods[i].toString());
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
