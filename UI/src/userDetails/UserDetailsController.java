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
import objects.StockDTO;
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
    @FXML private TableView<StockDTO> stockDetails;
    @FXML private TableColumn<StockDTO, String> stockSymbol;
    @FXML private TableColumn<StockDTO, String> companyName;
    @FXML private TableColumn<StockDTO, Integer> stockQuantity;
    @FXML private TableColumn<StockDTO, Integer> stockPrice;

    private ObservableList<StockDTO> data;

    public void setMainController(UserTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        companyName.setCellValueFactory(new PropertyValueFactory<StockDTO, String>("companyName"));
        stockSymbol.setCellValueFactory(new PropertyValueFactory<StockDTO, String>("stockSymbol"));
        stockQuantity.setCellValueFactory(new PropertyValueFactory<StockDTO, Integer>("stockQuantity"));
        stockPrice.setCellValueFactory(new PropertyValueFactory<StockDTO, Integer>("stockPrice"));
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



        List<StockDTO> userList = new ArrayList();

        stockDetails.setItems(this.data);
        for(Holding holding : holdings.values()) {
            StockDTO stockDTO = new StockDTO(holding.getStock().getCompanyName(), holding.getStock().getSymbol(),
                                    holding.getQuantity(), holding.getStock().getPrice());

            userList.add(stockDTO);
        }

        data = FXCollections.observableArrayList(userList);
        stockDetails.setItems(data);
    }

    @FXML
    private void onActionAddCommand() {
        mainController.toggleAddCommand();
    }
}


