package app;

import engine.*;
import exception.*;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import messages.MessagesController;
import objects.ExchangeCollectionDTO;
import objects.NewCmdOutcomeDTO;
import objects.StockDTO;
import userTab.AdminTabController;
import userTab.UserTabController;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppController {

    @FXML private HeaderController headerController;
    @FXML private MessagesController messagesController;
    @FXML private AdminTabController adminTabController;
    @FXML private AnchorPane messages;
    @FXML private TabPane usersTabPane;
    @FXML private GridPane header;

    private List<UserTabController> tabControllersList;
    private Engine engine;
    private Stage primaryStage;
    private boolean isXMLLoaded = false;



    @FXML
    public void initialize() {
        if (headerController != null && messagesController != null) {
            headerController.setMainController(this);
            messagesController.setMainController(this);
        }
        tabControllersList = new ArrayList<>();
    }


    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setHeaderController(HeaderController headerController) {
        this.headerController = headerController;
        this.headerController.setMainController(this);
    }

    public void setMessagesController(MessagesController messagesController) {
        this.messagesController = messagesController;
        this.messagesController.setMainController(this);
    }

    public void clearMessages(){
        messagesController.clearMessages();
    }

    public void loadXML(String filePath){
        try {
            messagesController.clearMessages();
            engine.loadXML(filePath);
            this.createUserTabs();
            messagesController.addMessage("File loaded successfully!");
            isXMLLoaded = true;
        } catch (StockNegPriceException | XMLException | FileNotFoundException |
                 JAXBException | StockSymbolLowercaseException e) {
            messagesController.addMessage(e.getMessage());
            if (isXMLLoaded)
                messagesController.addMessage("The system will continue with the last version.");
        }
    }

    private void refreshTabs(){
        for (UserTabController controller : tabControllersList){
            controller.addUserTab(engine.getUsers().get(controller.getUserName()));
        }
        //adminTabController.setBuyCommandTable(engine.getExchangeCollectionDTO("GOOGL"));
    }

    private void createUserTabs() {
        usersTabPane.getTabs().clear();
        for(User user : engine.getUsers().values())
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL url = getClass().getResource("/userTab/userTab.fxml");
                loader.setLocation(url);
                Tab userTab = loader.load();

                UserTabController userTabController = loader.getController();
                tabControllersList.add(userTabController);
                userTabController.setMainController(this);
                userTab.setText(user.getName());
                userTabController.addUserTab(user);

                usersTabPane.getTabs().add(userTab);

            } catch (IOException e) {
                messagesController.addMessage(e.getMessage());
            }
        }
        createAdminTab();
    }

    private void createAdminTab() {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL url = getClass().getResource("/userTab/adminTab.fxml");
                loader.setLocation(url);
                Tab adminTab = loader.load();

                this.adminTabController = loader.getController();
                usersTabPane.getTabs().add(adminTab);
                this.adminTabController.setMainController(this);
                this.adminTabController.addAdminTab();
               //this.adminTabController.setBuyCommandTable(engine.getExchangeCollectionDTO("GOOGL"));

            } catch (IOException e) {
                messagesController.addMessage(e.getMessage());
            }



    }

    public List<String> getAllStocks(){
        List<String> stocks = new ArrayList<>();

        for (StockDTO stock : engine.getAllStocks())
        {
            stocks.add(stock.getStockSymbol());
        }

        return stocks;
    }

    public List<String> getUserStocks(String userName){
        List<String> stocks = new ArrayList<>();

        for (Holding holding : engine.getUsers().get(userName).getHoldings().values())
        {
            stocks.add(holding.getStock().getSymbol());
        }

        return stocks;
    }

    public boolean addCommand(String userName, String Symbol, Stock.CmdType Type , Command.CmdDirection CmdDirection, String Price, String Quantity) {

        try {
            messagesController.clearMessages();
            int quantity = convertStringToInt(Quantity);
            int price = convertStringToInt(Price);
            NewCmdOutcomeDTO newCmdOutcomeDTO = engine.addCommand(userName, Symbol, Type, CmdDirection, price, quantity);
            refreshTabs();
            messagesController.addMessage(newCmdOutcomeDTO.toString());
            return true;
        }
        catch (NoSuchStockException | CommandNegPriceException | StockNegQuantityException | NoSuchCmdDirectionException | NoSuchCmdTypeException e) {
            messagesController.addMessage(e.getMessage());
            return false;

        } catch (Exception e) {
            messagesController.addMessage(e.getMessage());
            return false;
        }
    }

    public int convertStringToInt(String str) throws Exception {
        try {
            int res = Integer.parseInt(str);
            //If input is negative
            return res;
        } catch (NumberFormatException e) {
            throw new Exception("Please enter only numbers");
        }
    }
    public ExchangeCollectionDTO getExchangeCollectionForStock(String stockSymbol)
    {
       return engine.getExchangeCollectionDTO(stockSymbol);
    }
}
