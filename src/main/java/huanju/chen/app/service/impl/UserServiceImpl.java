package huanju.chen.app.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.UserMapper;
import huanju.chen.app.exception.AlreadyExistsException;
import huanju.chen.app.exception.BadCreateException;
import huanju.chen.app.domain.dto.User;
import huanju.chen.app.domain.vo.LoginParam;
import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadRequestException;
import huanju.chen.app.exception.v2.NotFoundException;
import huanju.chen.app.security.token.Token;
import huanju.chen.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private CacheManager cacheManager;


    @Override
    public Map<String, Object> userLogin(LoginParam loginParam) {
        logger.debug(JSON.toJSONString(loginParam));
        logger.info(loginParam.getUsername() + "正在尝试登陆");
        //是否是用ID登录
        boolean idLogin;

        String username = loginParam.getUsername();
        String password = SecureUtil.md5(loginParam.getPassword());
        Integer userId = null;

        try {
            userId = Integer.valueOf(username);
            idLogin = true;
        } catch (NumberFormatException e) {
            idLogin = false;
        }
        User user = null;

        if (idLogin) {
            user = userMapper.find(userId);
        } else {
            user = userMapper.findByName(username);
        }
        if (user == null) {
            throw new BadRequestException(400, "用户名或用户编号正确");
        }

        //验证账户是否为启动状态
        if (!user.getValid()){
            throw new BadRequestException(400,"用户已被禁用，无法登陆");
        }

        //验证密码
        if (!password.equals(user.getPassword())) {
            throw new BadRequestException(400, "密码不正确");
        }

        Cache cache = cacheManager.getCache("tokenV2Cache");

        String tokenId = UUID.randomUUID().toString();
        tokenId = tokenId.replace("-", "");

        if (cache != null) {
            Token token = new Token(user.getId(), user.getRole());
            cache.put(tokenId, token);
        } else {
            logger.error("缓存容器获取失败");
            throw new AccountingException(500, "服务器出现异常...");
        }

        Map<String, Object> resultMap = new HashMap<>(2, 1);

        resultMap.put("tokenId", tokenId);

        resultMap.put("user", user.covert());

        return resultMap;
    }

    @Override
    public void userLogout(String tokenId) {
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache != null) {
            cache.evict("tokenId");
        } else {
            logger.error("缓存容器获取失败");
            throw new AccountingException(500, "服务器出现异常...");
        }


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class, readOnly = false)
    public User save(User user) {
        logger.debug(JSON.toJSONString(user));
        if (user.getRole() == 1) {
            throw new BadCreateException("不允许添加用户为超级管理员", HttpStatus.BAD_REQUEST);
        }

        user.setValid(true);
        user.setJoinTime(new Timestamp(System.currentTimeMillis()));
        user.setPassword(SecureUtil.md5("12345678"));

        if (userMapper.findByName(user.getUsername()) != null) {
            throw new AlreadyExistsException("用户已存在", HttpStatus.BAD_REQUEST);
        }
        userMapper.save(user);
        User temp = userMapper.find(user.getId());
        logger.info(user.getUsername() + "用户添加成功!");
        return temp;
    }

    @Override
    public List<User> getUserList(Map<String, Object> map) {
        return userMapper.list(map);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class, readOnly = false)
    public void resetPwd(Integer userId) {
        User user=userMapper.find(userId);
        if (user==null){
            throw new NotFoundException(400,"未找到指定用户");
        }
        user=new User();
        user.setId(userId);
        user.setPassword(SecureUtil.md5("12345678"));
        userMapper.update(user);
    }

    @Override
    public void lockUser(Integer userId) {
        logger.debug("正在禁用编号为 "+userId+" 用户");
        User user=userMapper.find(userId);
        if (user==null){
            throw new BadRequestException(400,"非法请求");
        }


        if ("root".equals(user.getUsername())){
            throw new BadRequestException(400,"非法请求，该用户无法被禁用");
        }


        user=new User();
        user.setId(userId);
        user.setValid(false);
        userMapper.update(user);
    }

    @Override
    public void unLockUser(Integer userId) {
        logger.debug("正在启用编号为 "+userId+" 的用户");
        User user=userMapper.find(userId);
        if (user==null){
            throw new BadRequestException(400,"非法请求");
        }
        if ("root".equals(user.getUsername())){
            throw new BadRequestException(400,"无效的请求");
        }

        user=new User();
        user.setId(userId);
        user.setValid(true);
        userMapper.update(user);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }


    @Override
    public User find(Integer id) {
        return userMapper.find(id);
    }



}
