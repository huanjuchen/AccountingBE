package huanju.chen.app.exception.v2;

import java.io.Serializable;

public class AccountingException extends RuntimeException implements Serializable {

    private Integer errorCode;

    private String message;

    private String[] errorMsg;

    public AccountingException() {
    }

    public AccountingException(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public AccountingException(Integer errorCode, String message, String[] errorMsg) {
        this.errorCode = errorCode;
        this.message = message;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
    }
}
