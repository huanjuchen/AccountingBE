package huanju.chen.app.controller;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.User;
import huanju.chen.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;


    @PostMapping("/admin/user")
    public ResponseEntity<RespBody> createUser(@RequestBody @Validated User user) {
        return userService.createUser(user);
    }

    @GetMapping("/admin/user")
    public ResponseEntity<RespBody> getUserList(@RequestParam(required = false) int page) {
        logger.debug("page=" + page);
        return userService.getUserList(page);
    }

    @GetMapping("/admin/user/userId")
    public ResponseEntity<RespBody> getUserById(int userId){
        logger.debug("userId="+userId);
        return null;
    }


}
