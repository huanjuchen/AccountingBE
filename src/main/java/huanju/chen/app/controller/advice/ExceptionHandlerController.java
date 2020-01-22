package huanju.chen.app.controller.advice;

import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.response.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);


    /**
     * 处理自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccountingException.class)
    public ApiResult accountingException(AccountingException e) {
        logger.info(e.getMessage());
        return ApiResult.error(e.getErrorCode(), e.getMessage(), e.getErrorMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult methodNotSupport(HttpRequestMethodNotSupportedException e) {
        logger.info(e.getMessage());

        return ApiResult.error(405, "该接口不支持 " + e.getMethod() + " 请求");
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult messageNotReadable(HttpMessageNotReadableException e) {
        logger.info(e.getMessage());

        return ApiResult.error(400, "请求参数缺失或错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult argumentNotValid(MethodArgumentNotValidException e) {
        logger.info(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            String[] errorMsg = new String[bindingResult.getErrorCount()];
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int i = 0; i < errors.size(); i++) {
                errorMsg[i]=errors.get(i).getDefaultMessage();
            }
            return ApiResult.error(400,"请求参数不合法",errorMsg);
        }

        return ApiResult.error(400, "请求参数验证不合法");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult argumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        logger.info(e.getMessage());
        return ApiResult.error(400, "请求参数不合法");
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResult runtime(RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return ApiResult.error(500, "服务器出错了");
    }

}
