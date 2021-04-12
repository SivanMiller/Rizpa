package objects;


// Uniform class for Transaction DTO and Command DTO
public class TransactionDTO {

    private int Price;
    private int Quantity;
    private String Date;
    private int Turnover;

    public TransactionDTO(int Price, int Quantity, String Date, int Turnover) {
        this.Price = Price;
        this.Quantity = Quantity;
        this.Date = Date;
        this.Turnover = Turnover;
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

    @Override
    public String toString() {
        return "Price = " + Price +
                ", Quantity = " + Quantity +
                ", Date = '" + Date + '\'' +
                ", Turnover = " + Turnover;
    }
}
