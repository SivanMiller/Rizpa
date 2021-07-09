package engine;

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
    private List<UserCommandDTO>  allAccountMovements;


    public User(String name, Map<String, Holding> holdings) {
        Name = name;
        Holdings = holdings;
        allAccountMovements=new ArrayList<>();
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
        allAccountMovements=new ArrayList<>();

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

    public void setHoldings(Map<String, Holding> holdings) {
        Holdings = holdings;
    }

//    public int getHoldingsTurnover() {
//        HoldingsTurnover = 0;
//        for (Holding holding : this.Holdings.values())
//        {
//            HoldingsTurnover += holding.getQuantity() * holding.getStock().getPrice();
//        }
//        return HoldingsTurnover;
//    }
    public List<UserCommandDTO> getAllAccountMovements() {
        return allAccountMovements;
    }

    public void AddFunds(int fundsToAdd) {
        int tempFunds=this.Funds;
        this.Funds += fundsToAdd;
        UserCommandDTO newCommand=new UserCommandDTO(UserCommandDTO.CmdType.CHARGE.toString(),fundsToAdd,tempFunds,this.Funds);

        this.allAccountMovements.add(newCommand);

    }
    public int getFunds() { return this.Funds; }

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
        int tempFunds=this.Funds;
        this.Funds += transaction.getTransactionQuantity() * transaction.getTransactionPrice();
        UserCommandDTO newCommand= new UserCommandDTO(UserCommandDTO.CmdType.BUY.toString(),transaction.getTransactionDate(),transaction.getTransactionPrice(),tempFunds,this.Funds);
        this.allAccountMovements.add(newCommand);
    }

    public void commitSellTransaction(Stock stock, TransactionDTO transaction) {
        Holding stockHolding = this.getHolding(stock.getSymbol());
        stockHolding.setQuantity(stockHolding.getQuantity() - transaction.getTransactionQuantity());

        if (stockHolding.getQuantity() == 0) {
            this.getHoldings().remove(stock.getSymbol());
        }
        int tempFunds=this.Funds;

        this.Funds -= transaction.getTransactionQuantity() * transaction.getTransactionPrice();
        UserCommandDTO newCommand= new UserCommandDTO(UserCommandDTO.CmdType.SELL.toString(),transaction.getTransactionDate(),transaction.getTransactionPrice(),tempFunds,this.Funds);
        this.allAccountMovements.add(newCommand);

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
