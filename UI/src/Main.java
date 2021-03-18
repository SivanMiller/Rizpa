import engine.LMTCommand;
import engine.Command;
import engine.ExchangeCollection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ExchangeCollection ex = new ExchangeCollection();

        // 1
        Command cmd = new LMTCommand(100,
                20,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 2
        cmd = new LMTCommand(110,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 3
        cmd = new LMTCommand(90,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 4
        cmd = new LMTCommand(90,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 5
        cmd = new LMTCommand(120,
                25,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 6
        cmd = new LMTCommand(90,
                30,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 7
        cmd = new LMTCommand(80,
                20,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 8
        cmd = new LMTCommand(100,
                10,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 9
        cmd = new LMTCommand(90,
                5,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.BUY);
        ex.addNewCommand(cmd);

        // 10
        cmd = new LMTCommand(90,
                5,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 11
        cmd = new LMTCommand(95,
                25,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 12
        cmd = new LMTCommand(90,
                20,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 13
        cmd = new LMTCommand(90,
                12,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        // 14
        cmd = new LMTCommand(85,
                13,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()).toString(),
                Command.CmdType.SELL);
        ex.addNewCommand(cmd);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.next();


    }
}
