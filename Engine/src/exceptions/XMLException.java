package exceptions;

public class XMLException extends Exception{

    private String strMessage;
    public XMLException(String strMessage) {
        this.strMessage = strMessage;
    }

    @Override
    public String getMessage() {
        return this.strMessage;
    }
}
