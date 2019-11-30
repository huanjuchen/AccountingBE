package huanju.chen.app.controller;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.vo.LoginParam;
import huanju.chen.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<RespBody> login(@RequestBody LoginParam loginParam){
        return userService.userLogin(loginParam);
    }

    @GetMapping("/logout")
    public ResponseEntity<RespBody> logout(){
        return null;
    }

    @GetMapping("/test")
    public ResponseEntity<RespBody> test(){
        return ResponseEntity.ok(null);
    }

}
