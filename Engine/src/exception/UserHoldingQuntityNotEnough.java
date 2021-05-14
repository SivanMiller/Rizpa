package exception;

public class UserHoldingQuntityNotEnough extends Exception{
    private final String EXCEPTION_MESSAGE = "User does not have enough stocks to sell";

    public UserHoldingQuntityNotEnough() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
