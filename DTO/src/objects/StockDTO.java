package objects;

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

    public String PrintAllCommands()
    {
        String str = "Stock:" + '\n' +
                "CompanyName = '" + sCompanyName + '\'' + '\n' +
                "Symbol='" + sSymbol + '\'' + '\n';

        str += "Buy Commands: " + '\n';
        //Print Buy Commands if there are any
        if (!excExchange.getBuyCommand().isEmpty()) {
            for (ExchangeDTO buy : excExchange.getBuyCommand()) {
                str += '\t' + buy.toString() + '\n';
            }
        }
        else{
            str += '\t' + "There are no unfinished BUY Commands" + '\n';
        }
        str += "Sell Commands: " + '\n';
        //Print Sell Commands if there are any
        if (!excExchange.getSellCommand().isEmpty()) {
            for (ExchangeDTO sell : excExchange.getSellCommand()) {
                str += '\t' + sell.toString() + '\n';
            }
        }
        else{
            str += '\t' + "There are no unfinished SELL Commands" + '\n';
        }

        //Print Transactions if there are any
        if (!excExchange.getTransaction().isEmpty()) {
            str += "Transactions: " + '\n';
            for (ExchangeDTO tran : excExchange.getTransaction()) {
                str +=  '\t' + tran.toString() + '\n';
            }
        }

        return str;
    }
}
