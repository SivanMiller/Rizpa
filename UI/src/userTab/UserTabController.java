package userTab;

import addCommand.AddCommandController;
import app.AppController;
import engine.Holding;
import engine.Stock;
import engine.User;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import userDetails.UserDetailsController;

import java.util.ArrayList;
import java.util.List;

public class UserTabController {
    @FXML private GridPane userDetails;
    @FXML private GridPane addCommand;
    @FXML private UserDetailsController userDetailsController;
    @FXML private AddCommandController addCommandController;
    private AppController mainController;
    private String userName;

    @FXML
    private void initialize() {
        userDetailsController.setMainController(this);
        addCommandController.setMainController(this);
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUserDetailsController(UserDetailsController userDetailsController) {
        this.userDetailsController = userDetailsController;
    }

    public String getUserName() {
        return userName;
    }

    public void setAddCommandController(AddCommandController addCommandController) {
        this.addCommandController = addCommandController;
    }

    public void addUserTab(User user){
        userDetailsController.setUserName(user.getName());
        userName = user.getName();
        userDetailsController.setStockSum("0");
        userDetailsController.setStocksTable(user.getHoldings());

    }

    public void toggleAddCommand()
    {
        addCommandController.togggleDisplay();
    }

    public List<String> getAllStocks() {
        return mainController.getAllStocks();
    }

    public List<String> getUserStocks(){
        return mainController.getUserStocks(userDetailsController.getUserNameString());
    }

    public void addCommand(String Symbol, String Type , String CmdDirection, String  Price, String Quantity)
    {
        mainController.addCommand(userName, Symbol, Type, CmdDirection, Price, Quantity);
    }
    @FXML
    private void changedTab(){
        mainController.clearMessages();
    }
}
