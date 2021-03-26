package engine;

import exceptions.StockNegPriceException;
import objects.ExchangeDTO;
import objects.StockDTO;

import java.util.*;

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
    //TODO ADD EXCEPTION NEGATIVE QUANTITY
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

    public int getPrice() {
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

    public void setPrice(int nGate) {
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

    public StockDTO convertToDTO()
    {
        List<ExchangeDTO> setTransaction = new ArrayList<>();

        for (int i = 0; i < this.ecExchange.getTransactions().size(); i++)
        {
            setTransaction.add(this.ecExchange.getTransactions().get(i).convertToDTO());
        }
        return new StockDTO(this.getCompanyName(), this.getSymbol(), this.getPrice(),
                             this.ecExchange.convertToDTO(),
                             this.ecExchange.convertToDTO().getTransaction().size(), 0 ); // TODO: WHAT IS MAHZOR
    }
}
