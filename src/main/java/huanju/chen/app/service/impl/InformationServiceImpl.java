package huanju.chen.app.service.impl;

import huanju.chen.app.dao.InformationMapper;
import huanju.chen.app.domain.dto.Information;
import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadDeleteException;
import huanju.chen.app.exception.v2.BadRequestException;
import huanju.chen.app.exception.v2.NotFoundException;
import huanju.chen.app.security.token.Token;
import huanju.chen.app.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author HuanJu
 * @date 2020/3/6 17:45
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class InformationServiceImpl implements InformationService {


    private InformationMapper informationMapper;

    private CacheManager cacheManager;

    @Resource
    public void setInformationMapper(InformationMapper informationMapper) {
        this.informationMapper = informationMapper;
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void add(Information information, String tokenId) {
        Integer userId = getUserId(tokenId);
        information.setUserId(userId);
        information.setTime(new Date());
        int rows = informationMapper.save(information);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }

    }

    @Override
    public Information find(Integer id) {
        return informationMapper.find(id);
    }

    @Override
    public List<Information> list(Map<String, Object> map) {
        return informationMapper.list(map);
    }

    @Override
    public Integer count(Map<String, Object> map) {
        return informationMapper.count(map);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void delete(Integer id, String tokenId) {
        Integer userId = getUserId(tokenId);
        Information information = informationMapper.find(id);
        if (information == null) {
            throw new NotFoundException(400, "未找到要删除的公告");
        }
        if (!Objects.equals(userId, information.getUserId())) {
            throw new BadDeleteException(400, "删除失败，只允许删除自己的公告");
        }

        int rows = informationMapper.delete(id);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }


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
