package huanju.chen.app.model;

/**
 * @author HuanJu
 *
 *
 */
public class RespBody {

    private Integer code;

    private String message;

    private Object data;

    public RespBody() {
    }

    public RespBody(Integer code) {
        this.code = code;
    }

    public RespBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RespBody(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
