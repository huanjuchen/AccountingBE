package huanju.chen.app.exception;

import org.springframework.http.HttpStatus;

public class AccountingException extends RuntimeException {

    private HttpStatus status;

    private Object exceptionData;

    public AccountingException() {
    }

    public AccountingException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AccountingException(String message, HttpStatus status, Object exceptionData) {
        super(message);
        this.status = status;
        this.exceptionData = exceptionData;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(Object exceptionData) {
        this.exceptionData = exceptionData;
    }
}
