package engine;

import exception.CommandNegPriceException;
import exception.StockNegQuantityException;
import objects.CommandDTO;
//import objects.LMTCommandDTOS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(User User,int Price, int Quantity, CmdDirection Direction) throws CommandNegPriceException, StockNegQuantityException {
        if (Price < 0)
            throw new CommandNegPriceException();
        if (Quantity < 0)
            throw new StockNegQuantityException();
        this.Price = Price;
        this.Quantity = Quantity;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Direction = Direction;
        this.Order = ORDER_COUNTER;
        this.User = User;
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
    public CommandDTO convertToDTO() {
        return new CommandDTO(this.Date, "LMT", this.Quantity, this.Price,this.User.getName());
    }
}


