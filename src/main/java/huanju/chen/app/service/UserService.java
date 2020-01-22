package huanju.chen.app.service;


import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.LoginParam;


import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 */
public interface UserService {

    Map<String,Object> userLogin(LoginParam loginParam);

    void userLogout(String tokenId);

    User save(User user);

    List<User> getUserList(Map<String,Object> map);

    void resetPwd(Integer userId);

    void lockUser(Integer userId);

    void unLockUser(Integer userId);

    int count(Map<String,Object> map);



    User find(Integer id);






}
