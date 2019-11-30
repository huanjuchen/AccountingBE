package huanju.chen.app.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AccountingException{

    public NotFoundException() {
    }

    public NotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotFoundException(String message, HttpStatus status, Object exceptionData) {
        super(message, status, exceptionData);
    }
}
