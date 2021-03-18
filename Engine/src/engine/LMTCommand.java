package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(int nPrice, int nQuantity, String sDate, CmdType Type) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Type = Type;
        this.Order = order;
        order++;
    }

    @Override
    public String toString() {
        return "Command{" +
                "nPrice=" + nPrice +
                ", nQuantity=" + nQuantity +
                ", sDate='" + sDate + '\'' +
                ", Type=" + Type +
                ", Order=" + Order +
                '}';
    }
}


