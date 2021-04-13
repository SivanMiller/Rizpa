package exception;

public class StockSymbolLowercaseException extends Exception{

    private final String EXCEPTION_MESSAGE = "Stock symbol is in lowercase";

    public StockSymbolLowercaseException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }


}
