package engine;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.OptimizedTransducedAccessorFactory;
import javafx.util.Pair;
import objects.HoldingDTO;
import objects.TransactionDTO;
import objects.UserCommandDTO;
import objects.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String Name;
//    private int HoldingsTurnover;
    private int Funds;
    private Map<String, Holding> Holdings;
    private List<UserCommandDTO> AccountMovements;
    private List<Pair<String, TransactionDTO>> UnreportedTransactions;

    public User(String name, Map<String, Holding> holdings) {
        Name = name;
        Holdings = holdings;
        AccountMovements = new ArrayList<>();
        UnreportedTransactions = new ArrayList<>();
        for (Holding holding : this.Holdings.values())
        {
//            HoldingsTurnover += holding.getQuantity() * holding.getStock().getPrice();
            Funds += holding.getQuantity() * holding.getStock().getPrice();
        }
    }

    public User(String name) {
        Name = name;
        Holdings = new HashMap<>();
//        HoldingsTurnover = 0;
        Funds = 0;
        AccountMovements = new ArrayList<>();
        UnreportedTransactions = new ArrayList<>();

    }

    public void addHolding(Holding newHold)
    {
        Holdings.put(newHold.getStock().getSymbol(), newHold);
        this.Funds += newHold.getQuantity() * newHold.getStock().getPrice();
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

    //    public int getHoldingsTurnover() {
//        HoldingsTurnover = 0;
//        for (Holding holding : this.Holdings.values())
//        {
//            HoldingsTurnover += holding.getQuantity() * holding.getStock().getPrice();
//        }
//        return HoldingsTurnover;
//    }

    public List<UserCommandDTO> getAccountMovements() {
        return AccountMovements;
    }

    public void AddFunds(int fundsToAdd) {
        int beforeFunds = this.Funds;
        this.Funds += fundsToAdd;
        UserCommandDTO newCommand = new UserCommandDTO(UserCommandDTO.CmdType.CHARGE.toString(),fundsToAdd,beforeFunds,this.Funds);

        this.AccountMovements.add(newCommand);

    }
    public int getFunds() { return this.Funds; }

    public void commitBuyTransaction(Stock Stock, TransactionDTO NewTransaction){
        Holding stockHolding = this.getHolding(Stock.getSymbol());
        if (stockHolding == null)
        {
            Holding newHolding = new Holding(NewTransaction.getTransactionQuantity(), Stock);
            this.getHoldings().put(Stock.getSymbol(), newHolding);
        }
        else {
            stockHolding.setQuantity(stockHolding.getQuantity() + NewTransaction.getTransactionQuantity());
        }

        int beforeFunds = this.Funds;
        this.Funds += NewTransaction.getTransactionQuantity() * NewTransaction.getTransactionPrice();

        UserCommandDTO newCommand = new UserCommandDTO(UserCommandDTO.CmdType.BUY.toString(), NewTransaction.getTransactionDate(), NewTransaction.getTransactionPrice(), beforeFunds, this.Funds);
        this.AccountMovements.add(newCommand);

        this.UnreportedTransactions.add(new Pair(Stock.getSymbol(), NewTransaction));
    }

    public void commitSellTransaction(Stock Stock, TransactionDTO NewTransaction) {
        Holding stockHolding = this.getHolding(Stock.getSymbol());
        stockHolding.setQuantity(stockHolding.getQuantity() - NewTransaction.getTransactionQuantity());

        if (stockHolding.getQuantity() == 0) {
            this.getHoldings().remove(Stock.getSymbol());
        }

        int beforeFunds = this.Funds;
        this.Funds -= NewTransaction.getTransactionQuantity() * NewTransaction.getTransactionPrice();

        UserCommandDTO newCommand = new UserCommandDTO(UserCommandDTO.CmdType.SELL.toString(), NewTransaction.getTransactionDate(), NewTransaction.getTransactionPrice(), beforeFunds, this.Funds);
        this.AccountMovements.add(newCommand);

        this.UnreportedTransactions.add(new Pair(Stock.getSymbol(), NewTransaction));
    }

    public List<Pair<String, TransactionDTO>> reportTransactions(){
        List<Pair<String, TransactionDTO>> list = new ArrayList<>(UnreportedTransactions);

        UnreportedTransactions.clear();

        return list;
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Holdings=" + Holdings.toString() +
                '}';
    }

    public UserDTO convertToDTO(){
        List<HoldingDTO> holdings = new ArrayList<>();

        for (Holding holding : this.getHoldings().values()){
            holdings.add(holding.convertToDTO());
        }

        return new UserDTO(this.getName(), this.Funds, holdings);
    }

}
