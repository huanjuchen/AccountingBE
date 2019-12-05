package huanju.chen.app.service;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.User;
import huanju.chen.app.model.vo.LoginParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author HuanJu
 */
public interface UserService {

    ResponseEntity<RespBody> userLogin(LoginParam loginParam);

    ResponseEntity<RespBody> userLogout();

    ResponseEntity<RespBody> createUser(User user);

    ResponseEntity<RespBody> getUserList(int page);


    int save(User user);

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    List<User> getList(int start, int length);


}
