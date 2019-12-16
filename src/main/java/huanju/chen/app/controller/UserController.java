package huanju.chen.app.controller;


import huanju.chen.app.model.RespResult;
import huanju.chen.app.model.entity.User;
import huanju.chen.app.service.UserService;
import huanju.chen.app.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;


    @PostMapping("/admin/user")
    public RespResult createUser(@RequestBody @Validated User user) {
        User temp=userService.save(user);
        return RespResult.okAndBody(temp.covert());
    }

    @GetMapping("/admin/user")
    public RespResult getUserList(@RequestParam(required = false) int page) {
        logger.debug("page=" + page);
        List<User> userList=userService.getUserList(page);

        return RespResult.okAndBody(EntityUtils.covertToUserVoList(userList));
    }

    @GetMapping("/admin/user/userId")
    public RespResult getUserById(int userId){
        logger.debug("userId="+userId);
        return RespResult.ok();
    }


}
