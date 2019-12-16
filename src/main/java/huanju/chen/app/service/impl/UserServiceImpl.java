package huanju.chen.app.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.UserMapper;
import huanju.chen.app.exception.AlreadyExistsException;
import huanju.chen.app.exception.BadCreateException;
import huanju.chen.app.exception.NotFoundException;
import huanju.chen.app.model.entity.User;
import huanju.chen.app.model.vo.LoginParam;
import huanju.chen.app.security.utils.JwtUtils;
import huanju.chen.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private CacheManager cacheManager;



    @Override
    public Map<String,Object> userLogin(LoginParam loginParam) {
        logger.debug(JSON.toJSONString(loginParam));
        logger.info(loginParam.getUsername()+"正在尝试登陆");
        User user=userMapper.findUserByUsernameAndPassword(loginParam.getUsername(),SecureUtil.md5(loginParam.getPassword()));
        if (user==null){
            throw new NotFoundException("用户名或密码错误",HttpStatus.BAD_REQUEST);
        }

        Cache cache=cacheManager.getCache("tokenCache");
        if (cache==null){
            logger.error("无法获取缓存");
            throw new NotFoundException("系统错误，无法找到指定缓存容器",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String token= JwtUtils.buildJwt(user);
        cache.put(user.getId(),token);

        Map<String,Object> map=new HashMap<>(2,1);
        map.put("token",token);
        map.put("user",user.covert());


        logger.info(loginParam.getUsername()+"登陆成功");

        return map;
    }

    @Override
    public void userLogout() {

    }

    @Override
    public User save(User user) {
        logger.debug(JSON.toJSONString(user));
        if (user.getRole()==1){
            throw new BadCreateException("不允许添加用户为超级管理员", HttpStatus.BAD_REQUEST);
        }

        user.setValid(true);
        user.setJoinTime(new Timestamp(System.currentTimeMillis()));
        user.setPassword(SecureUtil.md5("12345678"));

        if(userMapper.findByName(user.getUsername())!=null){
            throw new AlreadyExistsException("用户已存在",HttpStatus.BAD_REQUEST);
        }
        userMapper.save(user);
        User temp=userMapper.find(user.getId());
        logger.info(user.getUsername()+"用户添加成功!");
        return temp;
    }

    @Override
    public List<User> getUserList(int page) {
        if (page<1){
            page=1;
        }

        return getList((page-1)*10,10);
    }



    @Override
    @Cacheable(cacheNames = "userCache",condition = "#id>0",unless = "#result==null")
    public User find(Integer id) {
        return userMapper.find(id);
    }


    @Override
    public User findUserByUsername(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public List<User> getList(int start,int length) {
        return userMapper.listByStart(start, length);
    }
}
