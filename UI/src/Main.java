import engine.LMTCommand;
import engine.Command;
import engine.ExchangeCollection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Command cmd = new LMTCommand(100,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.Type.BUY);
        ExchangeCollection ex = new ExchangeCollection();
        ex.addBuyCommand(cmd);


        cmd = new LMTCommand(90,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.Type.SELL);
        ex.addSellCommand(cmd);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.next();


    }
}
