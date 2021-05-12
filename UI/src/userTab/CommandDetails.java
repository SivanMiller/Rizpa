package userTab;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CommandDetails {
    public final SimpleStringProperty date;
    public final SimpleStringProperty type;
    public final SimpleIntegerProperty quantity;
    public final SimpleIntegerProperty price;
    public final SimpleStringProperty user;

    public CommandDetails(SimpleStringProperty date, SimpleStringProperty type, SimpleIntegerProperty quantity, SimpleIntegerProperty price, SimpleStringProperty user) {
        this.date = date;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.user = user;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }
}
