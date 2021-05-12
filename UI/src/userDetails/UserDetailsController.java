package userDetails;

import engine.Holding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import userTab.UserTabController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    private UserTabController mainController;

    @FXML private TextField userName;
    @FXML private TextField stockSum;
    @FXML private TableView<StockDetails> stockDetails;
    @FXML private TableColumn<StockDetails, String> companyName;
    @FXML private TableColumn<StockDetails, String> stockSymbol;
    @FXML private TableColumn<StockDetails, Integer> stockQuantity;
    @FXML private TableColumn<StockDetails, Integer> stockPrice;

    private ObservableList<StockDetails> data;

    public void setMainController(UserTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        companyName.setCellValueFactory(new PropertyValueFactory<StockDetails, String>("companyName"));
        stockSymbol.setCellValueFactory(new PropertyValueFactory<StockDetails, String>("stockSymbol"));
        stockQuantity.setCellValueFactory(new PropertyValueFactory<StockDetails, Integer>("stockQuantity"));
        stockPrice.setCellValueFactory(new PropertyValueFactory<StockDetails, Integer>("stockPrice"));
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public String getUserNameString() {
        return userName.getText();
    }


    public void setStockSum(String stockSum) {
        this.stockSum.setText(stockSum);
    }

    public void setStocksTable(Map<String, Holding> holdings) {
        List<StockDetails> userList = new ArrayList();

        stockDetails.setItems(this.data);
        for(Holding holding : holdings.values()) {
            StockDetails stockDetails = new StockDetails(holding.getStock().getCompanyName(), holding.getStock().getSymbol(),
                                    holding.getQuantity(), holding.getStock().getPrice());
            userList.add(stockDetails);
        }

        data = FXCollections.observableArrayList(userList);
        stockDetails.setItems(data);
    }

    @FXML
    private void onActionAddCommand() {
        mainController.toggleAddCommand();
    }
}


