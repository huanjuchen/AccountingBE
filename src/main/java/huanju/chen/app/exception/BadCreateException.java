package huanju.chen.app.exception;

import org.springframework.http.HttpStatus;

public class BadCreateException extends AccountingException {

    public BadCreateException() {
    }

    public BadCreateException(String message, HttpStatus status) {
        super(message, status);
    }

    public BadCreateException(String message, HttpStatus status, Object exceptionData) {
        super(message, status, exceptionData);
    }
}
