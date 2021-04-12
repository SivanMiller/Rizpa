package engine;

import exceptions.CommandNegPriceException;
import exceptions.StockNegQuantityException;
import objects.LMTCommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(int Price, int Quantity, CmdDirection Direction) throws CommandNegPriceException, StockNegQuantityException {
        if (Price < 0)
            throw new CommandNegPriceException();
        if (Quantity < 0)
            throw new StockNegQuantityException();
        this.Price = Price;
        this.Quantity = Quantity;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Direction = Direction;
        this.Order = ORDER_COUNTER;
        ORDER_COUNTER++;
    }

    @Override
    public String toString() {
        return "LMT Command" +
                "nPrice =" + Price +
                ", nQuantity =" + Quantity +
                ", sDate ='" + Date + '\'' +
                ", Direction =" + Direction +
                ", Order =" + Order;
    }

    @Override
    public LMTCommandDTO convertToDTO() {
        return new LMTCommandDTO(this.Price, this.Quantity, this.Date, this.Price * this.Quantity);
    }
}


