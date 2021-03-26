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
        return "StockDTO{" +
                "CompanyName='" + sCompanyName + '\'' +
                ", Symbol='" + sSymbol + '\'' +
                ", Price=" + nPrice +
                ", Exchange Collection =" + excExchange.toString() + //TODO CHECK NOT EMPTY
                ", TransactionNum=" + nTransactionNum +
                ", TransactionSum=" + nTransactionSum +
                '}';
    }
}
