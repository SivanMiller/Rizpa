package exception;

public class StockNegPriceException extends Exception{

    private String sSymbol;
    //private String EXCEPTION_MESSAGE = "Stock " + this.sSymbol + " Price is negative";


    public StockNegPriceException(String sSymbol) {
        this.sSymbol = sSymbol;
    }

    @Override
    public String getMessage() {
        return "Error. Stock " + this.sSymbol + " Price is negative";
    }

}
