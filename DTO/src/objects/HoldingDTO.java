package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HoldingDTO {

    public final SimpleStringProperty companyName;
    public final SimpleStringProperty stockSymbol;
    public final SimpleIntegerProperty stockQuantity;
    public final SimpleIntegerProperty stockPrice;

    public HoldingDTO(String companyName, String stockSymbol,int stockQuantity, int stockPrice) {
        this.companyName = new SimpleStringProperty(companyName);
        this.stockSymbol = new SimpleStringProperty(stockSymbol);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.stockPrice = new SimpleIntegerProperty(stockPrice);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty getCompanyNameProperty() {
        return companyName;
    }

    public String getStockSymbol() {
        return stockSymbol.get();
    }

    public SimpleStringProperty getStockSymbolProperty() {
        return stockSymbol;
    }

    public int getStockQuantity() {
        return stockQuantity.get();
    }

    public SimpleIntegerProperty getStockQuantityProperty() {
        return stockQuantity;
    }

    public int getStockPrice() {
        return stockPrice.get();
    }

    public SimpleIntegerProperty getStockPriceProperty() {
        return stockPrice;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol.set(stockSymbol);
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice.set(stockPrice);
    }

}
