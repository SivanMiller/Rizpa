package exceptions;

public class StockNegQuantityException extends Exception{

    private final String EXCEPTION_MESSAGE = "Quantity is negative";

    public StockNegQuantityException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

}
