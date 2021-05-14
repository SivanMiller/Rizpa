package userTab;

import app.AppController;
import engine.Holding;
import engine.Stock;
import engine.Transaction;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.CommandDTO;
import objects.ExchangeCollectionDTO;
import objects.StockDTO;
import objects.TransactionDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminDetailsController implements Initializable {
    private AdminTabController mainController;
    @FXML
    private Label stockListLabel;
    @FXML
    private Label buyCommandLabel;
    @FXML
    private Label sellCommandLabel;
    @FXML
    private Label transactionLabel;
    @FXML
    private ComboBox<String> stockComboBox;
    @FXML
    private TableView<CommandDTO> buyCommandTableView;

    @FXML    private TableColumn<CommandDTO, String> buyDate;
    @FXML    private TableColumn<CommandDTO, Integer> buyQuantity;
    @FXML    private TableColumn<CommandDTO, Integer> buyPrice;
    @FXML    private TableColumn<CommandDTO, String> buyUser;
    @FXML    private TableColumn<CommandDTO, String> buyType;
    private ObservableList<CommandDTO> buyListData;

    @FXML private TableView<CommandDTO> sellCommandTableView;
    @FXML    private TableColumn<CommandDTO, String> sellDate;
    @FXML    private TableColumn<CommandDTO, Integer> sellQuantity;
    @FXML    private TableColumn<CommandDTO, Integer> sellPrice;
    @FXML    private TableColumn<CommandDTO, String> sellUser;
    @FXML    private TableColumn<CommandDTO, String> sellType;
    private ObservableList<CommandDTO> sellListData;

    @FXML    private TableView<TransactionDTO> transactionTableView;
    @FXML    private TableColumn<TransactionDTO, String> transactionDate;
    @FXML    private TableColumn<TransactionDTO, Integer> transactionQuantity;
    @FXML    private TableColumn<TransactionDTO, Integer> transactionPrice;
    @FXML    private TableColumn<TransactionDTO, String> transactionBuyUser;
    @FXML    private TableColumn<TransactionDTO, String> transactionSellUser;
    private ObservableList<TransactionDTO> tranListData;

    private SimpleBooleanProperty showProperty;
    public AdminDetailsController() {
       this.showProperty = new SimpleBooleanProperty(false);
    }
    public void setMainController(AdminTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buyDate.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("buyDate"));
        buyType.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("buyType"));
        buyQuantity.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("buyQuantity"));
        buyPrice.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("buyPrice"));
        buyUser.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("buyUser"));

        sellDate.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("sellDate"));
        sellType.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("sellType"));
        sellQuantity.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("sellQuantity"));
        sellPrice.setCellValueFactory(new PropertyValueFactory<CommandDTO, Integer>("sellPrice"));
        sellUser.setCellValueFactory(new PropertyValueFactory<CommandDTO, String>("sellUser"));

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

    }
    public void setStocksComboBox(List<String> stocksNameList) {

        ObservableList<String> list=FXCollections.observableArrayList(stocksNameList);
        this.stockComboBox.setItems(list);

    }

    @FXML
    public void onActionChoseStock()
    {
        String chosenStockName=stockComboBox.getValue();
        setCommandTables(mainController.getExchangeCollectionDtoForStock(chosenStockName));
        showProperty.set(true);
    }

    public void setCommandTables(ExchangeCollectionDTO data) {


       buyListData = FXCollections.observableArrayList(data.getBuyCommand());
        buyCommandTableView.setItems(this.buyListData);

        sellListData= FXCollections.observableArrayList(data.getSellCommand());
        sellCommandTableView.setItems(this.sellListData);

        tranListData= FXCollections.observableArrayList(data.getTransaction());
        transactionTableView.setItems(this.tranListData);
    }
}


