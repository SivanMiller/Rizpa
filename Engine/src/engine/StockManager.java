package engine;

import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import objects.CommandDTO;
import objects.TransactionDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockManager {
    private Map<String, Stock> Stocks;

    public StockManager() {
        Stocks = new HashMap<>();
    }

    public synchronized void addStock(String Symbol, String CompanyName, int Price) throws StockSymbolLowercaseException, StockNegPriceException {
        Stock newStock = new Stock(CompanyName, Symbol, Price);
        Stocks.put(Symbol, newStock);
    }
    public synchronized void addStock(String Symbol, Stock newStock) {

       Stocks.put(Symbol, newStock);
    }
    public List<TransactionDTO> getStockTransactionsList(String stockSymbol){
        return Stocks.get(stockSymbol).getExchangeCollection().getTransaction();
    }

    public List<CommandDTO> getStockBuyCommandList(String stockSymbol)
    {
        return this.Stocks.get(stockSymbol).getExchangeCollection().getBuyCommand();
    }
    public List<CommandDTO> getStockSellCommandList(String stockSymbol)
    {
        return this.Stocks.get(stockSymbol).getExchangeCollection().getSellCommand();
    }


    public synchronized void removeStock(String Symbol) {
        Stocks.remove(Symbol);
    }

    public synchronized Map<String, Stock> getStocks() {
        return Collections.unmodifiableMap(Stocks);
    }

    public boolean isStockExists(String Symbol) {
        return Stocks.containsKey(Symbol);
    }
}
