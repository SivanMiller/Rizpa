package engine;

import java.util.Objects;

public class Stock {

    private String sCompanyName;
    private String sSymbol;
    private int nGate;
    private ExchangeCollection ecExchange;

    public Stock(){
        sCompanyName = "";
        sSymbol = "";
        ecExchange = new ExchangeCollection();
    }

    public Stock(String sCompanyName, String sSymbol, int nGate, ExchangeCollection ecExchange) {
        this.sCompanyName = sCompanyName;
        this.sSymbol = sSymbol;
        this.nGate = nGate;
        this.ecExchange = ecExchange;
    }

    public String getCompanyName() {
        return sCompanyName;
    }

    public String getSymbol() {
        return sSymbol;
    }

    public int getGate() {
        return nGate;
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
        this.nGate = nGate;
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
               ", Gate = " + nGate +
               ", Exchange = " + ecExchange +
               '}';
    }
}
