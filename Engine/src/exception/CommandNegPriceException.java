package exception;

public class CommandNegPriceException extends Exception{

    private final String EXCEPTION_MESSAGE = "Command Price cannot be negative, please try again";


    public CommandNegPriceException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

}
