package objects;


import engine.User;

// Uniform class for Transaction DTO and Command DTO
public class TransactionDTO {

    private int Price;
    private int Quantity;
    private String Date;
    private int Turnover;
    protected User buyUser;
    protected User sellUser;

    public TransactionDTO(int price, int quantity, String date, int turnover, User buyUser, User sellUser) {
        Price = price;
        Quantity = quantity;
        Date = date;
        Turnover = turnover;
        this.buyUser = buyUser;
        this.sellUser = sellUser;
    }

    public String getDate() {
        return Date;
    }

    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getTurnover() {
        return Turnover;
    }

    public User getBuyUser() {
        return buyUser;
    }

    public User getSellUser() {
        return sellUser;
    }

    @Override
    public String toString() {
        return "Price = " + Price +
                ", Quantity = " + Quantity +
                ", Date = '" + Date + '\'' +
                ", Turnover = " + Turnover;
    }

}
