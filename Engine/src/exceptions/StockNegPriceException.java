package exceptions;

public class StockNegPriceException extends Exception{

    private final String EXCEPTION_MESSAGE = "Price is negative";

    public StockNegPriceException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

}
