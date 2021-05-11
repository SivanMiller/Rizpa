package userDetails;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class StockDetails {
    public final SimpleStringProperty companyName;
    public final SimpleStringProperty stockSymbol;
    public final SimpleIntegerProperty stockQuantity;
    public final SimpleIntegerProperty stockPrice;

    public StockDetails(String companyName, String stockSymbol,
                        int stockQuantity, int stockPrice) {
        this.companyName = new SimpleStringProperty(companyName);
        this.stockSymbol = new SimpleStringProperty(stockSymbol);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.stockPrice = new SimpleIntegerProperty(stockPrice);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public String getStockSymbol() {
        return stockSymbol.get();
    }

    public SimpleStringProperty stockSymbolProperty() {
        return stockSymbol;
    }

    public int getStockQuantity() {
        return stockQuantity.get();
    }

    public SimpleIntegerProperty stockQuantityProperty() {
        return stockQuantity;
    }

    public int getStockPrice() {
        return stockPrice.get();
    }

    public SimpleIntegerProperty stockPriceProperty() {
        return stockPrice;
    }
}
