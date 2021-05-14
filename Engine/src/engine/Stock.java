package engine;

import exception.*;
import objects.ExchangeCollectionDTO;
import objects.NewCmdOutcomeDTO;
import objects.StockDTO;

import java.util.*;

public class Stock {

    public enum CmdType {
        LMT,
        MKT
    }
    private String CompanyName;
    private String Symbol;
    private int Price;
    private ExchangeCollection ExchangeCollection;

    public Stock(String CompanyName, String Symbol, int Price) throws StockNegPriceException, StockSymbolLowercaseException {
        if (Price < 0)
            throw new StockNegPriceException(Symbol);
        this.CompanyName = CompanyName;

        if (Utilities.checkUpperCase(Symbol)) {
            this.Symbol = Symbol.toUpperCase();
        }
        else {
            throw new StockSymbolLowercaseException();
        }
        this.Price = Price;
        this.ExchangeCollection = new ExchangeCollection(Price);
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getSymbol() {
        return Symbol;
    }

    public int getPrice() {
        return Price;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public void setExchangeCollection(ExchangeCollection ExchangeCollection) {
        this.ExchangeCollection = ExchangeCollection;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stock)) return false;
        Stock stock = (Stock) obj;
        return CompanyName.equals(stock.CompanyName) && Symbol.equals(stock.Symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CompanyName, Symbol);
    }

    @Override
    public String toString() {
        return "Stock{" +
               "Symbol = '" + Symbol + '\'' +
               ", CompanyName = '" + CompanyName + '\'' +
               ", Price = " + Price +
               ", Exchange = " + ExchangeCollection +
               '}';
    }

    public NewCmdOutcomeDTO addNewCommand(User user, int Type, Command.CmdDirection Direction, int Price, int Quantity) throws StockNegQuantityException, CommandNegPriceException, NoSuchCmdTypeException {
        Command newCommand = null;
        try {
            // the values of the enum start from 0
            Type--;

            if (Type == CmdType.LMT.ordinal()) {
                newCommand = new LMTCommand(user, Price, Quantity, Direction);
            }
            else if (Type == CmdType.MKT.ordinal()) {
                newCommand = new MKTCommand(user, Quantity, Direction);
            }
            else {
                throw new NoSuchCmdTypeException();
            }

            //Update stock price
            this.Price = this.ExchangeCollection.getLastTransactionPrice();
            return this.ExchangeCollection.addNewCommand(newCommand);
        }
        catch (CommandNegPriceException | StockNegQuantityException e) {
            throw e;
        }

    }

    public ExchangeCollectionDTO getExchangeCollection()
    {
        return (this.ExchangeCollection.convertToDTO());
    }

    public StockDTO convertToDTO() {
        return new StockDTO(this.getCompanyName(), this.getSymbol(),0, this.getPrice());//TODO how to get the Quantity
    }
}
