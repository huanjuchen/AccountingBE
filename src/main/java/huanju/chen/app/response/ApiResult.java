package huanju.chen.app.response;


/**
 * 统一返回格式
 *
 * @param <T> 数据类型
 */
public class ApiResult<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */

    private String msg;

    /**
     * 详细错误信息
     */
    private String[] errorMsg;

    /**
     * 数据
     */
    private T data;


    /**
     * 成功，不包含数据
     *
     * @return apiResult
     */
    public static ApiResult success() {
        return new ApiResult(200, "ok");
    }

    /**
     * 成功，包含数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "ok", data);
    }


    /**
     * 错误，不包含错误详细信息
     * @param code
     * @param msg
     * @return
     */
    public static ApiResult error(int code, String msg) {
        return new ApiResult(code, msg);
    }


    /**
     * 错误，包含错误信息详情
     *
     * @param code
     * @param msg
     * @param errorMsg
     * @return
     */
    public static ApiResult error(int code, String msg, String[] errorMsg) {
        ApiResult result = new ApiResult(code, msg);
        result.setErrorMsg(errorMsg);
        return result;
    }


    public ApiResult() {
    }


    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public ApiResult(int code, String msg, String[] errorMsg, T data) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String[] getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
