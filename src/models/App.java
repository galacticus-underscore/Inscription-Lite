package models;
import java.util.Stack;

public class App {
    private static String view = Views.START.getPath();
    private static Stack<Views> view_history = new Stack<Views>();
    private static Session session = new Session();

    public static void renderView(Views v) {
        view = v.getPath();
        view_history.push(v);
    }
    
    public static void returnView() {
        view = view_history.pop().getPath();
    }

    public static void startGame() {
        session.setActivity(true);
        session.render();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
    }
}
