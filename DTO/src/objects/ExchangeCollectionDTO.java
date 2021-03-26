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

    @Override
    public String toString() {
        String str;

        str = "Buy Commands: " + '\n';
        for (ExchangeDTO buy : lstBuyCommand) {
            str += buy.toString() + '\n';
        }

        str = "Sell Commands: " + '\n';
        for (ExchangeDTO sell : lstSellCommand) {
            str += sell.toString() + '\n';
        }

        for (ExchangeDTO tran : lstTransaction) {
            str += tran.toString() + '\n';
        }

        return str;
    }
}
