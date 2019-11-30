package huanju.chen.app.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private HttpStatus  status;

    private Object exceptionData;

    public CustomException(String message, HttpStatus status, Object exceptionData) {
        super(message);
        this.status = status;
        this.exceptionData = exceptionData;
    }

    public CustomException() {
    }

    public CustomException(HttpStatus status, Object exceptionData) {
        this.status = status;
        this.exceptionData = exceptionData;
    }


    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
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
