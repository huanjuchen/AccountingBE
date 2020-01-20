package huanju.chen.app.controller;


import huanju.chen.app.domain.RespResult;
import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.UserVo;
import huanju.chen.app.exception.v2.BadRequestException;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.UserService;
import huanju.chen.app.domain.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;


    @PostMapping("/admin/user")
    public ApiResult<UserVo> createUser(@RequestBody @Validated User user) {
        User temp = userService.save(user);
        return ApiResult.success(temp.covert());
    }

    @GetMapping("/admin/user")
    public ApiResult<List> getUserList(@RequestParam(required = false, name = "page") String page,
                                       @RequestParam(required = false, name = "pageSize") String pageSize,
                                       @RequestParam(required = false, name = "selectWord") String selectWord,
                                       @RequestParam(required = false,name = "desc") String desc,
                                       @RequestParam(required = false, name = "valid") String valid) {
        logger.debug("page=" + page + "  pageSize=" + pageSize + "  selectWord=" + selectWord + "  valid=" + valid);
        Map<String, Object> map = new HashMap<>(8, 1);
        Integer pageInt = null;
        Integer pageSizeInt = null;

        if (selectWord != null && !"".equals(selectWord)) {
            try {
                Integer userid=Integer.valueOf(selectWord);
                User user=userService.find(userid);
                if (user!=null){
                    List<UserVo> userVos=new ArrayList<>();
                    userVos.add(user.covert());
                    return ApiResult.success(userVos);
                }
            }catch (NumberFormatException e){
                map.put("nameSw", selectWord);
            }
        }

        if (valid != null && !"".equals(valid)) {
            map.put("valid", Boolean.valueOf(valid));
        }

        if (desc!=null && desc.length()>0){
            map.put("desc","desc");
        }

        try {
            if (page != null && !"".equals(page)) {
                pageInt = Integer.valueOf(page);
            }
        } catch (NumberFormatException e) {
            logger.debug("page的值无法正常转换，已忽略");
        }

        try {
            if (pageSize != null && !"".equals(pageSize)) {
                pageSizeInt = Integer.valueOf(pageSize);
            }
        } catch (NumberFormatException e) {
            logger.debug("pageSize的值无法正常转换，已忽略");
        }


        if (pageInt != null && pageSizeInt != null) {
            if (pageInt < 1) {
                pageInt = 1;
            }
            if (pageSizeInt < 1) {
                pageSizeInt = 1;
            }
            map.put("offset", (pageInt - 1) * pageSizeInt);
            map.put("count", pageSizeInt);
        } else {
            map.put("offset", 0);
            map.put("count", 10);
        }

        List<User> userList = userService.getUserList(map);
        return ApiResult.success(EntityUtils.covertToUserVoList(userList));
    }


    @PutMapping("/admin/user/resetPwd")
    public ApiResult resetPassword(@RequestParam(required = true) Integer userId){


        userService.resetPwd(userId);




        return ApiResult.success();
    }


    @GetMapping("/admin/user/{userId}")
    public RespResult getUserById(@PathVariable String userId) {
        logger.debug("userId=" + userId);


        return RespResult.ok();
    }


}
