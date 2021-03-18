package engine;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.Comparator;
import java.util.Objects;

public abstract class Command {

    //static int sell_order = 1;
    static int order = 1;
    public enum CmdType {
        BUY,
        SELL
    };

    protected int nPrice;
    protected int nQuantity;
    protected String sDate;
    protected CmdType Type;
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

    public CmdType getType() { return this.Type;  }

    public void setType(CmdType type) {
        this.Type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return nPrice == command.nPrice && nQuantity == command.nQuantity && Type == command.Type && sDate.equals(command.sDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nPrice, nQuantity, sDate, Type);
    }

    @Override
    public abstract String toString();

}
class SortCommands implements Comparator<Command>
{

    @Override
    public int compare(Command cmd1, Command cmd2) {
        if (cmd1.getType() == Command.CmdType.BUY) {
            if (cmd1.getPrice() ==  cmd2.getPrice()) {
                if (cmd1.Order > cmd2.Order) {
                    return 1;
                } else {
                    return -1;
                }
            }
            else if (cmd1.getPrice() <  cmd2.getPrice())
                return 1;
            else {
                return -1;
            }
        }
        else if (cmd1.getType() == Command.CmdType.SELL) {
            if (cmd1.getPrice() ==  cmd2.getPrice()) {
                if (cmd1.Order > cmd2.Order) {
                    return 1;
                } else {
                    return -1;
                }
            }
            else if (cmd1.getPrice() >  cmd2.getPrice())
                return 1;
            else {
                return -1;
            }
        }
        return 0;

    }
}
