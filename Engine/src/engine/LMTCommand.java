package engine;

import exceptions.CommandNegPriceException;
import exceptions.StockNegPriceException;
import exceptions.StockNegQuantityException;
import objects.LMTCommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(int nPrice, int nQuantity, CmdDirection Direction) throws CommandNegPriceException, StockNegQuantityException {
        if (nPrice < 0)
            throw new CommandNegPriceException();
        if (nQuantity < 0)
            throw new StockNegQuantityException();
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.cmdDirection = Direction;
        this.Order = order;
        order++;
    }

    @Override
    public String toString() {
        return "LMT Command" +
                "nPrice =" + nPrice +
                ", nQuantity =" + nQuantity +
                ", sDate ='" + sDate + '\'' +
                ", Direction =" + cmdDirection +
                ", Order =" + Order;
    }
    @Override
    public LMTCommandDTO convertToDTO()
    {
        return new LMTCommandDTO(this.nPrice, this.nQuantity, this.sDate, this.nPrice * this.nQuantity);
    }
}


