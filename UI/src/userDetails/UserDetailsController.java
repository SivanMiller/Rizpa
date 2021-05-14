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
import objects.HoldingDTO;
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
    @FXML private TableView<HoldingDTO> stockDetails;
    @FXML private TableColumn<HoldingDTO, String> stockSymbol;
    @FXML private TableColumn<HoldingDTO, String> companyName;
    @FXML private TableColumn<HoldingDTO, Integer> stockQuantity;
    @FXML private TableColumn<HoldingDTO, Integer> stockPrice;

    private ObservableList<HoldingDTO> data;

    public void setMainController(UserTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        companyName.setCellValueFactory(new PropertyValueFactory<HoldingDTO, String>("companyName"));
        stockSymbol.setCellValueFactory(new PropertyValueFactory<HoldingDTO, String>("stockSymbol"));
        stockQuantity.setCellValueFactory(new PropertyValueFactory<HoldingDTO, Integer>("stockQuantity"));
        stockPrice.setCellValueFactory(new PropertyValueFactory<HoldingDTO, Integer>("stockPrice"));
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
        List<HoldingDTO> userList = new ArrayList();

        stockDetails.setItems(this.data);
        for(Holding holding : holdings.values()) {
//            StockDTO stockDTO = new StockDTO(holding.getStock().getCompanyName(), holding.getStock().getSymbol(),
//                                    holding.getQuantity(), holding.getStock().getPrice());

            userList.add(holding.convertToDTO());
        }

        data = FXCollections.observableArrayList(userList);
        stockDetails.setItems(data);
    }

    @FXML
    private void onActionAddCommand() {
        mainController.toggleAddCommand();
    }
}


