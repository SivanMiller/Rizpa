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

    public ExchangeCollection(PriorityQueue<Command> pqBuyCommand, PriorityQueue<Command> pqSellCommand,
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

    @Override
    public String toString() {

        String str;
        PriorityQueue<Command> temp;

        str = "Buy Commands: " + '\n';
        temp = this.pqBuyCommand;
        for (int i = 0; i < this.pqBuyCommand.size(); i++)
        {
            str += this.pqBuyCommand.poll().toString() + '\n';
        }

        str = "Sell Commands: " + '\n';
        temp = this.pqSellCommand;
        for (int i = 0; i < this.pqSellCommand.size(); i++)
        {
            str += this.pqSellCommand.poll().toString() + '\n';
        }

        for (Transaction tran : lstTransaction) {
            str += tran.toString() + '\n';
        }

        return str;
    }

    public void addNewTransaction(int nPrice, int nQuantity, String sDate, int Order) {
        Transaction trNewTransaction = new Transaction(nPrice, nQuantity, sDate, nPrice * nQuantity, Order);
        // Adding to Transaction set
        lstTransaction.add(trNewTransaction);
    }

    public void addNewCommand(Command cmdNewCommand){

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
            if (pqSellCommand.isEmpty() || pqSellCommand.peek().getPrice() > cmdNewCommand.getPrice()) {
                pqBuyCommand.add(cmdNewCommand);
                break;
            }
            //there is a compatibility of prices! lets BUY!
            else {
                nQuantity = pqSellCommand.peek().getQuantity() - cmdNewCommand.getQuantity();

                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    addNewTransaction(pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // delete Sell command
                    pqSellCommand.remove(pqSellCommand.peek());
                }
                // there is more Stock to sell
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // update Sell command??
                    pqSellCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(pqSellCommand.peek().getPrice(), pqSellCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);

                    // update BUY command??
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    pqSellCommand.remove(pqSellCommand.peek());
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
            if (pqBuyCommand.isEmpty() || pqBuyCommand.peek().getPrice() < cmdNewCommand.getPrice()) {
                pqSellCommand.add(cmdNewCommand);
                break;
            }
            //there is a compatibility of prices! lets SELL!
            else {
                nQuantity = pqBuyCommand.peek().getQuantity() - cmdNewCommand.getQuantity();
                sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

                // the quantity is matching
                if (nQuantity == 0) {
                    // add new Transaction
                    addNewTransaction(pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);

                    // delete Buy command
                    pqBuyCommand.remove(pqBuyCommand.peek());
                }
                //The quantity in the BUY collection is larger
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                   pqBuyCommand.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransaction(pqBuyCommand.peek().getPrice(), pqBuyCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);
                    //Decrease Command quantity by peek quantity
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    pqBuyCommand.remove(pqBuyCommand.peek());
                }
            }
        }
    }
    public ExchangeCollectionDTO convertToDTO()
    {
        List<ExchangeDTO> lstBuyCommand = new ArrayList<>();
        List<ExchangeDTO> lstSellCommand = new ArrayList<>();
        List<ExchangeDTO> lstTransaction = new ArrayList<>();

        PriorityQueue<Command> temp;

        temp = this.pqBuyCommand;
        for (int i = 0; i < this.pqBuyCommand.size(); i++)
        {
            lstBuyCommand.add(this.pqBuyCommand.poll().convertToDTO());
        }
        temp = this.pqSellCommand;
        for (int i = 0; i < this.pqSellCommand.size(); i++)
        {
            lstSellCommand.add(this.pqSellCommand.poll().convertToDTO());
        }

        for (Transaction tran : this.lstTransaction)
        {
            lstTransaction.add(tran.convertToDTO());
        }

        return new ExchangeCollectionDTO(lstBuyCommand, lstSellCommand, lstTransaction);

    }
}
