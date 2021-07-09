package engine;

import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;

import java.util.Collections;
import java.util.HashMap;
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
    public synchronized void addStock(String Symbol, Stock newStock) throws StockSymbolLowercaseException, StockNegPriceException {


        Stocks.put(Symbol, newStock);
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
