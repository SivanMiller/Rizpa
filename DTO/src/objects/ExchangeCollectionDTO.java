package objects;

import java.util.List;


public class ExchangeCollectionDTO {

    private List<CommandDTO> lstBuyCommand;
    private List<CommandDTO> lstSellCommand;
    private List<TransactionDTO> lstTransaction;

    public ExchangeCollectionDTO(List<CommandDTO> lstBuyCommand,
                                 List<CommandDTO> lstSellCommand,
                                 List<TransactionDTO> lstTransaction) {
        this.lstBuyCommand = lstBuyCommand;
        this.lstSellCommand = lstSellCommand;
        this.lstTransaction = lstTransaction;
    }

    public List<CommandDTO> getBuyCommand() {
        return lstBuyCommand;
    }

    public List<CommandDTO> getSellCommand() {
        return lstSellCommand;
    }

    public List<TransactionDTO> getTransaction() {
        return lstTransaction;
    }

    @Override
    public String toString() {
        String str = "";

        str += "Buy Commands: " + '\n';
        //Print Buy Commands if there are any
        if (!lstBuyCommand.isEmpty()) {
            for (CommandDTO buy : lstBuyCommand) {
                str += '\t' + buy.toString() + '\n';
            }
        }
        else{
            str += '\t' + "There are no unfinished BUY Commands" + '\n';
        }

        str += "Sell Commands: " + '\n';
        //Print Sell Commands if there are any
        if (!lstSellCommand.isEmpty()) {
            for (CommandDTO sell : lstSellCommand) {
                str += '\t' + sell.toString() + '\n';
            }
        }
        else{
            str += '\t' + "There are no unfinished SELL Commands" + '\n';
        }


        str += "Transactions: " + '\n';
        //Print Transactions if there are any
        if (!lstTransaction.isEmpty()) {
            for (TransactionDTO tran : lstTransaction) {
                str += '\t' + tran.toString() + '\n';
            }
        }
        else{
            str += '\t' + "There are no transactions" + '\n';
        }
        return str;
    }
}
