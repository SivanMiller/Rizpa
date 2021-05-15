package adminTab;


import adminTab.adminDetails.AdminDetailsController;
import app.AppController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import objects.ExchangeCollectionDTO;

import java.util.List;
import java.util.Map;

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

    public List<Pair<String, Integer>> getStockHistory(String Symbol){
        return mainController.getStockHistory(Symbol);
    }

    @FXML
    public void tabChanged(){
        mainController.clearMessages();
        adminDetailsController.resetTab();
    }
}
