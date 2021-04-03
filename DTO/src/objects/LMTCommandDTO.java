package objects;


// Uniform class for Transaction DTO and Command DTO
public class LMTCommandDTO extends CommandDTO{

    public LMTCommandDTO(int nPrice, int nQuantity, String sDate, int nTurnover) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = sDate;
        this.nTurnover = nTurnover;
    }

    @Override
    public String toString() {
        return "Price = " + nPrice +
                ", Quantity = " + nQuantity +
                ", Date = '" + sDate + '\'' +
                ", Turnover = " + nTurnover +
                ", Type = LMT";
    }
}
