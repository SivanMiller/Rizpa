package objects;


// Uniform class for Transaction DTO and Command DTO
public class LMTCommandDTO extends CommandDTO{

    public LMTCommandDTO(int Price, int Quantity, String Date, int Turnover) {
        this.Price = Price;
        this.Quantity = Quantity;
        this.Date = Date;
        this.Turnover = Turnover;
    }

    @Override
    public String toString() {
        return "Price = " + Price +
                ", Quantity = " + Quantity +
                ", Date = '" + Date + '\'' +
                ", Turnover = " + Turnover +
                ", Type = LMT";
    }
}
