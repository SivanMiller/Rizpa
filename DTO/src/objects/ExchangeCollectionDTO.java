package objects;

import engine.Command;
import engine.Transaction;

import java.util.List;
import java.util.PriorityQueue;


public class ExchangeCollectionDTO {

    private List<ExchangeDTO> lstBuyCommand;
    private List<ExchangeDTO> lstSellCommand;
    private List<ExchangeDTO> lstTransaction;

    public ExchangeCollectionDTO(List<ExchangeDTO> lstBuyCommand,
                                 List<ExchangeDTO> lstSellCommand,
                                 List<ExchangeDTO> lstTransaction) {
        this.lstBuyCommand = lstBuyCommand;
        this.lstSellCommand = lstSellCommand;
        this.lstTransaction = lstTransaction;
    }

    public List<ExchangeDTO> getBuyCommand() {
        return lstBuyCommand;
    }

    public List<ExchangeDTO> getSellCommand() {
        return lstSellCommand;
    }

    public List<ExchangeDTO> getTransaction() {
        return lstTransaction;
    }

    //TODO: IF TRANSACTION LIST IS EMPTY, PRINT MESSAGE EMPTY LIST
    @Override
    public String toString() {
        String str = "";

//        //Print Buy Commands if there are any
//        if (!lstBuyCommand.isEmpty()) {
//            str += "Buy Commands: " + '\n';
//            for (ExchangeDTO buy : lstBuyCommand) {
//                str += '\t' + buy.toString() + '\n';
//            }
//        }
//
//        //Print Sell Commands if there are any
//        if (!lstSellCommand.isEmpty()) {
//            str += "Sell Commands: " + '\n';
//            for (ExchangeDTO sell : lstSellCommand) {
//                str += '\t' + sell.toString() + '\n';
//            }
//        }

        //Print Transactions if there are any
        if (!lstTransaction.isEmpty()) {
            str += "Transactions: " + '\n';
            for (ExchangeDTO tran : lstTransaction) {
                str +=  '\t' + tran.toString() + '\n';
            }
        }

        return str;
    }
}
