package huanju.chen.app.exception.v2;

import java.io.Serializable;

public class BadRequestException extends AccountingException implements Serializable {


    public BadRequestException() {
    }

    public BadRequestException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public BadRequestException(Integer errorCode, String message, String[] errorMsg) {
        super(errorCode, message, errorMsg);
    }
}
