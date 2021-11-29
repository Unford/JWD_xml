package by.epam.xml.exception;

public class DepositXmlException extends Exception{
    public DepositXmlException() {
        super();
    }

    public DepositXmlException(String message) {
        super(message);
    }

    public DepositXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositXmlException(Throwable cause) {
        super(cause);
    }
}
