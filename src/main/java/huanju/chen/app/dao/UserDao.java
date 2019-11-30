package huanju.chen.app.dao;

import huanju.chen.app.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HuanJu
 */
//@Repository("userDao")
public interface UserDao extends BaseDao<User> {


    /**
     * 根据用户名和密码获取用户
     *
     * @param username 用户名
     * @param password 密码
     * @return user
     */
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);


}
