package engine;

import exception.StockNegQuantityException;
import objects.CommandDTO;
//import objects.MKTCommandDTOS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MKTCommand extends Command {

    public MKTCommand() {
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public MKTCommand(User user,int Quantity, CmdDirection Direction) throws StockNegQuantityException {
        if (Quantity < 0)
            throw new StockNegQuantityException();
        this.Price = 0;
        this.Quantity = Quantity;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Direction = Direction;
        this.Order = ORDER_COUNTER;
        this.User =user;

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
    public CommandDTO convertToDTO() {
        return new CommandDTO(this.Date, "MKT", this.Quantity, this.Price,this.User.getName(), this.getDirection().toString());
    }
}


