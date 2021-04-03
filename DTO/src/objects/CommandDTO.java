package objects;


// Uniform class for Transaction DTO and Command DTO
public abstract class CommandDTO {

    protected int nPrice;
    protected int nQuantity;
    protected String sDate;
    protected int nTurnover;

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
    public abstract String toString();
}
