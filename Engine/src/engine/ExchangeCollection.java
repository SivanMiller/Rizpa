package engine;

import objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeCollection {

    private PriorityQueue<Command> BuyCommands;
    private PriorityQueue<Command> SellCommands;
    private List<Transaction> Transactions;
    private int LastPrice;
    private int Turnover;

    public ExchangeCollection(int LastPrice) {
       this.BuyCommands = new PriorityQueue<>(new SortCommands());
       this.SellCommands = new PriorityQueue<>(new SortCommands());
       this.Transactions = new ArrayList<>();
       this.LastPrice = LastPrice;
    }

    public PriorityQueue<Command> getBuyCommands() {
        return BuyCommands;
    }

    public void setBuyCommands(PriorityQueue<Command> buyCommands) {
        this.BuyCommands = buyCommands;
    }

    public PriorityQueue<Command> getSellCommands() {
        return SellCommands;
    }

    public void setSellCommands(PriorityQueue<Command> sellCommands) {
        this.SellCommands = sellCommands;
    }

    public List<Transaction> getTransactions() {
        return Transactions;
    }

    public void setTransactions(List<Transaction> lstTransaction) {
        this.Transactions = lstTransaction;
    }

    public void setLastPrice(int LastPrice) { this.LastPrice = LastPrice;  }

    public int getLastPrice() { return LastPrice;  }

    public int getTurnover() {  return Turnover; }

    public int getLastTransactionPrice() { return LastPrice; }

    public void addNewTransaction(Transaction newTransaction) {
       // Adding to Transaction set
        Transactions.add(newTransaction);

        this.LastPrice = newTransaction.getPrice();

        // Sum of all transactions turnover
        this.Turnover += newTransaction.getPrice() * newTransaction.getQuantity();
    }

    public NewCmdOutcomeDTO addNewCommand(Command NewCommand) {
        //Check command type
        if (NewCommand.getDirection() == Command.CmdDirection.BUY) {
           return addBuyCommand(NewCommand);
        }
        else {
           return addSellCommand(NewCommand);
        }
    }

    private NewCmdOutcomeDTO addBuyCommand(Command NewCommand) {
        PriorityQueue<Command> tempSellCommands = new PriorityQueue<>(this.SellCommands);
        List<Transaction> tempTransactions = new ArrayList<>();

        NewCmdOutcomeDTO outcome = new NewCmdOutcomeDTO();
        int nQuantity;
        String Date;

        while (NewCommand.getQuantity() > 0) {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if (tempSellCommands.isEmpty() ||
                     (tempSellCommands.peek().getPrice() > NewCommand.getPrice() &&
                      NewCommand.getPrice() != 0)) {
                //MKT Command
                if (NewCommand.getPrice() == 0) {
                    NewCommand.setPrice(getLastTransactionPrice());
                }

                if (NewCommand.getClass() != FOKCommand.class && NewCommand.getClass() != IOCCommand.class)
                    this.BuyCommands.add(NewCommand);

                outcome.addCommand(NewCommand.convertToDTO());

                break;
            }
            //there is a compatibility of prices! lets BUY!
            else {
                nQuantity = tempSellCommands.peek().getQuantity() - NewCommand.getQuantity();

                Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(tempSellCommands.peek().getPrice(), NewCommand.getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), tempSellCommands.peek().getUser());
                    //addNewTransaction(NewTransaction);
                    //outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);
                    NewCommand.setQuantity(0);
                    // delete Sell command
                    tempSellCommands.remove(tempSellCommands.peek());
                }
                // there are more Stocks to sell
                else if (nQuantity > 0) {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(tempSellCommands.peek().getPrice(), NewCommand.getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), tempSellCommands.peek().getUser());
//                    addNewTransaction(NewTransaction);
//                    outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);
                    NewCommand.setQuantity(0);
                    // update Sell command
                    tempSellCommands.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(tempSellCommands.peek().getPrice(), tempSellCommands.peek().getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), tempSellCommands.peek().getUser());
//                    addNewTransaction(NewTransaction);
//                    outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);
                    // update BUY command??
                    NewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    tempSellCommands.remove(tempSellCommands.peek());
                }
            }
        }

        if (NewCommand.getQuantity() == 0 || NewCommand.getClass() != FOKCommand.class){
            this.SellCommands = tempSellCommands;
            for (Transaction transaction : tempTransactions) {
                addNewTransaction(transaction);
                outcome.addTransaction(transaction.convertToDTO());
            }
        }

        return outcome;
    }

    private NewCmdOutcomeDTO addSellCommand(Command NewCommand) {
        PriorityQueue<Command> tempBuyCommands = new PriorityQueue<>(this.BuyCommands);
        List<Transaction> tempTransactions = new ArrayList<>();
        NewCmdOutcomeDTO outcome = new NewCmdOutcomeDTO();
        int nQuantity;
        String sDate;
        //There are still more stock to sell
        while (NewCommand.getQuantity() > 0) {
            //All the others are cheaper, then there is no deal!
            if (tempBuyCommands.isEmpty() ||
                    tempBuyCommands.peek().getPrice() < NewCommand.getPrice()) {
                //MKT Command
                if (NewCommand.getPrice() == 0) {
                    NewCommand.setPrice(this.getLastTransactionPrice());
                }

                if (NewCommand.getClass() != FOKCommand.class && NewCommand.getClass() != IOCCommand.class)
                    this.SellCommands.add(NewCommand);
                outcome.addCommand(NewCommand.convertToDTO());
                break;
            }
            //there is a compatibility of prices! lets SELL!
            else {
                nQuantity = tempBuyCommands.peek().getQuantity() - NewCommand.getQuantity();
                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction

                    Transaction NewTransaction = new Transaction(tempBuyCommands.peek().getPrice(), NewCommand.getQuantity(), sDate, NewCommand.Order, tempBuyCommands.peek().getUser(), NewCommand.getUser());
//                    addNewTransaction(NewTransaction);
//                    outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);
                    NewCommand.setQuantity(0);

                    // delete Buy command
                    tempBuyCommands.remove(this.BuyCommands.peek());
                }
                //The quantity in the BUY collection is larger
                else if (nQuantity > 0) {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(tempBuyCommands.peek().getPrice(), NewCommand.getQuantity(), sDate, NewCommand.Order, tempBuyCommands.peek().getUser(), NewCommand.getUser());
//                    addNewTransaction(NewTransaction);
//                    outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);
                    NewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                    tempBuyCommands.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(tempBuyCommands.peek().getPrice(), tempBuyCommands.peek().getQuantity(), sDate, NewCommand.Order, tempBuyCommands.peek().getUser(), NewCommand.getUser());
//                    addNewTransaction(NewTransaction);
//                    outcome.addTransaction(NewTransaction.convertToDTO());
                    tempTransactions.add(NewTransaction);

                    //Decrease Command quantity by peek quantity
                    NewCommand.setQuantity(nQuantity * (-1));

                    // delete Buy command
                    tempBuyCommands.remove(tempBuyCommands.peek());
                }
            }
        }

        if (NewCommand.getQuantity() == 0 || NewCommand.getClass() != FOKCommand.class){
            this.BuyCommands = tempBuyCommands;
            for (Transaction transaction : tempTransactions) {
                addNewTransaction(transaction);
                outcome.addTransaction(transaction.convertToDTO());
            }
        }

        return outcome;
    }

    public ExchangeCollectionDTO convertToDTO() {
        List<CommandDTO> lstBuyCommand = new ArrayList<>();
        List<CommandDTO> lstSellCommand = new ArrayList<>();
        List<TransactionDTO> lstTransaction = new ArrayList<>();
        int nTempSumBuyCommand = 0;
        int nTempSumSellCommand = 0;
        int nTempSumTransaction = 0;

        PriorityQueue<Command> temp = new PriorityQueue<>(this.BuyCommands);
        for (int i = 0; i < this.BuyCommands.size(); i++) {
            nTempSumBuyCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstBuyCommand.add(temp.poll().convertToDTO());
        }

        temp = new PriorityQueue<>(this.SellCommands);
        for (int i = 0; i < this.SellCommands.size(); i++) {
            nTempSumSellCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstSellCommand.add(temp.poll().convertToDTO());
        }

        for (int i = this.Transactions.size() - 1; i >= 0 ; i--) {
            nTempSumTransaction += this.Transactions.get(i).getTurnover();
            lstTransaction.add(this.Transactions.get(i).convertToDTO());
        }

        return new ExchangeCollectionDTO(lstBuyCommand, lstSellCommand, lstTransaction,nTempSumBuyCommand,nTempSumSellCommand,nTempSumTransaction);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeCollection)) return false;
        ExchangeCollection that = (ExchangeCollection) o;
        return LastPrice == that.LastPrice && Objects.equals(getBuyCommands(), that.getBuyCommands()) && Objects.equals(getSellCommands(), that.getSellCommands()) && Objects.equals(Transactions, that.Transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyCommands(), getSellCommands(), Transactions, LastPrice);
    }

    @Override
    public String toString() {
        return "ExchangeCollection{" +
                "pqBuyCommand=" + BuyCommands +
                ", pqSellCommand=" + SellCommands +
                ", lstTransaction=" + Transactions +
                ", nLastPrice=" + LastPrice +
                '}';
    }
}
