package huanju.chen.app.controller;

import huanju.chen.app.model.RespResult;
import huanju.chen.app.model.vo.LoginParam;
import huanju.chen.app.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class AuthController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public RespResult login(@RequestBody LoginParam loginParam){
        Map<String,Object> map=userService.userLogin(loginParam);
        return RespResult.okAndBody(map);
    }

    @GetMapping("/logout")
    public RespResult logout(){
        return null;
    }

    @GetMapping("/test")
    public RespResult test(){
        return RespResult.ok();
    }

}
