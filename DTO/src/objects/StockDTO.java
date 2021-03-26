package objects;

import engine.Stock;
import engine.Transaction;

import java.util.List;
import java.util.Set;

public class StockDTO {

    private String sCompanyName;
    private String sSymbol;
    private int nPrice;
    private ExchangeCollectionDTO excExchange;
    private int nTransactionNum;
    private int nTransactionSum;

    public StockDTO(String sCompanyName, String sSymbol, int nPrice,
                    ExchangeCollectionDTO excExchange, int nTransactionNum, int nTransactionSum) {
        this.sCompanyName = sCompanyName;
        this.sSymbol = sSymbol;
        this.nPrice = nPrice;
        this.excExchange = excExchange;
        this.nTransactionNum = nTransactionNum;
        this.nTransactionSum = nTransactionSum;
    }

    public String getCompanyName() {
        return sCompanyName;
    }

    public String getSymbol() {
        return sSymbol;
    }

    public int getGate() {
        return nPrice;
    }

    public int getTransactionNum() {
        return nTransactionNum;
    }

    public int getTransactionSum() {
        return nTransactionSum;
    }


    public ExchangeCollectionDTO getExcExchange() {
        return excExchange;
    }

    @Override
    public String toString() {
        return "Stock:" + '\n' +
                "CompanyName = '" + sCompanyName + '\'' + '\n' +
                "Symbol='" + sSymbol + '\'' + '\n' +
                "Price=" + nPrice + '\n' +
                excExchange.toString() +
                "TransactionNum=" + nTransactionNum + '\n' +
                "TransactionSum=" + nTransactionSum + '\n';
    }
}
