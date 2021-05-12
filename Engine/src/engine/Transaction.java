package engine;

import objects.TransactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {

    private static int ORDER_COUNTER = 1;
    private int Price;
    private int Quantity;
    private String Date;
    private int Turnover;
    protected int Order;
    protected User buyUser;
    protected User sellUser;

    public Transaction(int Price, int Quantity, String Date, int Order, User buyUser, User sellUser) {
        this.Price = Price;
        this.Quantity = Quantity;
        this.Turnover = Price * Quantity;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.buyUser = buyUser;
        this.sellUser = sellUser;
        this.Order = ORDER_COUNTER;
     }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getDate() {
        return Date;
    }

    public int getTurnover() {
        return Turnover;
    }

    public void setTurnover(int Turnover) {
        this.Turnover = Turnover;
    }


    public User getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(User buyUser) {
        this.buyUser = buyUser;
    }

    public User getSellUser() {
        return sellUser;
    }

    public void setSellUser(User sellUser) {
        this.sellUser = sellUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Price == that.Price && Quantity == that.Quantity && Turnover == that.Turnover && Date.equals(that.Date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Price, Quantity, Date, Turnover);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                " sDate='" + Date + '\'' +
                ", nQuantity=" + Quantity +
                ", nPrice=" + Price +
                ", nTurnover=" + Turnover +
                ", Order=" + Order +
                '}';
    }

    public TransactionDTO convertToDTO() {
        return new TransactionDTO(this.Price, this.Quantity, this.Date, this.Turnover, this.buyUser, this.sellUser);
    }
}
