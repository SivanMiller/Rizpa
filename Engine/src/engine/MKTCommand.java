package engine;

import exception.StockNegQuantityException;
import objects.MKTCommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MKTCommand extends Command {

    public MKTCommand() {
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public MKTCommand(int Quantity, CmdDirection Direction) throws StockNegQuantityException {
        if (Quantity < 0)
            throw new StockNegQuantityException();
        this.Price = 0;
        this.Quantity = Quantity;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Direction = Direction;
        this.Order = ORDER_COUNTER;
        ORDER_COUNTER++;
    }

    @Override
    public String toString() {
        return "MKT Command" +
                "nPrice =" + Price +
                ", nQuantity =" + Quantity +
                ", sDate ='" + Date + '\'' +
                ", Direction =" + Direction +
                ", Order =" + Order;
    }

    @Override
    public MKTCommandDTO convertToDTO() {
        return new MKTCommandDTO(this.Price, this.Quantity, this.Date, this.Price * this.Quantity);
    }
}


