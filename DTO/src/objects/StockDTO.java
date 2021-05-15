package objects;

//public class StockDTO {
//    private String CompanyName;
//    private String Symbol;
//    private int Price;
//    private ExchangeCollectionDTO ExchangeCollection;
//    private int TransactionNum; // Transactions Number
//    private int Turnover; // Transactions sum of turnovers
//
//    public StockDTO(String CompanyName, String Symbol, int Price,
//                    ExchangeCollectionDTO ExchangeCollection, int TransactionNum, int Turnover) {
//        this.CompanyName = CompanyName;
//        this.Symbol = Symbol;
//        this.Price = Price;
//        this.ExchangeCollection = ExchangeCollection;
//        this.TransactionNum = TransactionNum;
//        this.Turnover = Turnover;
//    }
//
//    public String getCompanyName() {
//        return CompanyName;
//    }
//
//    public String getSymbol() {
//        return Symbol;
//    }
//
//    public int getGate() {
//        return Price;
//    }
//
//    public int getTransactionNum() {
//        return TransactionNum;
//    }
//
//    public int getTransactionSum() {
//        return Turnover;
//    }
//
//    public ExchangeCollectionDTO getExchangeCollection() {
//        return ExchangeCollection;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Stock:" + '\n' +
//                "CompanyName = '" + CompanyName + '\'' + '\n' +
//                "Symbol = '" + Symbol + '\'' + '\n' +
//                "Price = " + Price + '\n' +
//                "Transactions Number = " + TransactionNum + '\n' +
//                "Transactions Turnover SUM = " + Turnover + '\n';
//    }
//
//    public String PrintAllCommands()
//    {
//
//        return this.toString()+ ExchangeCollection.toString();
//    }
//
//    public String PrintTransaction() {
//        String str = "Transactions: " + '\n';
//        //Print Transactions if there are any
//        if (!ExchangeCollection.getTransaction().isEmpty()) {
//            for (TransactionDTO tran : ExchangeCollection.getTransaction()) {
//                str += '\t' + tran.toString() + '\n';
//            }
//        }
//        else {
//            str +='\t' + "There are no transactions " + '\n';
//        }
//        return str;
//    }
//}

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public class StockDTO {
    public final SimpleStringProperty companyName;
    public final SimpleStringProperty stockSymbol;
    public final SimpleIntegerProperty stockQuantity;
    public final SimpleIntegerProperty stockPrice;

    public StockDTO(String companyName, String stockSymbol,int stockQuantity, int stockPrice) {
        this.companyName = new SimpleStringProperty(companyName);
        this.stockSymbol = new SimpleStringProperty(stockSymbol);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.stockPrice = new SimpleIntegerProperty(stockPrice);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty getCompanyNameProperty() {
        return companyName;
    }

    public String getStockSymbol() {
        return stockSymbol.get();
    }

    public SimpleStringProperty getStockSymbolProperty() {
        return stockSymbol;
    }

    public int getStockQuantity() {
        return stockQuantity.get();
    }

    public SimpleIntegerProperty getStockQuantityProperty() {
        return stockQuantity;
    }

    public int getStockPrice() {
        return stockPrice.get();
    }

    public SimpleIntegerProperty getStockPriceProperty() {
        return stockPrice;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol.set(stockSymbol);
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    public void setStockPrice(int stockPrice) {
        this.stockPrice.set(stockPrice);
    }
}