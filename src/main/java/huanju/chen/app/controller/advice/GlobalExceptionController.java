package huanju.chen.app.controller.advice;

import huanju.chen.app.exception.AccountingException;
import huanju.chen.app.exception.CustomException;
import huanju.chen.app.model.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionController {

    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionController.class);


    @ExceptionHandler(AccountingException.class)
    public ResponseEntity<RespResult> accountingExceptionHandle(AccountingException e){
        e.printStackTrace();
        RespResult body = new RespResult(e.getStatus().value(), e.getMessage(), e.getExceptionData());
        return new ResponseEntity<>(body, e.getStatus());
    }


    /**
     * 处理自定义的异常
     *
     * @param e 自定义的异常
     * @return resp
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RespResult> blogExceptionHandle(CustomException e) {
        RespResult body = new RespResult(e.getStatus().value(), e.getMessage(), e.getExceptionData());
        return new ResponseEntity<>(body, e.getStatus());
    }


    /**
     * 处理HttpRequestMethodNotSupportedException
     *
     * @param e 方法不支持异常
     * @return resp
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RespResult> httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        RespResult body = new RespResult();
        body.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        body.setMessage("请求方法" + e.getMethod() + "不被允许");
        logger.warn(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespResult> httpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e){
        logger.warn(e.getMessage());
        RespResult body = new RespResult();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage("请求参数缺失--请求参数错误");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespResult> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e){
        logger.warn(e.getMessage());
        RespResult body = new RespResult();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage("请求数据验证失败");
//        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespResult> methodArgumentTypeMismatchExceptionHandle(MethodArgumentTypeMismatchException e){
        logger.warn(e.getMessage());
        RespResult body = new RespResult();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage("请求参数不匹配");
//        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(body);
    }


    /**
     * RuntimeException统一处理
     *
     * @param e runtimeException
     * @return resp
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RespResult> runtimeExceptionHandle(RuntimeException e) {
        e.printStackTrace();
        RespResult body = new RespResult();
        body.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setMessage("服务器错误，请稍后重试！");
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
