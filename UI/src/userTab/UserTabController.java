package userTab;

import addCommand.AddCommandController;
import app.AppController;
import engine.User;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import userDetails.UserDetailsController;

public class UserTabController {
    @FXML private GridPane userDetails;
    @FXML private GridPane addCommand;
    @FXML private UserDetailsController userDetailsController;
    @FXML private AddCommandController addCommandController;
    private AppController mainController;


    @FXML
    private void initialize() {

    userDetailsController.setMainController(this);
    }

        public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUserDetailsController(UserDetailsController userDetailsController) {
        this.userDetailsController = userDetailsController;
    }

    public void setAddCommandController(AddCommandController addCommandController) {
        this.addCommandController = addCommandController;
    }

    public void addUserTab(User user){
        userDetailsController.setUserName(user.getName());
        userDetailsController.setStockSum("0");
        userDetailsController.setStocksTable(user.getHoldings());

    }

    public void toggleAddCommand()
    {
        addCommandController.togggleDisplay();
    }
}
