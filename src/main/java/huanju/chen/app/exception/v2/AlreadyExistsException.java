package huanju.chen.app.exception.v2;

public class AlreadyExistsException extends AccountingException {

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public AlreadyExistsException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
