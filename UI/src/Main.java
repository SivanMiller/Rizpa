//import engine.*;
//import exception.*;
//import objects.StockDTO;
//
//import javax.xml.bind.JAXBException;
//import java.io.FileNotFoundException;
//import java.util.*;
//
//public class Main {
//    private static RizpaMethods engine = new Engine();
//    private static Scanner scanner = new Scanner(System.in);
//    private static boolean isLoadSucc = false;
//
//    private static final String LOAD            = "1";
//    private static final String INIT_BYE        = "2";
//    private static final String ALL_STOCKS      = "2";
//    private static final String SINGLE_STOCK    = "3";
//    private static final String NEW_COMMAND     = "4";
//    private static final String ALL_COMMANDS    = "5";
//    private static final String BYE             = "6";
//
//    private static final String BUY_COMMAND     = "1";
//    private static final String SELL_COMMAND    = "2";
//
//    private static final String LMT_COMMAND     = "1";
//    private static final String MKT_COMMAND    = "2";
//
//
//    private static void menu() {
//        System.out.println("**********************************************************");
//        System.out.println("1 - Load XML file ");
//        System.out.println("2 - Print Stocks ");
//        System.out.println("3 - Print Single Stock ");
//        System.out.println("4 - Execute trading order ");
//        System.out.println("5 - Print the lists of commands to execute");
//        System.out.println("6 - EXIT");
//        System.out.println("**********************************************************");
//    }
//
//    private static void initialMenu() {
//        System.out.println("**********************************************************");
//        System.out.println("Please enter your choice");
//        System.out.println("1 - Load XML file ");
//        System.out.println("2 - EXIT");
//        System.out.println("**********************************************************");
//    }
//
//    // return -1 if the input is under 0 or if the input is not int
//    private static int checkInputValue(String input) throws NumberFormatException {
//        try {
//            int res = Integer.parseInt(input);
//            //If input is negative
//            if (res <= 0)
//                return -1;
//            else
//                return res;
//        } catch (NumberFormatException e) {
//            return -1;
//        }
//    }
//
//    private static void printStocks() {
//        List<StockDTO> stocks = engine.getAllStocks();
//
//        for (StockDTO stock : stocks) {
//            System.out.println(stock.toString());
//        }
//    }
//
//    private static void printOneStock() {
//        System.out.println("Enter the stock SYMBOL");
//        String stockSymbol  =scanner.next();
//        stockSymbol = stockSymbol.toUpperCase(Locale.ROOT);
//        try {
//            System.out.println(engine.getStock(stockSymbol).toString() +
//                    engine.getStock(stockSymbol).PrintTransaction());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private static void newCommand() {
//
////        System.out.println("Enter the stock SYMBOL");
////        String stockSymbol = scanner.next();
////        stockSymbol = stockSymbol.toUpperCase(Locale.ROOT);
////        if (!engine.doesStockExists(stockSymbol)) {
////            System.out.println("ERROR. Stock doesn't exist. Please try again.");
////            return;
////        }
////
////        System.out.println("Enter the command direction." + '\n' +
////                "1 - for a buy command" + '\n' +
////                "2 - for a sell command");
////        String CmdDirection = scanner.next();
////        if (!CmdDirection.equals(BUY_COMMAND)  && !CmdDirection.equals(SELL_COMMAND)) {
////            System.out.println("Invalid command direction. Please try again");
////            return;
////        }
////
////        System.out.println("Enter the command Type." + '\n' +
////                "1 - for a LMT command" + '\n' +
////                "2 - for a MKT command");
////        String CmdType = scanner.next();
////        if (!CmdType.equals(MKT_COMMAND)  && !CmdType.equals(LMT_COMMAND)) {
////            System.out.println("Invalid command Type. Please try again");
////            return;
////        }
////        int Price = 0;
////        if (!CmdType.equals(MKT_COMMAND)) {
////            System.out.println("Enter the command price");
////            String commandPrice = scanner.next();
////            Price = checkInputValue(commandPrice);
////            if (Price == -1) {
////                System.out.println("ERROR. The command price must be a positive number");
////                return;
////            }
////        }
////
////        System.out.println("Enter the command quantity");
////        String commandQuantity = scanner.next();
////        int Quantity = checkInputValue(commandQuantity);
////        if (Quantity == -1) {
////            System.out.println("ERROR. The command quantity must be a positive number");
////            return;
////        }
////
////        try {
////            NewCmdOutcomeDTO newCommand = engine.addCommand(stockSymbol, CmdType ,CmdDirection, Price, Quantity);
////            System.out.println(newCommand.toString());
////        }
////        catch (NoSuchStockException | CommandNegPriceException | StockNegQuantityException | NoSuchCmdDirectionException | NoSuchCmdTypeException e) {
////            System.out.println(e.getMessage());
////        }
//    }
//
//    private static void printAllCommands() {
//
//        List<StockDTO> stocks = engine.getAllStocks();
//
//        for (StockDTO stock : stocks) {
//            System.out.println(stock.PrintAllCommands());
//        }
//    }
//
//    private static void exeAction(String input) {
//        switch (input) {
//            case LOAD:
//                loadFile();
//                break;
//            case ALL_STOCKS:
//                printStocks();
//                break;
//            case SINGLE_STOCK:
//                printOneStock();
//                break;
//            case NEW_COMMAND:
//                newCommand();
//                break;
//            case ALL_COMMANDS:
//                printAllCommands();
//                break;
//            case BYE:
//                System.out.println("Thank you for using Rizpa! See you later!");
//                return;
//            default:
//                System.out.println("ERROR WRONG INPUT. Please enter 1-6");
//                break;
//        }
//    }
//
//    public static void loadFile() {
//        System.out.println("Please enter an XML file ");
//        String fileName = scanner.next();
//        fileName += scanner.nextLine();
//        if(!fileName.endsWith(".xml")) {
//            System.out.println("The file doesn't end with XML!");
//            if(isLoadSucc)
//                System.out.println("The system will continue with the last version.");
//            return;
//        }
//
//        try {
//            engine.loadXML(fileName);
//            isLoadSucc = true;
//            System.out.println("File loaded successfully! Let's get started!");
//
//        } catch (StockNegPriceException | XMLException | FileNotFoundException | JAXBException | StockSymbolLowercaseException e) {
//            System.out.println(e.getMessage());
//            System.out.println("File not loaded");
//
//            if (isLoadSucc == true) {
//                System.out.println("The system will continue with the last version.");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        String input;
//
//        // Initial menu, will continue until a file will be loaded or BYE as chosen
//        while (isLoadSucc == false) {
//
//            initialMenu();
//            input = scanner.next();
//
//            switch (input) {
//                case LOAD: {
//                    loadFile();
//                    break;
//                }
//                case INIT_BYE: {
//                    System.out.println("Thank you for using Rizpa! See you later!");
//                    return;
//                }
//                default: {
//                    System.out.println("Wrong action. Please enter 1-2");
//                    break;
//                }
//            }
//        }
//
//        //Primary menu, will continue until BYE was chosen
//        menu();
//        input = scanner.next();
//        while (!input.equals(BYE)) {
//            exeAction(input);
//            menu();
//            input = scanner.next();
//        }
//
//        System.out.println("Thank you for using Rizpa! See you later!");
//        return;
//    }
//}










import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public final static String HEADER_fXML_RESOURCE = "/app/app3.fxml";

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(HEADER_fXML_RESOURCE);
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
