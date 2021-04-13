package exception;

public class NoSuchCmdDirectionException extends Exception{
    private final String EXCEPTION_MESSAGE = "No such Command Direction. Please try again";

    public NoSuchCmdDirectionException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
