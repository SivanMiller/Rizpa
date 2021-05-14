package engine;

import objects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeCollection {

    private PriorityQueue<Command> BuyCommand;
    private PriorityQueue<Command> SellCommand;
    private List<Transaction> Transaction;
    private int LastPrice;
    private int Turnover;

    public ExchangeCollection(int LastPrice) {
       this.BuyCommand = new PriorityQueue<>(new SortCommands());
       this.SellCommand = new PriorityQueue<>(new SortCommands());
       this.Transaction = new ArrayList<>();
       this.LastPrice = LastPrice;
    }

    public PriorityQueue<Command> getBuyCommand() {
        return BuyCommand;
    }

    public void setBuyCommand(PriorityQueue<Command> buyCommand) {
        this.BuyCommand = buyCommand;
    }

    public PriorityQueue<Command> getSellCommand() {
        return SellCommand;
    }

    public void setSellCommand(PriorityQueue<Command> sellCommand) {
        this.SellCommand = sellCommand;
    }

    public List<Transaction> getTransactions() {
        return Transaction;
    }

    public void setTransactions(List<Transaction> lstTransaction) {
        this.Transaction = lstTransaction;
    }

    public void setLastPrice(int LastPrice) { this.LastPrice = LastPrice;  }

    public int getLastPrice() { return LastPrice;  }

    public int getTurnover() {  return Turnover; }

    public int getLastTransactionPrice() { return LastPrice; }

    public void addNewTransaction(Transaction newTransaction) {
       // Adding to Transaction set
        Transaction.add(newTransaction);

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
        NewCmdOutcomeDTO outcome = new NewCmdOutcomeDTO();
        int nQuantity;
        String Date;
        while (NewCommand.getQuantity() > 0) {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if (this.SellCommand.isEmpty() ||
                     (this.SellCommand.peek().getPrice() > NewCommand.getPrice() &&
                      NewCommand.getPrice() != 0)) {
                //MKT Command
                if (NewCommand.getPrice() == 0) {
                    NewCommand.setPrice(getLastTransactionPrice());
                }

                this.BuyCommand.add(NewCommand);
                outcome.addCommand(NewCommand.convertToDTO());

                break;
            }
            //there is a compatibility of prices! lets BUY!
            else {
                nQuantity = this.SellCommand.peek().getQuantity() - NewCommand.getQuantity();

                Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(this.SellCommand.peek().getPrice(), NewCommand.getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), this.SellCommand.peek().getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());
                    NewCommand.setQuantity(0);
                    // delete Sell command
                    this.SellCommand.remove(this.SellCommand.peek());
                }
                // there is more Stock to sell
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(this.SellCommand.peek().getPrice(), NewCommand.getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), this.SellCommand.peek().getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());
                    NewCommand.setQuantity(0);
                    // update Sell command
                    this.SellCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(this.SellCommand.peek().getPrice(), this.SellCommand.peek().getQuantity(), Date, NewCommand.Order, NewCommand.getUser(), this.SellCommand.peek().getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());
                    // update BUY command??
                    NewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    this.SellCommand.remove(this.SellCommand.peek());
                }
            }
        }
        return outcome;
    }

    private NewCmdOutcomeDTO addSellCommand(Command NewCommand) {
        NewCmdOutcomeDTO outcome = new NewCmdOutcomeDTO();
        int nQuantity;
        String sDate;
        //There are still more stock to sell
        while (NewCommand.getQuantity() > 0) {
            //All the others are cheaper, then there is no deal!
            if (this.BuyCommand.isEmpty() ||
                    this.BuyCommand.peek().getPrice() < NewCommand.getPrice()) {
                //MKT Command
                if (NewCommand.getPrice() == 0) {
                    NewCommand.setPrice(this.getLastTransactionPrice());
                }

                this.SellCommand.add(NewCommand);
                outcome.addCommand(NewCommand.convertToDTO());
                break;
            }
            //there is a compatibility of prices! lets SELL!
            else {
                nQuantity = this.BuyCommand.peek().getQuantity() - NewCommand.getQuantity();
                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction

                    Transaction NewTransaction = new Transaction(this.BuyCommand.peek().getPrice(), NewCommand.getQuantity(), sDate, NewCommand.Order, this.BuyCommand.peek().getUser(), NewCommand.getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());
                    NewCommand.setQuantity(0);

                    // delete Buy command
                    this.BuyCommand.remove(this.BuyCommand.peek());
                }
                //The quantity in the BUY collection is larger
                else if (nQuantity > 0) {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(this.BuyCommand.peek().getPrice(), NewCommand.getQuantity(), sDate, NewCommand.Order, this.BuyCommand.peek().getUser(), NewCommand.getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());
                    NewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                    this.BuyCommand.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    // add new Transaction
                    Transaction NewTransaction = new Transaction(this.BuyCommand.peek().getPrice(), this.BuyCommand.peek().getQuantity(), sDate, NewCommand.Order, this.BuyCommand.peek().getUser(), NewCommand.getUser());
                    addNewTransaction(NewTransaction);
                    outcome.addTransaction(NewTransaction.convertToDTO());

                    //Decrease Command quantity by peek quantity
                    NewCommand.setQuantity(nQuantity * (-1));

                    // delete Buy command
                    this.BuyCommand.remove(this.BuyCommand.peek());
                }
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

        PriorityQueue<Command> temp = new PriorityQueue<>(this.BuyCommand);
        for (int i = 0; i < this.BuyCommand.size(); i++) {
            nTempSumBuyCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstBuyCommand.add(temp.poll().convertToDTO());
        }

        temp = new PriorityQueue<>(this.SellCommand);
        for (int i = 0; i < this.SellCommand.size(); i++) {
            nTempSumSellCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstSellCommand.add(temp.poll().convertToDTO());
        }

        for (int i = this.Transaction.size() - 1; i >= 0 ; i--) {
            nTempSumTransaction += this.Transaction.get(i).getTurnover();
            lstTransaction.add(this.Transaction.get(i).convertToDTO());
        }

        return new ExchangeCollectionDTO(lstBuyCommand, lstSellCommand, lstTransaction,nTempSumBuyCommand,nTempSumSellCommand,nTempSumTransaction);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeCollection)) return false;
        ExchangeCollection that = (ExchangeCollection) o;
        return LastPrice == that.LastPrice && Objects.equals(getBuyCommand(), that.getBuyCommand()) && Objects.equals(getSellCommand(), that.getSellCommand()) && Objects.equals(Transaction, that.Transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyCommand(), getSellCommand(), Transaction, LastPrice);
    }

    @Override
    public String toString() {
        return "ExchangeCollection{" +
                "pqBuyCommand=" + BuyCommand +
                ", pqSellCommand=" + SellCommand +
                ", lstTransaction=" + Transaction +
                ", nLastPrice=" + LastPrice +
                '}';
    }
}
