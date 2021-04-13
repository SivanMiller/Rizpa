package exception;

public class NoSuchCmdTypeException extends Exception{
    private final String EXCEPTION_MESSAGE = "No such Command Type. Please try again";

    public NoSuchCmdTypeException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
