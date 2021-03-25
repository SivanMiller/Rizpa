package objects;

import engine.Stock;
import engine.Transaction;

import java.util.Set;

public class StockDTO {

    private String sCompanyName;
    private String sSymbol;
    private int nPrice;
    private Set<ExchangeDTO> setTransaction;
    private int nTransactionNum;
    private int nTransactionSum;

    public StockDTO(String sCompanyName, String sSymbol, int nPrice,
                    Set<ExchangeDTO> setTransaction, int nTransactionNum, int nTransactionSum) {
        this.sCompanyName = sCompanyName;
        this.sSymbol = sSymbol;
        this.nPrice = nPrice;
        this.setTransaction = setTransaction;
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

    public Set<ExchangeDTO> getSetTransaction() {
        return setTransaction;
    }

    public int getTransactionNum() {
        return nTransactionNum;
    }

    public int getTransactionSum() {
        return nTransactionSum;
    }

    @Override
    public String toString() {
        return "StockDTO{" +
                "sCompanyName='" + sCompanyName + '\'' +
                ", sSymbol='" + sSymbol + '\'' +
                ", nPrice=" + nPrice +
                ", setTransaction=" + setTransaction + //TODO CHANGE TO TRANSACTION TOSTRING
                ", nTransactionNum=" + nTransactionNum +
                ", nTransactionSum=" + nTransactionSum +
                '}';
    }
}
