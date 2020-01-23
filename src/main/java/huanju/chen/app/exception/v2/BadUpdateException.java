package huanju.chen.app.exception.v2;

public class BadUpdateException extends AccountingException {

    public BadUpdateException() {
    }

    public BadUpdateException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BadUpdateException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
