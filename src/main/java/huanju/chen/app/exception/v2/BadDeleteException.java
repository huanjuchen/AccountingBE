package huanju.chen.app.exception.v2;

public class BadDeleteException extends AccountingException {

    public BadDeleteException() {
    }

    public BadDeleteException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BadDeleteException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
