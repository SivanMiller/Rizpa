package engine;

import exception.*;
import javafx.util.Pair;
import objects.ExchangeCollectionDTO;
import objects.NewCmdOutcomeDTO;
import objects.StockDTO;
import objects.TransactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Stock {
    private String CompanyName;
    private String Symbol;
    private int Price;
    private ExchangeCollection ExchangeCollection;
    private List<Pair<String, Integer>> priceHistory;

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
        priceHistory = new ArrayList<>();
        priceHistory.add(new Pair(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), this.getPrice()));
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

    public NewCmdOutcomeDTO addNewCommand(User User, Command.CmdType Type, Command.CmdDirection Direction, int Price, int Quantity) throws StockNegQuantityException, CommandNegPriceException, NoSuchCmdTypeException {
        Command newCommand = null;
        try {

            if (Type == Command.CmdType.LMT) {
                newCommand = new LMTCommand(User, Price, Quantity, Direction);
            }
            else if (Type == Command.CmdType.MKT) {
                newCommand = new MKTCommand(User, Quantity, Direction);
            }
            else if (Type == Command.CmdType.FOK) {
                newCommand = new FOKCommand(User, Price, Quantity, Direction);
            }
            else if (Type == Command.CmdType.IOC) {
                newCommand = new IOCCommand(User, Price, Quantity, Direction);
            }
            else {
                throw new NoSuchCmdTypeException();
            }

            //Update stock price
            NewCmdOutcomeDTO newCommandDTO = this.ExchangeCollection.addNewCommand(newCommand);

            if (!newCommandDTO.getNewTransaction().isEmpty()) {
                this.Price = this.ExchangeCollection.getLastTransactionPrice();
                priceHistory.add(new Pair(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), this.getPrice()));
            }

            return newCommandDTO;
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
        return new StockDTO(this.getCompanyName(), this.getSymbol(),0, this.getPrice());
    }

    public List<Pair<String, Integer>> getPriceHistory() {
        return priceHistory;
    }


}
