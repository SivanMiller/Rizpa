package engine;

public class LMTCommand extends Command {

    @Override
    public String toString() {
        return "LMT Command{" +
                "Price=" + nPrice +
                ", Quantity=" + nQuantity +
                ", Date='" + sDate + '\'' +
                ", Direction=" + bDirection +
                '}';
    }
}
