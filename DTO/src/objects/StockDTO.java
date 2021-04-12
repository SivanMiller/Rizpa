package objects;

public class StockDTO {
    private String CompanyName;
    private String Symbol;
    private int Price;
    private ExchangeCollectionDTO ExchangeCollection;
    private int TransactionNum; // Transactions Number
    private int Turnover; // Transactions sum of turnovers

    public StockDTO(String CompanyName, String Symbol, int Price,
                    ExchangeCollectionDTO ExchangeCollection, int TransactionNum, int Turnover) {
        this.CompanyName = CompanyName;
        this.Symbol = Symbol;
        this.Price = Price;
        this.ExchangeCollection = ExchangeCollection;
        this.TransactionNum = TransactionNum;
        this.Turnover = Turnover;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getSymbol() {
        return Symbol;
    }

    public int getGate() {
        return Price;
    }

    public int getTransactionNum() {
        return TransactionNum;
    }

    public int getTransactionSum() {
        return Turnover;
    }

    public ExchangeCollectionDTO getExchangeCollection() {
        return ExchangeCollection;
    }


    @Override
    public String toString() {
        return "Stock:" + '\n' +
                "CompanyName = '" + CompanyName + '\'' + '\n' +
                "Symbol = '" + Symbol + '\'' + '\n' +
                "Price = " + Price + '\n' +
                "Transactions Number = " + TransactionNum + '\n' +
                "Transactions Turnover SUM = " + Turnover + '\n';
    }

    public String PrintAllCommands()
    {

        return this.toString()+ ExchangeCollection.toString();
    }

    public String PrintTransaction() {
        String str = "Transactions: " + '\n';
        //Print Transactions if there are any
        if (!ExchangeCollection.getTransaction().isEmpty()) {
            for (TransactionDTO tran : ExchangeCollection.getTransaction()) {
                str += '\t' + tran.toString() + '\n';
            }
        }
        else {
            str +='\t' + "There are no transactions " + '\n';
        }
        return str;
    }
}
