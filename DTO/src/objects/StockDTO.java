package objects;

public class StockDTO {

    private String sCompanyName;
    private String sSymbol;
    private int nPrice;
    private ExchangeCollectionDTO excExchange;
    private int nTransactionNum; // Transactions Number
    private int nTurnover; // Transactions sum of turnovers

    public StockDTO(String sCompanyName, String sSymbol, int nPrice,
                    ExchangeCollectionDTO excExchange, int nTransactionNum, int nTurnover) {
        this.sCompanyName = sCompanyName;
        this.sSymbol = sSymbol;
        this.nPrice = nPrice;
        this.excExchange = excExchange;
        this.nTransactionNum = nTransactionNum;
        this.nTurnover = nTurnover;
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
        return nTurnover;
    }


    public ExchangeCollectionDTO getExcExchange() {
        return excExchange;
    }


    @Override
    public String toString() {
        return "Stock:" + '\n' +
                "CompanyName = '" + sCompanyName + '\'' + '\n' +
                "Symbol = '" + sSymbol + '\'' + '\n' +
                "Price = " + nPrice + '\n' +
                "Transactions Number = " + nTransactionNum + '\n' +
                "Transactions Turnover Sum = " + nTurnover + '\n';
    }

    public String PrintAllCommands()
    {

        return this.toString()+excExchange.toString();
    }

    public String PrintTransaction()
    {
        String str = "Transactions: " + '\n';
        //Print Transactions if there are any
        if (!excExchange.getTransaction().isEmpty()) {
            for (TransactionDTO tran : excExchange.getTransaction()) {
                str += '\t' + tran.toString() + '\n';
            }
        }
        else{
            str +='\t' + "There are no transactions " + '\n';
        }
        return str;
    }
}
