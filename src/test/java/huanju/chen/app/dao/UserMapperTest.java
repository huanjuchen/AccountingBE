package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void listTest(){
        List<User> userList= userMapper.listByStart(0,5);

        for (User user:userList){
            System.out.println(JSON.toJSONString(user));
        }


    }

}
