package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Command {

    protected int nPrice;
    protected int nQuantity;
    protected String sDate;
    protected boolean bDirection; // true = BUY false = SELL

    public Command() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public Command(int nPrice, int nQuantity, String sDate, boolean bDirection) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.bDirection = bDirection;
    }

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

    public boolean getDirection() {
        return bDirection;
    }

    public void setDirection(boolean bDirection) {
        this.bDirection = bDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return nPrice == command.nPrice && nQuantity == command.nQuantity && bDirection == command.bDirection && sDate.equals(command.sDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nPrice, nQuantity, sDate, bDirection);
    }

    @Override
    public abstract String toString();
}
