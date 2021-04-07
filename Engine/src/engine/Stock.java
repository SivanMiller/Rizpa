package engine;

import exceptions.NoSuchCmdTypeException;
import exceptions.StockNegPriceException;
import exceptions.StockNegQuantityException;
import exceptions.StockSymbolLowercaseException;
import objects.StockDTO;

import java.util.*;

public class Stock {

    public enum CmdType
    {
        LMT,
        MKT
    }
    private String sCompanyName;
    private String sSymbol;
    private int nPrice;
    private ExchangeCollection ecExchange;

    public Stock(String sCompanyName, String sSymbol, int nPrice) throws StockNegPriceException, StockSymbolLowercaseException {
        if (nPrice < 0)
            throw new StockNegPriceException();
        this.sCompanyName = sCompanyName;

        if (Utilities.checkUpperCase(sSymbol))
        {
            this.sSymbol = sSymbol.toUpperCase();
        }
        else
        {
            throw new StockSymbolLowercaseException();
        }
        this.nPrice = nPrice;
        this.ecExchange = new ExchangeCollection(nPrice);
    }

    public String getCompanyName() {
        return sCompanyName;
    }

    public String getSymbol() {
        return sSymbol;
    }

    public int getPrice() {
        return nPrice;
    }

    public ExchangeCollection getExchange() {
        return ecExchange;
    }

    public void setCompanyName(String sCompanyName) {
        this.sCompanyName = sCompanyName;
    }

    public void setSymbol(String sSymbol) {
        this.sSymbol = sSymbol;
    }

    public void setPrice(int nGate) {
        this.nPrice = nGate;
    }

    public void setExchange(ExchangeCollection ecExchange) {
        this.ecExchange = ecExchange;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stock)) return false;
        Stock stock = (Stock) obj;
        return sCompanyName.equals(stock.sCompanyName) && sSymbol.equals(stock.sSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sCompanyName, sSymbol);
    }

    @Override
    public String toString() {
        return "Stock{" +
               "Symbol = '" + sSymbol + '\'' +
               ", CompanyName = '" + sCompanyName + '\'' +
               ", Price = " + nPrice +
               ", Exchange = " + ecExchange +
               '}';
    }

    public void addNewCommand(int nType, Command.CmdDirection Direction, int nPrice, int nQuantity) throws StockNegQuantityException, StockNegPriceException, NoSuchCmdTypeException {
        Command newCommand = null;
        try {
            // the values of the enum start from 0
            nType--;

            if (nType == CmdType.LMT.ordinal())
            {
                newCommand = new LMTCommand(nPrice, nQuantity, Direction);
            }
            else if (nType == CmdType.MKT.ordinal())
            {
                newCommand = new MKTCommand(nQuantity, Direction);
            }
            else
            {
                throw new NoSuchCmdTypeException();
            }

            this.ecExchange.addNewCommand(newCommand);
            //Update stock price
            this.nPrice = this.ecExchange.LastTransactionPrice();
        }
        catch (StockNegPriceException | StockNegQuantityException e) {
            throw e;
        }

    }

    public StockDTO convertToDTO()
    {
        return new StockDTO(this.getCompanyName(), this.getSymbol(), this.getPrice(),
                             this.ecExchange.convertToDTO(),
                             this.ecExchange.convertToDTO().getTransaction().size(), ecExchange.getTurnover() );
    }
}
