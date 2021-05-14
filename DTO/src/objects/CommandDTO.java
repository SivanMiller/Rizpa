package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CommandDTO {
    public final SimpleStringProperty   buyDate;
    public final SimpleIntegerProperty  buyQuantity;
    public final SimpleIntegerProperty  buyPrice;
    public final SimpleStringProperty   buyUser;
    public final SimpleStringProperty   buyType;

    public CommandDTO(String date, String type, Integer quantity, Integer price, String user) {
        this.buyDate = new SimpleStringProperty(date);
        this.buyQuantity = new SimpleIntegerProperty(quantity);
        this.buyPrice = new SimpleIntegerProperty(price);
        this.buyUser = new SimpleStringProperty(user);
        this.buyType = new SimpleStringProperty(type);
    }

    @Override
    public String toString() {
        return "Price = " + getBuyPrice() +
                ", Quantity = " + getBuyQuantity() +
                ", Date = '" + getBuyDate() + '\'' +
                ", User = " + getBuyUser();
    }

    public String getBuyDate() {
        return buyDate.get();
    }

    public SimpleStringProperty buyDateProperty() {
        return buyDate;
    }

    public int getBuyQuantity() {
        return buyQuantity.get();
    }

    public SimpleIntegerProperty buyQuantityProperty() {
        return buyQuantity;
    }

    public int getBuyPrice() {
        return buyPrice.get();
    }

    public SimpleIntegerProperty buyPriceProperty() {
        return buyPrice;
    }

    public String getBuyUser() {
        return buyUser.get();
    }

    public SimpleStringProperty buyUserProperty() {
        return buyUser;
    }

    public String getBuyType() {
        return buyType.get();
    }

    public SimpleStringProperty buyTypeProperty() {
        return buyType;
    }
}
