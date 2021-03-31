package engine;

import objects.ExchangeCollectionDTO;
import objects.ExchangeDTO;
import objects.StockDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeCollection {

    private PriorityQueue<Command> pqBuyCommand;
    private PriorityQueue<Command> pqSellCommand;
    private List<Transaction> lstTransaction;


    public ExchangeCollection() {
        pqBuyCommand = new PriorityQueue<>(new SortCommands());
        pqSellCommand = new PriorityQueue<>(new SortCommands());
        lstTransaction = new ArrayList<>();
    }

    public ExchangeCollection(PriorityQueue<Command> pqBuyCommand,
                              PriorityQueue<Command> pqSellCommand,
                              List<Transaction> lstTransaction) {
        this.pqBuyCommand = pqBuyCommand;
        this.pqSellCommand = pqSellCommand;
        this.lstTransaction = lstTransaction;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeCollection)) return false;
        ExchangeCollection that = (ExchangeCollection) o;
        return Objects.equals(getPqBuyCommand(), that.getPqBuyCommand()) && Objects.equals(getPqSellCommand(), that.getPqSellCommand()) && Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPqBuyCommand(), getPqSellCommand(), getTransactions());
    }


    public void addNewTransaction(int nPrice, int nQuantity, String sDate, int Order) {
        Transaction trNewTransaction = new Transaction(nPrice, nQuantity, sDate, nPrice * nQuantity, Order);
        // Adding to Transaction set
        lstTransaction.add(trNewTransaction);
    }

    public void addNewCommand(Command cmdNewCommand){

        //Check command type
        if (cmdNewCommand.getType() == Command.CmdType.BUY)
        {
            addBuyCommand(cmdNewCommand);
        }
        else if (cmdNewCommand.getType() == Command.CmdType.SELL)
        {
            addSellCommand(cmdNewCommand);
        }

    }

    private void addBuyCommand(Command cmdNewCommand) {
        int nQuantity;
        String sDate;
        while (cmdNewCommand.getQuantity() > 0) {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if (this.pqSellCommand.isEmpty() || this.pqSellCommand.peek().getPrice() > cmdNewCommand.getPrice()) {
                try {
                    this.pqBuyCommand.add(cmdNewCommand);
                }
                catch(ClassCastException | NullPointerException | IllegalStateException | IllegalArgumentException e)
                {
                    e.getMessage();
                }
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
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
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
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(this.pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                    this.pqBuyCommand.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(this.pqBuyCommand.peek().getPrice(), this.pqBuyCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);
                    //Decrease Command quantity by peek quantity
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    this.pqBuyCommand.remove(this.pqBuyCommand.peek());
                }
            }
        }
    }

    public ExchangeCollectionDTO convertToDTO()
    {
        List<ExchangeDTO> lstBuyCommand = new ArrayList<>();
        List<ExchangeDTO> lstSellCommand = new ArrayList<>();
        List<ExchangeDTO> lstTransaction = new ArrayList<>();

        PriorityQueue<Command> temp = new PriorityQueue<>(this.pqBuyCommand);
        for (int i = 0; i < this.pqBuyCommand.size(); i++)
        {
            lstBuyCommand.add(temp.poll().convertToDTO());
        }

        temp = new PriorityQueue<>(this.pqSellCommand);
        for (int i = 0; i < this.pqSellCommand.size(); i++)
        {
            lstSellCommand.add(temp.poll().convertToDTO());
        }

        for (Transaction tran : this.lstTransaction)
        {
            lstTransaction.add(tran.convertToDTO());
        }

        return new ExchangeCollectionDTO(lstBuyCommand, lstSellCommand, lstTransaction);

    }

    @Override
    public String toString() {
        return "ExchangeCollection{" +
                "pqBuyCommand=" + pqBuyCommand +
                ", pqSellCommand=" + pqSellCommand +
                ", lstTransaction=" + lstTransaction +
                '}';
    }
}
