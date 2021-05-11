package app;

import engine.Engine;
import engine.Holding;
import engine.Stock;
import engine.User;
import exception.*;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import messages.MessagesController;
import objects.StockDTO;
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
    @FXML private AnchorPane messages;
    @FXML private TabPane usersTabPane;
    @FXML private GridPane header;

    private Engine engine;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        if (headerController != null && messagesController != null) {
            headerController.setMainController(this);
            messagesController.setMainController(this);
        }
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

    public void loadXML(String filePath){
        try {
            messagesController.clearMessages();
            engine.loadXML(filePath);
            this.createUserTabs();
            messagesController.addMessage("File loaded successfully!");
        } catch (StockNegPriceException | XMLException | FileNotFoundException |
                 JAXBException | StockSymbolLowercaseException e) {
            messagesController.addMessage(e.getMessage());
            messagesController.addMessage("The system will continue with the last version.");
        }
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
                userTabController.setMainController(this);
                userTab.setText(user.getName());
                userTabController.addUserTab(user);

                usersTabPane.getTabs().add(userTab);

            } catch (IOException e) {
                messagesController.addMessage(e.getMessage());
            }
        }

    }

    public List<String> getAllStocks(){
        List<String> stocks = new ArrayList<>();

        for (StockDTO stock : engine.getAllStocks())
        {
            stocks.add(stock.getSymbol());
        }

        return stocks;
    }

    public List<String> getUserStocks(String userName){
        List<String> stocks = new ArrayList<>();

        for (Holding holding : engine.getUsers().get(userName).getHoldings())
        {
            stocks.add(holding.getStock().getSymbol());
        }

        return stocks;
    }

    public void addCommand(String Symbol, String Type , String CmdDirection, String Price, String Quantity) {

        try {
            messagesController.clearMessages();
            engine.addCommand(Symbol, Type, CmdDirection, 0, 0);
        }
        catch (NoSuchStockException | CommandNegPriceException | StockNegQuantityException | NoSuchCmdDirectionException | NoSuchCmdTypeException e) {
            messagesController.addMessage(e.getMessage());

        }
    }
}
