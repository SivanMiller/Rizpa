package engine;

import exceptions.StockNegPriceException;
import exceptions.StockNegQuantityException;
import objects.ExchangeDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LMTCommand extends Command {

    public LMTCommand() {
        this.sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public LMTCommand(int nPrice, int nQuantity, CmdType Type) throws StockNegPriceException, StockNegQuantityException {
        if (nPrice < 0)
            throw new StockNegPriceException();
        if (nQuantity < 0)
            throw new StockNegQuantityException();
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
    @Override
    public ExchangeDTO convertToDTO()
    {
        return new ExchangeDTO(this.nPrice, this.nQuantity, this.sDate, this.nQuantity * this.nQuantity);
    }
}


