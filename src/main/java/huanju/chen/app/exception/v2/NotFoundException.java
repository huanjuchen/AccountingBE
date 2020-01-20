package huanju.chen.app.exception.v2;

public class NotFoundException extends AccountingException {

    public NotFoundException() {
    }

    public NotFoundException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public NotFoundException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
