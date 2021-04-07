package objects;

import java.util.List;


public class ExchangeCollectionDTO {

    private List<CommandDTO> lstBuyCommand;
    private List<CommandDTO> lstSellCommand;
    private List<TransactionDTO> lstTransaction;
    private int nSumBuyCommand;
    private int nSumSellCommand;
    private int nSumTransaction;

    public void setSumBuyCommand(int nSumBuyCommand) {
        this.nSumBuyCommand = nSumBuyCommand;
    }

    public void setSumSellCommand(int nSumSellCommand) {
        this.nSumSellCommand = nSumSellCommand;
    }

    public void setSumTransaction(int nSumTransaction) {
        this.nSumTransaction = nSumTransaction;
    }


    public ExchangeCollectionDTO(List<CommandDTO> lstBuyCommand,List<CommandDTO> lstSellCommand,
                                 List<TransactionDTO> lstTransaction,int nSumBuyCommand,int nSumSellCommand,
                                       int nSumTransaction) {
        this.lstBuyCommand = lstBuyCommand;
        this.lstSellCommand = lstSellCommand;
        this.lstTransaction = lstTransaction;
        this.nSumBuyCommand=nSumBuyCommand;
        this.nSumSellCommand=nSumSellCommand;
        this.nSumTransaction=nSumTransaction;
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
            str += "Sum turnover of Buy Command:" +'\t' + nSumBuyCommand + '\n';

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
            str += "Sum turnover of Sell Command:" +'\t' + nSumSellCommand + '\n';

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
            str += "Sum turnover of Transaction:" +'\t'+ nSumTransaction  + '\n';

        }
        else{
            str += '\t' + "There are no transactions" + '\n';
        }
        return str;
    }
}
