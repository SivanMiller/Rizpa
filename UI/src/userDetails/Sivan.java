package userDetails;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class Sivan {
    public final SimpleStringProperty companyName;
    public final SimpleStringProperty stockSymbol;
    public final SimpleIntegerProperty stockQuantity;
    public final SimpleIntegerProperty stockPrice;

    public Sivan(String companyName, String stockSymbol,
                 int stockQuantity, int stockPrice) {
        this.companyName = new SimpleStringProperty(companyName);
        this.stockSymbol = new SimpleStringProperty(stockSymbol);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.stockPrice = new SimpleIntegerProperty(stockPrice);
    }
}
