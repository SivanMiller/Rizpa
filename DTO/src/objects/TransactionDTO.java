package objects;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Uniform class for Transaction DTO and Command DTO
public class TransactionDTO {

    private SimpleIntegerProperty transactionPrice;
    private SimpleIntegerProperty transactionQuantity;
    private SimpleStringProperty transactionDate;
    protected SimpleStringProperty transactionBuyUser;
    protected SimpleStringProperty transactionSellUser;

    public TransactionDTO(int price, int quantity, String date, int turnover, String buyUser, String sellUser) {
        this.transactionPrice = new SimpleIntegerProperty(price);
        this.transactionQuantity = new SimpleIntegerProperty(quantity);
        this.transactionDate = new SimpleStringProperty(date);
        this.transactionBuyUser =new SimpleStringProperty(buyUser);
        this.transactionSellUser = new SimpleStringProperty(sellUser);
    }


    @Override
    public String toString() {
        return "Price = " + getTransactionPrice() +
                ", Quantity = " + getTransactionQuantity() +
                ", Date = '" + getTransactionDate() + '\'' +
                ", BuyUser = " + getTransactionBuyUser()+
                ", SellUser = " + getTransactionSellUser();
    }

    public int getTransactionPrice() {
        return transactionPrice.get();
    }

    public SimpleIntegerProperty transactionPriceProperty() {
        return transactionPrice;
    }

    public int getTransactionQuantity() {
        return transactionQuantity.get();
    }

    public SimpleIntegerProperty transactionQuantityProperty() {
        return transactionQuantity;
    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public SimpleStringProperty transactionDateProperty() {
        return transactionDate;
    }

    public String getTransactionBuyUser() {
        return transactionBuyUser.get();
    }

    public SimpleStringProperty transactionBuyUserProperty() {
        return transactionBuyUser;
    }

    public String getTransactionSellUser() {
        return transactionSellUser.get();
    }

    public SimpleStringProperty transactionSellUserProperty() {
        return transactionSellUser;
    }
}
