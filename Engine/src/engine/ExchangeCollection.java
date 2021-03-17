package engine;

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

    public void addCommand(Command cmdNewCommand)
    {
        //If this is a BUY Command
        if (cmdNewCommand.getDirection())
        {
            //if the cheapest sell Command is more expensive than my max price, then there is no deal!
            if(pqSellCommand.peek().getPrice() > cmdNewCommand.getPrice())
            {
                pqBuyCommand.add(cmdNewCommand);
            }
            //there is a compatibility of prices! lets BUY!
            else if (pqSellCommand.peek().getPrice() <= cmdNewCommand.getPrice())
            {
                // checking that the quantity is enough
                if (pqSellCommand.peek().getQuantity() >= cmdNewCommand.getQuantity())
                {

                }
            }
        }
    }
}
