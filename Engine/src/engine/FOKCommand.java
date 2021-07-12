package engine;

import exception.CommandNegPriceException;
import exception.StockNegQuantityException;
import objects.CommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FOKCommand extends Command{
    public FOKCommand(){
       this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
   }

    public FOKCommand(User User,int Price, int Quantity, CmdDirection Direction) throws CommandNegPriceException, StockNegQuantityException {
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
    public CommandDTO convertToDTO() {
        return new CommandDTO(this.Date, "FOK", this.Quantity, this.Price,this.User.getName());
    }

    @Override
    public String toString() {
        return null;
    }
}
