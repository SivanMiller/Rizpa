import engine.ExchangeCollection;
import engine.Transaction;

import java.util.Set;

public class StockDTO {

    private String sCompanyName;
    private String sSymbol;
    private int nGate;
    private Set<Transaction> setTransaction; //DTO
    private int nTransactionNum;
    private int nTransactionSum;

    public String getCompanyName() {
        return sCompanyName;
    }

    public String getSymbol() {
        return sSymbol;
    }

    public int getGate() {
        return nGate;
    }

    public Set<Transaction> getSetTransaction() {
        return setTransaction;
    }

    public int getTransactionNum() {
        return nTransactionNum;
    }

    public int getTransactionSum() {
        return nTransactionSum;
    }

}
