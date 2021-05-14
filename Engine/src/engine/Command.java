package engine;

import objects.CommandDTO;
import objects.CommandDTOS;

import java.util.Comparator;
import java.util.Objects;

public abstract class Command {

    protected static int ORDER_COUNTER = 1;
    public enum CmdDirection {
        BUY,
        SELL
    };

    protected int Price;
    protected int Quantity;
    protected String Date;
    protected CmdDirection Direction;
    protected int Order;
    protected User user;

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user;  }

    public int getPrice() {  return Price;  }

    public void setPrice(int nPrice) {
        this.Price = nPrice;
    }

    public int getQuantity() {  return Quantity;   }

    public void setQuantity(int nQuantity) {
        this.Quantity = nQuantity;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String sDate) {
        this.Date = sDate;
    }

    public CmdDirection getDirection() { return this.Direction;  }

    public void setDirection(CmdDirection direction) {  this.Direction = direction; }

    public abstract CommandDTO convertToDTO();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return Price == command.Price && Quantity == command.Quantity && Direction == command.Direction && Date.equals(command.Date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Price, Quantity, Date, Direction);
    }

    @Override
    public abstract String toString();

}

class SortCommands implements Comparator<Command> {

    @Override
    public int compare(Command cmd1, Command cmd2) {
        // if type is BUY the order should be by price descending
        if (cmd1.getDirection() == Command.CmdDirection.BUY) {
            // if same price check order
            if (cmd1.getPrice() ==  cmd2.getPrice()) {
                // cmd2 should be first
                if (cmd1.Order > cmd2.Order)
                    return 1;
                // cmd1 should be first
                else
                    return -1;
            }
            // cmd2 should be first
            else if (cmd1.getPrice() <  cmd2.getPrice())
                return 1;
            // cmd1 should be first
            else
                return -1;
        }

        // if type os SELL the order should be by price ascending
        else if (cmd1.getDirection() == Command.CmdDirection.SELL) {
            // if same price check order
            if (cmd1.getPrice() ==  cmd2.getPrice()) {
                // cmd2 should be first
                if (cmd1.Order > cmd2.Order)
                    return 1;
                else
                    return -1;
            }
            // cmd2 should be first
            else if (cmd1.getPrice() >  cmd2.getPrice())
                return 1;
            // cmd1 should be first
            else
                return -1;
        }
        return 0;

    }
}
