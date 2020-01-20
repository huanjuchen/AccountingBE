package huanju.chen.app.domain;

/**
 * @author HuanJu
 *
 *
 */
public class RespResult {

    private Integer code;

    private String message;

    private Object data;


    public static RespResult okAndBody(Object body){
        return new RespResult(200,"ok",body);
    }

    public static RespResult ok(){
        return new RespResult(200);
    }

    public RespResult() {
    }

    public RespResult(Integer code) {
        this.code = code;
    }

    public RespResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RespResult(Integer code, String message, Object data) {
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
