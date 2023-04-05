package models.processors;

public class LogProcessor {
    public static void start() {
        System.out.println("Inscryption Lite");
        System.out.println("A Computer Science 4 Project by Asperin, Co, & Kasilag");
        System.out.println("=".repeat(80));
    }

    public static void start(String className, String methodName) {
        System.out.println(className + "." + methodName + "()");
        System.out.println("=".repeat(80));
    }

    public static void log(String message) {
        System.out.println("LOG: " + message);
    }

    public static void success(String message) {
        System.out.println("SUCCESS: " + message + "!");
    }

    public static void error(String message) {
        System.out.println("ERROR: " + message + "!");
    }
}
