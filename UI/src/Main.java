import engine.Engine;
import exceptions.StockNegPriceException;
import exceptions.XMLException;
import objects.StockDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static Engine en = new Engine();
    private static Scanner scanner = new Scanner(System.in);

    private static final String LOAD="1";
    private static final String ALL_STOCKS="2";
    private static final String SINGLE_STOCK="3";
    private static final String NEW_COMMAND="4";
    private static final String ALL_COMMANDS="5";
    private static final String BYE="6";
    private static final String BUY_COMMAND="1";
    private static final String SELL_COMMAND="6";


    private static void menu()
    {
        System.out.println("1- Load XML file ");
        System.out.println("2- Print Stocks ");
        System.out.println("3- Print Single Stock ");
        System.out.println("4- Execute trading order ");
        System.out.println("5- Print the lists of commands to execute");
        System.out.println("6- EXIT");
    }
    private static void InitialMenu()
    {
        System.out.println("please enter your choice");
        System.out.println("1- Load XML file ");
        System.out.println("2- EXIT");

    }

    // return -1 if the input is under 0 or if the input is not int
    private static int checkInputValue(String input) throws NumberFormatException {
        try {
            int res = Integer.parseInt(input);
            if (res < 0)
                return -1;
            else
                return res;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void PrintStocks()
    {
        List<StockDTO> list = en.getAllStocks();

        for (StockDTO stock : list) {
            System.out.println(stock.toString());
        }
    }

    private static void PrintOneStock()
    {
        System.out.println("enter the SYMBOL stock");
        String stockSymbol=scanner.next();
        stockSymbol.toUpperCase(Locale.ROOT);
        try {
            System.out.println(en.getStock(stockSymbol).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void NewCommand() {
        System.out.println("enter the command price");
        String commandPrice = scanner.next();
        int nPrice = checkInputValue(commandPrice);
        if (nPrice == -1)
            System.out.println("ERROR. the command price must be a positive number");
        System.out.println("enter the command quantity");
        String commandQuantity = scanner.next();
        int nQuantity = checkInputValue(commandQuantity);
        if (nQuantity == -1)
            System.out.println("ERROR. the command quantity must be a positive number");

        System.out.println("enter the command type. 1- for a buy command , 2- for a sell command");
        String commandType = scanner.next();
        boolean flag = false;
        while (!flag)
            switch (commandType) {
                case BUY_COMMAND:
                case SELL_COMMAND:
                    flag = true;
                    break;
                default:
                    System.out.println("ERROR wrong input. 1- for a buy command , 2- for a sell command");
                    commandType = scanner.next();

                    break;
            }
        System.out.println("enter the SYMBOL stock");
        String stockSymbol = scanner.next();
        stockSymbol.toUpperCase(Locale.ROOT);
    }

    private static void PrintAllCommand() {

    }

    private static void Exe(String input) {
        switch (input) {
            case LOAD:
                Load();
                break;
            case ALL_STOCKS:
                PrintStocks();
                break;
            case ALL_COMMANDS:
                PrintAllCommand();
                break;
            case SINGLE_STOCK:
                PrintOneStock();
                break;
            case NEW_COMMAND:
                NewCommand();
                break;
            case BYE:
                return;
            default:
                System.out.println("ERROR WRONG INPUT.please enter 1-6");
                break;
        }
    }

    public static void Load() {
        System.out.println("please enter an XML file ");
        String fileName = scanner.next();
        while (!fileName.endsWith(".xml")) {
            System.out.println("the file doesnt end with XML!please enter again an EXL file ");
            fileName = scanner.next();
        }

        try {
            en.LoadXML(fileName);

        } catch (StockNegPriceException | XMLException | FileNotFoundException | JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        InitialMenu();
        String input= scanner.next();
        if(input== BYE)
        {
            return;
        }
        Load();
        menu();
        input=scanner.next();
        while (input!= BYE)
        {
            Exe(input);
            menu();
            input=scanner.next();
        }
    }
}
