package userDetails;

import engine.Holding;
import engine.Stock;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class UserDetailsController {
    @FXML private TextField userName;
    @FXML private TextField stockSum;
    @FXML private TableView stockDetails;

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setStockSum(String stockSum) {
        this.stockSum.setText(stockSum);
    }

    public void setStocksTable(List<Holding> holdings){
        for(Holding holding : holdings){
              stockDetails.getItems().add(holdings);
        }
    }

    @FXML
    private void onActionAddCommand(){

    }


}
