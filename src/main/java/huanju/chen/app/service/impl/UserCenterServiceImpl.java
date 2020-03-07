package huanju.chen.app.service.impl;

import cn.hutool.crypto.SecureUtil;
import huanju.chen.app.dao.UserMapper;
import huanju.chen.app.domain.dto.User;
import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadRequestException;
import huanju.chen.app.exception.v2.BadUpdateException;
import huanju.chen.app.security.token.Token;
import huanju.chen.app.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author HuanJu
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class UserCenterServiceImpl implements UserCenterService {
    private UserMapper userMapper;

    private CacheManager cacheManager;

    @Resource
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUser(String tokenId) {
        Integer userId = getUserId(tokenId);
        return userMapper.find(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void changeName(String newName, String tokenId) {
        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setName(newName);
        int rows = userMapper.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
    }

    @Override
    public void changePhone(String newPhone, String tokenId) {
        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setPhone(newPhone);
        int rows = userMapper.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
    }

    @Override
    public void changePassword(String newPwd, String tokenId) {
        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setPassword(SecureUtil.md5(newPwd));
        int rows = userMapper.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            throw new AccountingException(500, "无法获取缓存");
        }
        cache.evict(tokenId);

    }


    private Integer getUserId(String tokenId) {
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            throw new AccountingException(500, "无法获取缓存");
        }
        Token token = cache.get(tokenId, Token.class);
        if (token == null) {
            throw new BadRequestException(400, "请求错误");
        }
        return token.getUserId();
    }
}
