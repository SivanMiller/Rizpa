package engine;

import objects.TransactionDTO;

import java.util.Map;

public class User {

    private String Name;
    private Map<String, Holding> Holdings;

    public User(String name, Map<String, Holding> holdings) {
        Name = name;
        Holdings = holdings;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Map<String, Holding> getHoldings() {
        return Holdings;
    }

    public Holding getHolding(String Symbol) { return Holdings.get(Symbol); }

    public void setHoldings(Map<String, Holding> holdings) {
        Holdings = holdings;
    }

    public void commitBuyTransaction(Stock stock, TransactionDTO transaction){
        Holding stockHolding = this.getHolding(stock.getSymbol());
        if (stockHolding == null)
        {
            Holding newHolding = new Holding(transaction.getTransactionQuantity(), stock);
            this.getHoldings().put(stock.getSymbol(), newHolding);
        }
        else {
            stockHolding.setQuantity(stockHolding.getQuantity() + transaction.getTransactionQuantity());
        }
    }

    public void commitSellTransaction(Stock stock, TransactionDTO transaction){
        Holding stockHolding = this.getHolding(stock.getSymbol());
        stockHolding.setQuantity(stockHolding.getQuantity() - transaction.getTransactionQuantity());
        if (stockHolding.getQuantity() == 0) {
            this.getHoldings().remove(stock.getSymbol());
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Holdings=" + Holdings.toString() +
                '}';
    }
}
