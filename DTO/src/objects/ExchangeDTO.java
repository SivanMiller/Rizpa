package objects;


// Uniform class for Transaction DTO and Command DTO
public class ExchangeDTO {

    private int nPrice;
    private int nQuantity;
    private String sDate;
    private int nTurnover;

    public ExchangeDTO(int nPrice, int nQuantity, String sDate, int nTurnover) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = sDate;
        this.nTurnover = nTurnover;
    }

    public String getDate() {
        return sDate;
    }

    public int getPrice() {
        return nPrice;
    }

    public int getQuantity() {
        return nQuantity;
    }

    public int getTurnover() {
        return nTurnover;
    }

    @Override
    public String toString() {
        return "Price=" + nPrice +
                ", Quantity=" + nQuantity +
                ", Date='" + sDate + '\'' +
                ", Turnover=" + nTurnover +
                '}';
    }
}
