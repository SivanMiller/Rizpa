package exception;

public class NoSuchStockException extends Exception{
    private final String EXCEPTION_MESSAGE = "No such stock. Please try again";

    public NoSuchStockException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
