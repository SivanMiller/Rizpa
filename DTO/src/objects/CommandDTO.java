package objects;

public abstract class CommandDTO {


    protected int Price;
    protected int Quantity;
    protected String Date;
    protected int Turnover;

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
    public abstract String toString();
}
