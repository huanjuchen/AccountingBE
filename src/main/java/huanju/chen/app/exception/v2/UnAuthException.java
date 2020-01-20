package huanju.chen.app.exception.v2;

public class UnAuthException extends AccountingException {

    public UnAuthException() {
    }

    public UnAuthException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public UnAuthException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
