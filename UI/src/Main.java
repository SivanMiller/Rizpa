import engine.Engine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Engine e = new Engine();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.next();

        System.out.println(e.hello(name));

    }
}
