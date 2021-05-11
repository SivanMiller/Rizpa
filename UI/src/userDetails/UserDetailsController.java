package userDetails;

import app.AppController;
import engine.Holding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
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
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    private UserTabController mainController;
    @FXML private TextField userName;
    @FXML private TextField stockSum;
    @FXML private TableView<Sivan> stockDetails;
    @FXML private TableColumn<Sivan, String> companyName;
    @FXML private TableColumn<Sivan, String> stockSymbol;
    @FXML private TableColumn<Sivan, Integer> stockQuantity;
    @FXML private TableColumn<Sivan, Integer> stockPrice;

    private ObservableList<Sivan> data;

    public void setMainController(UserTabController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        companyName.setCellValueFactory(new PropertyValueFactory<Sivan, String>("companyName"));
        stockSymbol.setCellValueFactory(new PropertyValueFactory<Sivan, String>("stockSymbol"));
        stockQuantity.setCellValueFactory(new PropertyValueFactory<Sivan, Integer>("stockQuantity"));
        stockPrice.setCellValueFactory(new PropertyValueFactory<Sivan, Integer>("stockPrice"));
        Sivan sivan = new Sivan("Sivan", "Sivan",
                1, 1);
        List<Sivan> l = new ArrayList();
        l.add(sivan);
        data = FXCollections.observableArrayList(l);

        stockDetails.setItems(this.data);

    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setStockSum(String stockSum) {
        this.stockSum.setText(stockSum);
    }

    public void setStocksTable(List<Holding> holdings) {
//        ObservableList<Sivan> data = FXCollections.observableArrayList();
//        for(Holding holding : holdings) {
//            Sivan sivan = new Sivan(holding.getStock().getCompanyName(), holding.getStock().getSymbol(),
//                                    holding.getQuantity(), holding.getStock().getPrice());
//            data.add(sivan);
//        }
//
//        stockDetails.setItems(data);
    }

    @FXML
    private void onActionAddCommand() {
        mainController.toggleAddCommand();
    }
}


