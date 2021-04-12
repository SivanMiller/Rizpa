package objects;

import java.util.List;


public class ExchangeCollectionDTO {

    private List<CommandDTO> BuyCommand;
    private List<CommandDTO> SellCommand;
    private List<TransactionDTO> Transaction;
    private int SumBuyCommand;
    private int SumSellCommand;
    private int SumTransaction;

    public ExchangeCollectionDTO(List<CommandDTO> BuyCommand, List<CommandDTO> SellCommand,
                                 List<TransactionDTO> Transaction, int SumBuyCommand, int SumSellCommand,
                                 int SumTransaction) {
        this.BuyCommand = BuyCommand;
        this.SellCommand = SellCommand;
        this.Transaction = Transaction;
        this.SumBuyCommand = SumBuyCommand;
        this.SumSellCommand = SumSellCommand;
        this.SumTransaction = SumTransaction;
    }

    public List<CommandDTO> getBuyCommand() {
        return BuyCommand;
    }

    public List<CommandDTO> getSellCommand() {
        return SellCommand;
    }

    public List<TransactionDTO> getTransaction() {
        return Transaction;
    }

    @Override
    public String toString() {
        String str = "";

        str += "Buy Commands: " + '\n';
        //Print Buy Commands if there are any
        if (!BuyCommand.isEmpty()) {
            for (CommandDTO buy : BuyCommand) {
                str += '\t' + buy.toString() + '\n';
            }
            str += "Buy Commands' turnover SUM:" +'\t' + SumBuyCommand + '\n';

        }
        else{
            str += '\t' + "There are no unfinished BUY Commands" + '\n';
        }

        str += "Sell Commands: " + '\n';
        //Print Sell Commands if there are any
        if (!SellCommand.isEmpty()) {
            for (CommandDTO sell : SellCommand) {
                str += '\t' + sell.toString() + '\n';
            }
            str += "Sell Commands' turnover SUM:" +'\t' + SumSellCommand + '\n';

        }
        else {
            str += '\t' + "There are no unfinished SELL Commands" + '\n';
        }


        str += "Transactions: " + '\n';
        //Print Transactions if there are any
        if (!Transaction.isEmpty()) {
            for (TransactionDTO tran : Transaction) {
                str += '\t' + tran.toString() + '\n';
            }
            str += "Transactions' turnover SUM:" +'\t'+ SumTransaction + '\n';

        }
        else {
            str += '\t' + "There are no transactions" + '\n';
        }
        return str;
    }
}
