package huanju.chen.app.controller;

import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.UserVO;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuanJu
 */
@RestController
public class UserCenterController {

    private UserCenterService userCenterService;

    @Autowired
    public void setUserCenterService(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @GetMapping("/center/info")
    public ApiResult<UserVO> getUserInfo(HttpServletRequest request) {
        String tokenId = getTokenId(request);
        User user = userCenterService.getUser(tokenId);
        return user == null ? ApiResult.success(null) : ApiResult.success(user.covert());
    }

    @PutMapping("/center/changeName")
    public ApiResult<Object> changeName(HttpServletRequest request, String newName) {
        String tokenId = getTokenId(request);
        userCenterService.changeName(newName, tokenId);
        return ApiResult.success(null);
    }

    @PutMapping("/center/changePhone")
    public ApiResult<Object> changePhone(HttpServletRequest request, String newPhone) {
        String tokenId = getTokenId(request);
        userCenterService.changePhone(newPhone, tokenId);
        return ApiResult.success(null);
    }

    @PutMapping("/center/changePwd")
    public ApiResult<Object> changePwd(HttpServletRequest request, String newPwd) {
        String tokenId = getTokenId(request);
        userCenterService.changePassword(newPwd, tokenId);
        return ApiResult.success(null);
    }

    private String getTokenId(HttpServletRequest request) {
        return request.getHeader("token_id");
    }
}
