package adminTab;


import adminTab.adminDetails.AdminDetailsController;
import app.AppController;
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

    public void createAdminTab(){
        adminDetailsController.setStocksComboBox(mainController.getAllStocks());

    }

    public ExchangeCollectionDTO getExchangeCollectionDtoForStock(String stockName)
    {
        return mainController.getExchangeCollectionForStock(stockName);
    }

    @FXML
    public void tabChanged(){
        mainController.clearMessages();
        adminDetailsController.resetTab();
    }
}
