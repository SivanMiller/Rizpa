package userTab;

import objects.UserDTO;
import userTab.addCommand.AddCommandController;
import app.AppController;
import engine.Command;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import userTab.userDetails.UserDetailsController;

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

    public void createUserTab(UserDTO user){
        userDetailsController.setUserName(user.getName());
        userName = user.getName();
        userDetailsController.setHoldingsSum(user.getHoldingsTurnover());
        userDetailsController.setStocksTable(user.getHoldings());
    }

    public void toggleAddCommand()
    {
        addCommandController.DisplayBuySell();
        clearMessages();
    }

    public List<String> getAllStocks() {
        return mainController.getAllStocks();
    }

    public List<String> getUserStocks(){
        return mainController.getUserStocks(userDetailsController.getUserNameString());
    }

    public boolean addCommand(String Symbol, Command.CmdType Type , Command.CmdDirection CmdDirection, String  Price, String Quantity)
    {
        return mainController.addCommand(userName, Symbol, Type, CmdDirection, Price, Quantity);
    }

    @FXML
    private void changedTab(){
        //clearMessages(); //TODO check why deleted
        addCommandController.resetVisibility();
    }

    public void clearMessages(){
        mainController.clearMessages();
    }
}
