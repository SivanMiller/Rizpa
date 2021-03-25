package exceptions;

public class XMLException extends Exception{

    private String strMessage;
    public XMLException(String strMessage) {
        strMessage = strMessage;
    }

    @Override
    public String getMessage() {
        return this.strMessage;
    }
}
