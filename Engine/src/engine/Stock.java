package engine;

import exceptions.StockNegPriceException;

import java.util.Objects;

public class Stock {

    private String sCompanyName;
    private String sSymbol;
    private int nPrice;
    private ExchangeCollection ecExchange;

    public Stock(){
        sCompanyName = "";
        sSymbol = "";
        ecExchange = new ExchangeCollection();
    }
//    //put in utilitis??
//    private static boolean isStringUpperCase(String str){
//
//        //convert String to char array
//        char[] charArray = str.toCharArray();
//
//        for(int i=0; i < charArray.length; i++){
//
//            //if any character is not in upper case, return false
//            if( !Character.isUpperCase( charArray[i] ))
//                return false;
//        }
//
//        return true;
//    }
    public Stock(String sCompanyName, String sSymbol, int nPrice) throws StockNegPriceException {
        if (nPrice < 0)
            throw new StockNegPriceException();
        this.sCompanyName = sCompanyName;
        this.sSymbol = sSymbol.toUpperCase();
        this.nPrice = nPrice;
        this.ecExchange = new ExchangeCollection();
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

    public ExchangeCollection getExchange() {
        return ecExchange;
    }

    public void setCompanyName(String sCompanyName) {
        this.sCompanyName = sCompanyName;
    }

    public void setSymbol(String sSymbol) {
        this.sSymbol = sSymbol;
    }

    public void setGate(int nGate) {
        this.nPrice = nGate;
    }

    public void setExchange(ExchangeCollection ecExchange) {
        this.ecExchange = ecExchange;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stock)) return false;
        Stock stock = (Stock) obj;
        return sCompanyName.equals(stock.sCompanyName) && sSymbol.equals(stock.sSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sCompanyName, sSymbol);
    }

    @Override
    public String toString() {
        return "Stock{" +
               "Symbol = '" + sSymbol + '\'' +
               ", CompanyName = '" + sCompanyName + '\'' +
               ", Price = " + nPrice +
               ", Exchange = " + ecExchange +
               '}';
    }
}
