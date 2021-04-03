package engine;

import objects.CommandDTO;

import java.util.Comparator;
import java.util.Objects;

public abstract class Command {

    static int order = 1;
    public enum CmdDirection {
        BUY,
        SELL
    };

    protected int nPrice;
    protected int nQuantity;
    protected String sDate;
    protected CmdDirection cmdDirection;
    protected int Order;

    public int getPrice() {
        return nPrice;
    }

    public void setPrice(int nPrice) {
        this.nPrice = nPrice;
    }

    public int getQuantity() {  return nQuantity;   }

    public void setQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public CmdDirection getCmdDirection() { return this.cmdDirection;  }

    public void setCmdDirection(CmdDirection cmdDirection) {
        this.cmdDirection = cmdDirection;
    }

    public abstract CommandDTO convertToDTO();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return nPrice == command.nPrice && nQuantity == command.nQuantity && cmdDirection == command.cmdDirection && sDate.equals(command.sDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nPrice, nQuantity, sDate, cmdDirection);
    }

    @Override
    public abstract String toString();

}

class SortCommands implements Comparator<Command>
{

    @Override
    public int compare(Command cmd1, Command cmd2) {
        // if type is BUY the order should be by price descending
        if (cmd1.getCmdDirection() == Command.CmdDirection.BUY) {
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
        else if (cmd1.getCmdDirection() == Command.CmdDirection.SELL) {
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
