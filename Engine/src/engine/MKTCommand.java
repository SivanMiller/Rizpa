package engine;

import exceptions.StockNegPriceException;
import exceptions.StockNegQuantityException;
import objects.MKTCommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MKTCommand extends Command {

    public MKTCommand() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public MKTCommand(int nQuantity, CmdDirection Direction) throws StockNegPriceException, StockNegQuantityException {
        if (nQuantity < 0)
            throw new StockNegQuantityException();
        this.nPrice = 0;
        this.nQuantity = nQuantity;
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.cmdDirection = Direction;
        this.Order = order;
        order++;
    }

    @Override
    public String toString() {
        return "MKT Command" +
                "nPrice =" + nPrice +
                ", nQuantity =" + nQuantity +
                ", sDate ='" + sDate + '\'' +
                ", Direction =" + cmdDirection +
                ", Order =" + Order;
    }
    @Override
    public MKTCommandDTO convertToDTO()
    {
        return new MKTCommandDTO(this.nPrice, this.nQuantity, this.sDate, this.nPrice * this.nQuantity);
    }
}


