
public class ExchangeDTO {

    private int nPrice;
    private int nQuantity;
    private String sDate;
    private int nTurnover;

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
