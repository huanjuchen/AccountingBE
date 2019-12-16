package huanju.chen.app.service;


import huanju.chen.app.model.entity.User;
import huanju.chen.app.model.vo.LoginParam;


import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 */
public interface UserService {

    Map<String,Object> userLogin(LoginParam loginParam);

    void userLogout();

    User save(User user);

    List<User> getUserList(int page);



    User find(Integer id);


    User findUserByUsername(String username);

    List<User> getList(int start, int length);


}
