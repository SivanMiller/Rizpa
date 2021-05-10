package app;

import engine.Engine;
import engine.User;
import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
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
import userTab.UserTabController;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

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
            engine.loadXML(filePath);
            this.createUserTabs();
        } catch (StockNegPriceException | XMLException | FileNotFoundException |
                 JAXBException | StockSymbolLowercaseException e) {
            e.printStackTrace(); //TODO: MESSAGES
        }
    }

    private void createUserTabs() {
        for(User user : engine.getUsers().values())
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL url = getClass().getResource("/userTab/userTab.fxml");
                loader.setLocation(url);
                Tab userTab = loader.load();

                UserTabController userTabController = loader.getController();
                userTab.setText(user.getName());
                userTabController.addUserTab(user);

                usersTabPane.getTabs().add(userTab);

            } catch (IOException e) {
                e.printStackTrace(); // TODO Messages

            }
        }

    }
}
