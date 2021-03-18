package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import engine.Command;

public class ExchangeCollection {

    private PriorityQueue<Command> pqBuyCommand;
    private PriorityQueue<Command> pqSellCommand;
    private Set<Transaction> setTransaction;


    public ExchangeCollection() {
        pqBuyCommand = new PriorityQueue<>(new SortCommands());
        pqSellCommand = new PriorityQueue<>(new SortCommands());
        setTransaction = new HashSet<>();
    }

    public ExchangeCollection(PriorityQueue<Command> pqBuyCommand, PriorityQueue<Command> pqSellCommand,
                              Set<Transaction> setTransaction) {
        this.pqBuyCommand = pqBuyCommand;
        this.pqSellCommand = pqSellCommand;
        this.setTransaction = setTransaction;
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

    public Set<Transaction> getSetTransaction() {
        return setTransaction;
    }

    public void setSetTransaction(Set<Transaction> setTransaction) {
        this.setTransaction = setTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeCollection)) return false;
        ExchangeCollection that = (ExchangeCollection) o;
        return Objects.equals(getPqBuyCommand(), that.getPqBuyCommand()) && Objects.equals(getPqSellCommand(), that.getPqSellCommand()) && Objects.equals(getSetTransaction(), that.getSetTransaction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPqBuyCommand(), getPqSellCommand(), getSetTransaction());
    }

    @Override
    public String toString() {
        return "ExchangeCollection{" +
                "pqBuyCommand=" + pqBuyCommand +
                ", pqSellCommand=" + pqSellCommand +
                ", setTransaction=" + setTransaction +
                '}';
    }

    public void addNewTransation(int nPrice, int nQuantity, String sDate, int Order) {
        Transaction trNewTransaction = new Transaction(nPrice, nQuantity, sDate, nPrice * nQuantity, Order);
        // Adding to Transaction set
        setTransaction.add(trNewTransaction);
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
                    addNewTransation(pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // delete Sell command
                    pqSellCommand.remove(pqSellCommand.peek());
                }
                // there is more Stock to sell
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(pqSellCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    // update Sell command??
                    pqSellCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(pqSellCommand.peek().getPrice(), pqSellCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);

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
                    addNewTransation(cmdNewCommand.getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);

                    // delete Buy command
                    pqBuyCommand.remove(pqBuyCommand.peek());
                }
                //The quantity in the BUY collection is larger
                else if (nQuantity > 0) {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(pqBuyCommand.peek().getPrice(), cmdNewCommand.getQuantity(), sDate, cmdNewCommand.Order);
                    cmdNewCommand.setQuantity(0);
                    //Decrease peek quantity by Command quantity
                   pqBuyCommand.peek().setQuantity(nQuantity);
                }
                //The quantity in the BUY collection is smaller ----> nQuantity < 0
                else {
                    //sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(pqBuyCommand.peek().getPrice(), pqBuyCommand.peek().getQuantity(), sDate, cmdNewCommand.Order);
                    //Decrease Command quantity by peek quantity
                    cmdNewCommand.setQuantity(nQuantity * (-1));

                    // delete Sell command
                    pqBuyCommand.remove(pqBuyCommand.peek());
                }
            }
        }
    }
}
