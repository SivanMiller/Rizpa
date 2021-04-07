package engine;

import objects.CommandDTO;
import objects.ExchangeCollectionDTO;
import objects.TransactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeCollection {

    private PriorityQueue<Command> pqBuyCommand;
    private PriorityQueue<Command> pqSellCommand;
    private List<Transaction> lstTransaction;
    private int nLastPrice;
    private int nTurnover;

    public ExchangeCollection(int nLastPrice) {
       this.pqBuyCommand = new PriorityQueue<>(new SortCommands());
       this.pqSellCommand = new PriorityQueue<>(new SortCommands());
       this.lstTransaction = new ArrayList<>();
       this.nLastPrice = nLastPrice;
    }

    public PriorityQueue<Command> getPqBuyCommand() {
        return pqBuyCommand;
    }

    public void setPqBuyCommand(PriorityQueue<Command> pqBuyCommand) {
        this.pqBuyCommand = pqBuyCommand;
    }

    public PriorityQueue<Command> getPqSellCommand() {
        return pqSellCommand;
    }

    public void setPqSellCommand(PriorityQueue<Command> pqSellCommand) {
        this.pqSellCommand = pqSellCommand;
    }

    public List<Transaction> getTransactions() {
        return lstTransaction;
    }

    public void setTransactions(List<Transaction> lstTransaction) {
        this.lstTransaction = lstTransaction;
    }

    public void setLastPrice(int nLastPrice) { this.nLastPrice = nLastPrice;  }

    public int getLastPrice() { return nLastPrice;  }

    public int getTurnover() {
        return nTurnover;
    }

    public void addNewTransaction(int nPrice, int nQuantity, String sDate, int Order) {
        Transaction trNewTransaction = new Transaction(nPrice, nQuantity, sDate, nPrice * nQuantity, Order);
        // Adding to Transaction set
        lstTransaction.add(trNewTransaction);
        this.nLastPrice = nPrice;
        // Sum of all transactions turnover
        this.nTurnover += nPrice * nQuantity;
    }

    public int LastTransactionPrice()
    {
        return nLastPrice;
    }

    public void addNewCommand(Command cmdNewCommand){

        //Check command type
        if (cmdNewCommand.getCmdDirection() == Command.CmdDirection.BUY)
        {
            addBuyCommand(cmdNewCommand);
        }
        else if (cmdNewCommand.getCmdDirection() == Command.CmdDirection.SELL)
        {
            addSellCommand(cmdNewCommand);
        }

    }

    private void addBuyCommand(Command cmdNewCommand) {
        int nQuantity;
        String sDate;
        while (cmdNewCommand.getQuantity() > 0) {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if (this.pqSellCommand.isEmpty() || (this.pqSellCommand.peek().getPrice() > cmdNewCommand.getPrice() && cmdNewCommand.getPrice() != 0)) {

                //MKT Command
                if (cmdNewCommand.getPrice() == 0)
                {
                    cmdNewCommand.setPrice(LastTransactionPrice());
                }

                this.pqBuyCommand.add(cmdNewCommand);

                break;
            }
            //there is a compatibility of prices! lets BUY!
            else {
                nQuantity = this.pqSellCommand.peek().getQuantity() - cmdNewCommand.getQuantity();

                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    addNewTransaction(this.pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // delete Sell command
                    this.pqSellCommand.remove(this.pqSellCommand.peek());
                }
                // there is more Stock to sell
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(this.pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // update Sell command??
                    this.pqSellCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    // add new Transaction
                    addNewTransaction(this.pqSellCommand.peek().getPrice(), this.pqSellCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);

                    // update BUY command??
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    this.pqSellCommand.remove(this.pqSellCommand.peek());
                }
            }
        }
    }

    private void addSellCommand(Command cmdNewCommand) {
        int nQuantity;
        String sDate;
        //There are still more stock to sell
        while (cmdNewCommand.getQuantity() > 0) {
            //All the others are cheaper, then there is no deal!
            if (this.pqBuyCommand.isEmpty() || this.pqBuyCommand.peek().getPrice() < cmdNewCommand.getPrice()) {

                //MKT Command
                if (cmdNewCommand.getPrice() == 0)
                {
                    cmdNewCommand.setPrice(this.LastTransactionPrice());
                }

                this.pqSellCommand.add(cmdNewCommand);
                break;
            }
            //there is a compatibility of prices! lets SELL!
            else {
                nQuantity = this.pqBuyCommand.peek().getQuantity() - cmdNewCommand.getQuantity();
                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    addNewTransaction(this.pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);

                    // delete Buy command
                    this.pqBuyCommand.remove(this.pqBuyCommand.peek());
                }
                //The quantity in the BUY collection is larger
                else if (nQuantity > 0) {
                    // add new Transaction
                    addNewTransaction(this.pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                    this.pqBuyCommand.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    // add new Transaction
                    addNewTransaction(this.pqBuyCommand.peek().getPrice(), this.pqBuyCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);
                    //Decrease Command quantity by peek quantity
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Buy command
                    this.pqBuyCommand.remove(this.pqBuyCommand.peek());
                }
            }
        }
    }

    public ExchangeCollectionDTO convertToDTO()
    {
        List<CommandDTO> lstBuyCommand = new ArrayList<>();
        List<CommandDTO> lstSellCommand = new ArrayList<>();
        List<TransactionDTO> lstTransaction = new ArrayList<>();
        int nTempSumBuyCommand = 0;
        int nTempSumSellCommand = 0;
        int nTempSumTransaction = 0;

        PriorityQueue<Command> temp = new PriorityQueue<>(this.pqBuyCommand);
        for (int i = 0; i < this.pqBuyCommand.size(); i++)
        {
            nTempSumBuyCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstBuyCommand.add(temp.poll().convertToDTO());
        }

        temp = new PriorityQueue<>(this.pqSellCommand);
        for (int i = 0; i < this.pqSellCommand.size(); i++)
        {
            nTempSumSellCommand += temp.peek().getPrice() * temp.peek().getQuantity();
            lstSellCommand.add(temp.poll().convertToDTO());
        }

        for (int i = this.lstTransaction.size() - 1; i >= 0 ; i--)
        {
            nTempSumTransaction += this.lstTransaction.get(i).getTurnover();
            lstTransaction.add(this.lstTransaction.get(i).convertToDTO());
        }

        return new ExchangeCollectionDTO(lstBuyCommand, lstSellCommand, lstTransaction,nTempSumBuyCommand,nTempSumSellCommand,nTempSumTransaction);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeCollection)) return false;
        ExchangeCollection that = (ExchangeCollection) o;
        return nLastPrice == that.nLastPrice && Objects.equals(getPqBuyCommand(), that.getPqBuyCommand()) && Objects.equals(getPqSellCommand(), that.getPqSellCommand()) && Objects.equals(lstTransaction, that.lstTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPqBuyCommand(), getPqSellCommand(), lstTransaction, nLastPrice);
    }

    @Override
    public String toString() {
        return "ExchangeCollection{" +
                "pqBuyCommand=" + pqBuyCommand +
                ", pqSellCommand=" + pqSellCommand +
                ", lstTransaction=" + lstTransaction +
                ", nLastPrice=" + nLastPrice +
                '}';
    }
}
