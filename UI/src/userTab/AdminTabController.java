package userTab;


import app.AppController;
import engine.User;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import objects.ExchangeCollectionDTO;

public class AdminTabController {
    @FXML private GridPane adminDetails;
    @FXML private AdminDetailsController adminDetailsController;
    private AppController mainController;

    @FXML
    private void initialize() {
        adminDetailsController.setMainController(this);

    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setCommandTables(){

    }
    public void addAdminTab(){
        adminDetailsController.setStocksComboBox(mainController.getAllStocks());

    }

    public ExchangeCollectionDTO getExchangeCollectionDtoForStock(String stockName)
    {
        return mainController.getExchangeCollectionForStock(stockName);
    }
}
