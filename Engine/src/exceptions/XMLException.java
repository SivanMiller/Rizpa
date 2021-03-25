package exceptions;

public class XMLException extends Exception{
    private final String EXCEPTION_MESSAGE = "Price is negative";

    public XMLException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
