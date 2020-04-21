package huanju.chen.app.listener;

import cn.hutool.crypto.SecureUtil;
import huanju.chen.app.dao.UserMapper;
import huanju.chen.app.domain.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author HuanJu
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    public static final Logger logger= LoggerFactory.getLogger(StartedListener.class);

    private UserMapper userMapper;

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        User rootUser=userMapper.findByName("root");
        if (rootUser==null){
            User user=new User();
            user.setUsername("root");
            user.setPassword(SecureUtil.md5("12345678"));
            user.setName("超级管理员");
            user.setRole(1);
            user.setJoinTime(new Timestamp(System.currentTimeMillis()));
            user.setValid(true);

            userMapper.save(user);
            logger.info("系统初次使用，建立超级管理员用户---");
            logger.info("用户名：root   密码：12345678");

        }

    }
}
