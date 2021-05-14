package objects;


import engine.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Uniform class for Transaction DTO and Command DTO
public class TransactionDTO {

    private SimpleIntegerProperty price;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty date;
    protected SimpleStringProperty buyUser;
    protected SimpleStringProperty sellUser;

    public TransactionDTO(int price, int quantity, String date, int turnover, String buyUser, String sellUser) {
        this.price = new SimpleIntegerProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date= new SimpleStringProperty(date);
        this.buyUser =new SimpleStringProperty(buyUser);
        this.sellUser = new SimpleStringProperty(sellUser);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getBuyUser() {
        return buyUser.get();
    }

    public SimpleStringProperty buyUserProperty() {
        return buyUser;
    }

    public String getSellUser() {
        return sellUser.get();
    }

    public SimpleStringProperty sellUserProperty() {
        return sellUser;
    }

    @Override
    public String toString() {
        return "Price = " + getPrice() +
                ", Quantity = " + getQuantity() +
                ", Date = '" + getDate() + '\'' +
                ", BuyUser = " + getBuyUser()+
                ", SellUser = " + getSellUser();
    }

}
