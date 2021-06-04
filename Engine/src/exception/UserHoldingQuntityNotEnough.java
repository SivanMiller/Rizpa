package exception;

public class UserHoldingQuntityNotEnough extends Exception{
    private String userName;
    private String companyName;
    private String EXCEPTION_MESSAGE;
    private int sellQuantity;

    public UserHoldingQuntityNotEnough(String userName, String companyName, int sellQuantity) {
        this.userName = userName;
        this.companyName = companyName;
        this.sellQuantity = sellQuantity;
    }

    @Override
    public String getMessage() {
        EXCEPTION_MESSAGE = userName + " does not have enough stocks of " + companyName + " to sell. ";
        if (sellQuantity > 0){
            EXCEPTION_MESSAGE += userName + " is already trying to sell " + sellQuantity + " stocks of this company.";
        }
        return EXCEPTION_MESSAGE;
    }
}
