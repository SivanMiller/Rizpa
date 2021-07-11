package objects;

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

        if (!NewTransaction.isEmpty()) {
            str += "Congratulations! A new transaction was made!" + '\n';
            for (TransactionDTO tran : NewTransaction) {
                str += '\t' + tran.toString() + '\n';
            }
        }
        else {
            str += "No transactions were made yet" + '\n';
        }

        if (NewCommand != null) {
            str += "The command created is:" + '\n';
            str += '\t' + NewCommand.toString() + '\n';
        }
        else {
            str += "The command was completed" + '\n';
        }

        str += "Please see stock's Admin Tab for more information.";

        return str;
    }
}
