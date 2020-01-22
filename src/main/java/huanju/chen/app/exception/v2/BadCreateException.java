package huanju.chen.app.exception.v2;

public class BadCreateException extends AccountingException {
    public BadCreateException() {
    }

    public BadCreateException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BadCreateException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
