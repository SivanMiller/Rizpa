package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeCollection {

    private PriorityQueue<Command> pqBuyCommand;
    private PriorityQueue<Command> pqSellCommand;
    private Set<Transaction> setTransaction;


    public ExchangeCollection() {
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

    public void addNewTransation(int nPrice, int nQuantity, String sDate) {
        Transaction trNewTransaction = new Transaction(nPrice, nQuantity, sDate, nPrice * nQuantity);
        // Adding to Transaction set
        setTransaction.add(trNewTransaction);
    }

    public void addBuyCommand(Command cmdNewCommand) {
        int nQuantity;
        String sDate;
        while (cmdNewCommand.getQuantity() != 0) {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if (pqSellCommand.isEmpty() || pqSellCommand.peek().getPrice() > cmdNewCommand.getPrice()) {
                pqBuyCommand.add(cmdNewCommand);
                break;
            }
            //there is a compatibility of prices! lets BUY!
            else {
                nQuantity = pqSellCommand.peek().getQuantity() - cmdNewCommand.getQuantity();

                // the quantity is matching
                if (nQuantity == 0) {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), cmdNewCommand.getQuantity(), sDate);
                    cmdNewCommand.setQuantity(0);
                    // delete Sell command??
                }
                // there is more Stock to sell
                else if (nQuantity > 0) {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), cmdNewCommand.getQuantity(), sDate);
                    cmdNewCommand.setQuantity(0);
                    // update Sell command??
                    pqSellCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to BUY----> nQuantity < 0
                else {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), pqSellCommand.peek().getQuantity(), sDate);
                    // update BUY command??
                    cmdNewCommand.setQuantity(nQuantity * (-1));
                    // delete Sell commad??
                }
            }
        }
    }

    public void addSellCommand(Command cmdNewCommand) {
        int nQuantity;
        String sDate;
        while (cmdNewCommand.getQuantity() != 0) {
            //All the other are cheap, then there is no deal!
            if (pqBuyCommand.isEmpty() || pqBuyCommand.peek().getPrice() < cmdNewCommand.getPrice()) {
                pqSellCommand.add(cmdNewCommand);
                break;
            }
            //there is a compatibility of prices! lets SELL!
            else {
                nQuantity = pqBuyCommand.peek().getQuantity()- cmdNewCommand.getQuantity();

                // the quantity is matching
                if (nQuantity == 0) {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), cmdNewCommand.getQuantity(), sDate);
                    cmdNewCommand.setQuantity(0);
                    // delete Buy command??
                }
                // there is more Stock to BUY
                else if (nQuantity > 0) {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), cmdNewCommand.getQuantity(), sDate);
                    cmdNewCommand.setQuantity(0);
                    // update BUY command??
                   pqBuyCommand.peek().setQuantity(nQuantity);
                }
                // there is more Stock to sell----> nQuantity < 0
                else {
                    sDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
                    // add new Transaction
                    addNewTransation(cmdNewCommand.getPrice(), pqSellCommand.peek().getQuantity(), sDate);
                    // update BUY command??
                    cmdNewCommand.setQuantity(nQuantity * (-1));
                    // delete Sell commad??
                }
            }
        }
    }
}
