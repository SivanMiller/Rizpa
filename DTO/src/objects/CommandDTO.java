package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CommandDTO {
    public final SimpleStringProperty  Date;
    public final SimpleIntegerProperty Quantity;
    public final SimpleIntegerProperty Price;
    public final SimpleStringProperty  User;
    public final SimpleStringProperty  Type;
    public final SimpleStringProperty  Direction;

    public CommandDTO(String date, String type, Integer quantity, Integer price, String user, String Direction) {
        this.Date = new SimpleStringProperty(date);
        this.Quantity = new SimpleIntegerProperty(quantity);
        this.Price = new SimpleIntegerProperty(price);
        this.User = new SimpleStringProperty(user);
        this.Type = new SimpleStringProperty(type);
        this.Direction = new SimpleStringProperty(Direction);
    }

    @Override
    public String toString() {
        return this.getDirection() + " command. " +
                "Quantity = " + getQuantity() +
                ", Price = " + getPrice();
    }

    public String getDate() {
        return Date.get();
    }

    public SimpleStringProperty dateProperty() {
        return Date;
    }

    public int getQuantity() {
        return Quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return Quantity;
    }

    public int getPrice() {
        return Price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return Price;
    }

    public String getUser() {
        return User.get();
    }

    public SimpleStringProperty userProperty() {
        return User;
    }

    public String getType() {
        return Type.get();
    }

    public SimpleStringProperty typeProperty() {
        return Type;
    }

    public String getDirection() {
        return Direction.get();
    }

    public SimpleStringProperty directionProperty() {
        return Direction;
    }
}
