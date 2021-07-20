package exception;

public class NoSuchHolding extends Exception {

    private String userName;
    private String companyName;
    private String EXCEPTION_MESSAGE;

    public NoSuchHolding(String userName, String companyName) {
        this.userName = userName;
        this.companyName = companyName;
    }

    @Override
    public String getMessage() {
        EXCEPTION_MESSAGE = userName + " does not have any holding of " + companyName + " to sell. ";

        return EXCEPTION_MESSAGE;
    }
}
