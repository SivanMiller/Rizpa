package objects;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.List;

public class NewCmdOutcomeDTO {

    private CommandDTO NewCommand;
    private List<TransactionDTO> NewTransaction;

    public NewCmdOutcomeDTO() {
        this.NewTransaction = new ArrayList<>();
    }

    public List<TransactionDTO> getNewTransaction() { return NewTransaction; }

    public void addCommand(CommandDTO newCommand) {
        NewCommand = newCommand;
    }

    public void addTransaction(TransactionDTO newTransaction) {
        this.NewTransaction.add(newTransaction);
    }

    @Override
    public String toString() {
        String str = "";

//        if (!NewTransaction.isEmpty()) {
//            str += "Congratulations! A new transaction was made!" + '\n';
//            for (TransactionDTO tran : NewTransaction) {
//                str += '\t' + tran.toString() + '\n';
//            }
//        }
//        else {
//            str += "No transactions were made yet" + '\n';
//        }

        if (NewCommand != null) {
            if (NewCommand.getType().equals("LMT") || NewCommand.getType().equals("MKT")) {
                str += "The leftover command created is:" + '\n';
                str += NewCommand.toString();
            }
            else if (NewCommand.getType().equals("IOC") || NewCommand.getType().equals("FOK")) {
                if (NewTransaction.isEmpty()) {
                    str += "No transactions were made. The command is discarded";
                } else {
                    str += "Transactions were made, no leftover command is created.";
                }
            }
        }
        else {
            str += "The command was completed";
        }

        return str;
    }
}
