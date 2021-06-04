package adminTab.adminDetails;

import adminTab.AdminTabController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import objects.CommandDTO;
import objects.ExchangeCollectionDTO;
import objects.TransactionDTO;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminDetailsController implements Initializable {
    private AdminTabController mainController;
    @FXML private Label stockListLabel;
    @FXML private Label buyCommandLabel;
    @FXML private Label sellCommandLabel;
    @FXML private Label transactionLabel;
    @FXML private ComboBox<String> stockSymbol;
    @FXML private TableView<CommandDTO> buyCommandTableView;

    @FXML    private TableColumn<CommandDTO, String>  buyDate;
    @FXML    private TableColumn<CommandDTO, Integer> buyQuantity;
    @FXML    private TableColumn<CommandDTO, Integer> buyPrice;
    @FXML    private TableColumn<CommandDTO, String>  buyUser;
    @FXML    private TableColumn<CommandDTO, String>  buyType;
    private ObservableList<CommandDTO> buyListData;

    @FXML private TableView<CommandDTO> sellCommandTableView;
    @FXML    private TableColumn<CommandDTO, String> sellDate;
    @FXML    private TableColumn<CommandDTO, Integer> sellQuantity;
    @FXML    private TableColumn<CommandDTO, Integer> sellPrice;
    @FXML    private TableColumn<CommandDTO, String> sellUser;
    @FXML    private TableColumn<CommandDTO, String> sellType;
    private ObservableList<CommandDTO> sellListData;

    @FXML    private TableView<TransactionDTO> transactionTableView;
    @FXML    private TableColumn<TransactionDTO, String>  transactionDate;
    @FXML    private TableColumn<TransactionDTO, Integer> transactionQuantity;
    @FXML    private TableColumn<TransactionDTO, Integer> transactionPrice;
    @FXML    private TableColumn<TransactionDTO, String>  transactionBuyUser;
    @FXML    private TableColumn<TransactionDTO, String>  transactionSellUser;
    private ObservableList<TransactionDTO> tranListData;

    @FXML private LineChart<String,Number> stockHistoryChart;
    @FXML private Label stockHistoryChartLabel;
    @FXML private ScrollPane stockHistoryChartScroll;

    private SimpleBooleanProperty showProperty;
    public AdminDetailsController() {
       this.showProperty = new SimpleBooleanProperty(false);
    }
    public void setMainController(AdminTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buyDate.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("Date"));
        buyType.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("Type"));
        buyQuantity.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("Quantity"));
        buyPrice.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("Price"));
        buyUser.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("User"));

        sellDate.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("Date"));
        sellType.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("Type"));
        sellQuantity.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("Quantity"));
        sellPrice.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("Price"));
        sellUser.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("User"));

        transactionDate.setCellValueFactory(new PropertyValueFactory<TransactionDTO, String>("transactionDate"));
        transactionSellUser.setCellValueFactory(new PropertyValueFactory<TransactionDTO, String>("transactionSellUser"));
        transactionQuantity.setCellValueFactory(new PropertyValueFactory<TransactionDTO, Integer>("transactionQuantity"));
        transactionPrice.setCellValueFactory(new PropertyValueFactory<TransactionDTO, Integer>("transactionPrice"));
        transactionBuyUser.setCellValueFactory(new PropertyValueFactory<TransactionDTO, String>("transactionBuyUser"));

        buyCommandTableView.visibleProperty().bind(showProperty);
        sellCommandTableView.visibleProperty().bind(showProperty);
        transactionTableView.visibleProperty().bind(showProperty);
        buyCommandLabel.visibleProperty().bind(showProperty);
        sellCommandLabel.visibleProperty().bind(showProperty);
        transactionLabel.visibleProperty().bind(showProperty);
        stockHistoryChart.visibleProperty().bind(showProperty);
        stockHistoryChartLabel.visibleProperty().bind(showProperty);
    }

    public void setStocksComboBox(List<String> stocksNameList) {
        ObservableList<String> list = FXCollections.observableArrayList(stocksNameList);
        this.stockSymbol.setItems(list);
    }

    @FXML
    public void onActionChoseStock()
    {
        String chosenStockName = stockSymbol.getValue();
        if  (chosenStockName != null) {
            showProperty.set(true);
            setCommandTables(mainController.getExchangeCollectionDtoForStock(chosenStockName));

            //Price History Graph
            List<Pair<String, Integer>> priceHistory = mainController.getStockHistory(chosenStockName);
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            for (int i = 0; i < priceHistory.size(); i++) {
                series.getData().add(new XYChart.Data(priceHistory.get(i).getKey(),
                        priceHistory.get(i).getValue()));
            }
            stockHistoryChart.getData().setAll(series);
        }
    }

    public void setCommandTables(ExchangeCollectionDTO data) {

       buyListData = FXCollections.observableArrayList(data.getBuyCommand());
        buyCommandTableView.setItems(this.buyListData);

        sellListData= FXCollections.observableArrayList(data.getSellCommand());
        sellCommandTableView.setItems(this.sellListData);

        tranListData= FXCollections.observableArrayList(data.getTransaction());
        transactionTableView.setItems(this.tranListData);
    }

    public void resetTab(){
        showProperty.set(false);
        stockSymbol.getSelectionModel().clearSelection();
    }
}


