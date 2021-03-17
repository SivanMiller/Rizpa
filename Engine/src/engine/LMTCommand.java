package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(int nPrice, int nQuantity, String sDate, Type Type) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Type = Type;
    }

    @Override
    public String toString() {
        return "LMT Command{" +
                "Price=" + nPrice +
                ", Quantity=" + nQuantity +
                ", Date='" + sDate + '\'' +
                ", Direction=" + Type +
                '}';
    }
}
