package engine;

import java.util.Objects;

public abstract class Command {

    public enum Type {
        BUY,
        SELL
    }

    protected int nPrice;
    protected int nQuantity;
    protected String sDate;
    protected Type Type;

    public int getPrice() {
        return nPrice;
    }

    public void setPrice(int nPrice) {
        this.nPrice = nPrice;
    }

    public int getQuantity() {
        return nQuantity;
    }

    public void setQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public Type getDirection() {
        return this.Type;
    }

    public void setDirection(Type bDirection) {
        this.Type = bDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return nPrice == command.nPrice && nQuantity == command.nQuantity && Type == command.Type && sDate.equals(command.sDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nPrice, nQuantity, sDate, Type);
    }

    @Override
    public abstract String toString();
}
