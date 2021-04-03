import engine.*;
import exceptions.*;
import objects.StockDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static Engine engine = new Engine();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isLoadSucc = false;

    private static final String LOAD            = "1";
    private static final String INIT_BYE        = "2";
    private static final String ALL_STOCKS      = "2";
    private static final String SINGLE_STOCK    = "3";
    private static final String NEW_COMMAND     = "4";
    private static final String ALL_COMMANDS    = "5";
    private static final String BYE             = "6";
    private static final String BUY_COMMAND     = "1";
    private static final String SELL_COMMAND    = "2";


    private static void menu()
    {
        System.out.println("**********************************************************");
        System.out.println("1 - Load XML file ");
        System.out.println("2 - Print Stocks ");
        System.out.println("3 - Print Single Stock ");
        System.out.println("4 - Execute trading order ");
        System.out.println("5 - Print the lists of commands to execute");
        System.out.println("6 - EXIT");
        System.out.println("**********************************************************");
    }

    private static void InitialMenu()
    {
        System.out.println("**********************************************************");
        System.out.println("Please enter your choice");
        System.out.println("1 - Load XML file ");
        System.out.println("2 - EXIT");
        System.out.println("**********************************************************");
    }

    // return -1 if the input is under 0 or if the input is not int
    private static int checkInputValue(String input) throws NumberFormatException {
        try {
            int res = Integer.parseInt(input);
            //If input is negative
            if (res <= 0)
                return -1;
            else
                return res;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void PrintStocks()
    {
        List<StockDTO> list = engine.getAllStocks();

        for (StockDTO stock : list) {
            System.out.println(stock.toString());
        }
    }

    private static void PrintOneStock()
    {
        System.out.println("Enter the stock SYMBOL");
        String stockSymbol=scanner.next();
        stockSymbol = stockSymbol.toUpperCase(Locale.ROOT);
        try {
            System.out.println(engine.getStock(stockSymbol).toString()+engine.getStock(stockSymbol).PrintTransaction());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    private static void NewCommand() {

        System.out.println("Enter the stock SYMBOL");
        String stockSymbol = scanner.next();
        stockSymbol = stockSymbol.toUpperCase(Locale.ROOT);
        if (!engine.doesStockExists(stockSymbol))
        {
            System.out.println("ERROR. Stock doesn't exist. Please try again.");
            return;
        }

        System.out.println("Enter the command price");
        String commandPrice = scanner.next();
        int nPrice = checkInputValue(commandPrice);
        if (nPrice == -1) {
            System.out.println("ERROR. The command price must be a positive number");
            return;
        }

        System.out.println("Enter the command quantity");
        String commandQuantity = scanner.next();
        int nQuantity = checkInputValue(commandQuantity);
        if (nQuantity == -1) {
            System.out.println("ERROR. The command quantity must be a positive number");
            return;
        }

        System.out.println("Enter the command type." + '\n' +
                            "1 - for a buy command" + '\n' +
                            "2 - for a sell command");
        String commandType = scanner.next();
        if(!commandType.equals(BUY_COMMAND)  && !commandType.equals(SELL_COMMAND)) {
            System.out.println("ERROR command type. Please try again");
            return;
        }

        try {
            engine.addCommand(stockSymbol, commandType, nPrice, nQuantity);
            System.out.println("New command added successfully!");
        }
        catch (NoSuchStockException | StockNegPriceException | StockNegQuantityException | NoSuchCmdTypeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void PrintAllCommand() {

        List<StockDTO> list = engine.getAllStocks();

        for (StockDTO stock : list) {
            System.out.println(stock.PrintAllCommands());
        }

    }

    private static void Exe(String input) {
        switch (input) {
            case LOAD:
                Load();
                break;
            case ALL_STOCKS:
                PrintStocks();
                break;
            case SINGLE_STOCK:
                PrintOneStock();
                break;
            case ALL_COMMANDS:
                PrintAllCommand();
                break;
            case NEW_COMMAND:
                NewCommand();
                break;
            case BYE:
                System.out.println("Thank you for using Rizpa! See you later!");
                return;
            default:
                System.out.println("ERROR WRONG INPUT. Please enter 1-6");
                break;
        }
    }

    public static void Load() {
        System.out.println("Please enter an XML file ");
        String fileName = scanner.next();
        while (!fileName.endsWith(".xml")) {
            System.out.println("The file doesn't end with XML!please enter again an XML file ");
            fileName = scanner.next();
        }

        try {
            engine.LoadXML(fileName);
            isLoadSucc = true;
            System.out.println("File loaded successfully! Let's get started!");

        } catch (StockNegPriceException | XMLException | FileNotFoundException | JAXBException e) {
            System.out.println(e.getMessage());
            if (isLoadSucc == true)
            {
                System.out.println("The system will continue with the last version." + '\n');
            }
        }
    }

    public static void main(String[] args) {

        InitialMenu();
        String input = scanner.next();
        while (isLoadSucc == false) {
            switch (input) {
                case LOAD: {
                    Load();
                    break;
                }
                case INIT_BYE: {
                    System.out.println("Thank you for using Rizpa! See you later!");
                    return;
                }
                default: {
                    System.out.println("Wrong action.");
                    InitialMenu();
                    input = scanner.next();
                    break;
                }
            }
        }

        menu();
        input=scanner.next();
        while (!input.equals(BYE)) {
            Exe(input);
            menu();
            input = scanner.next();
        }

        System.out.println("Thank you for using Rizpa! See you later!");
        return;
    }
}
