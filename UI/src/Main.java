import engine.Engine;
import engine.LMTCommand;
import engine.Command;
import engine.ExchangeCollection;
import exceptions.StockNegPriceException;
import exceptions.XMLException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public enum InputType {
        LOAD,
        ALL_STOCKS,
        SINGLE_STOCK,
        NEW_COMMAND,
        ALL_COMMANDS,
        BYE
    };

    static void menu()
    {
        System.out.println("1- Load XML file ");
        System.out.println("2- Print Stocks ");
        System.out.println("3- Print Single Stock ");
        System.out.println("4- Execute trading order ");
        System.out.println("5- Print the lists of commands to execute");
        System.out.println("6- EXIT");
    }

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        int input= scanner.nextInt();
        Engine en = new Engine();

        try {
            en.LoadXML("C:\\Users\\netas\\Downloads\\ex1-small.xml");

        }
        catch (StockNegPriceException | XMLException | FileNotFoundException | JAXBException e)
        {
            System.out.println(e.getMessage());
        }
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

    }
}
