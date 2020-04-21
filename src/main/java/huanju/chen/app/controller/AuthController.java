package huanju.chen.app.controller;

import huanju.chen.app.domain.vo.LoginParam;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@RestController
public class AuthController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public ApiResult<Map<String,Object>> login(@Validated @RequestBody LoginParam loginParam){
        Map<String,Object> map=userService.userLogin(loginParam);
        return ApiResult.success(map);
    }


    @GetMapping("/logout")
    public ApiResult<Object> logout(HttpServletRequest request,HttpServletResponse response){
        String tokenId=request.getHeader("token_id");
        if (tokenId!=null){
            userService.userLogout(tokenId);
        }
        return ApiResult.success(null);
    }

    @GetMapping("/test")
    public ApiResult<Object> test(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()){
            String headerName=headerNames.nextElement();
            String headerValue=request.getHeader(headerName);
            System.out.println(headerName+": "+headerValue);

        }
        return ApiResult.success(null);
    }

}
