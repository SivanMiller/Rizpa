package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CommandDTO {
    public final SimpleStringProperty date;
    public final SimpleStringProperty type;
    public final SimpleIntegerProperty quantity;
    public final SimpleIntegerProperty price;
    public final SimpleStringProperty user;

    public CommandDTO(String date, String type, Integer quantity, Integer price, String user) {
        this.date = new SimpleStringProperty(date);
        this.type = new SimpleStringProperty(type);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleIntegerProperty(price);
        this.user =new SimpleStringProperty(user);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty getDateProperty() {
        return date;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty getTypeProperty() {
        return type;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty getQuantityProperty() {
        return quantity;
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty getPriceProperty() {
        return price;
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty getUserProperty() {
        return user;
    }

    @Override
    public String toString() {
        return "Price = " + getPrice() +
                ", Quantity = " + getQuantity() +
                ", Date = '" + getDate() + '\'' +
                ", User = " + getUser();
    }

}
