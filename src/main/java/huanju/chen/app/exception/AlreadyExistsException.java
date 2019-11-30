package huanju.chen.app.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AccountingException {


    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message, HttpStatus status) {
        super(message, status);
    }

    public AlreadyExistsException(String message, HttpStatus status, Object exceptionData) {
        super(message, status, exceptionData);
    }
}
