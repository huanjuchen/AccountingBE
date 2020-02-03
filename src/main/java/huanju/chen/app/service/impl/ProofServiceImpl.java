package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.ProofItemMapper;
import huanju.chen.app.dao.ProofMapper;
import huanju.chen.app.dao.SubjectMapper;
import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.dto.ProofItem;
import huanju.chen.app.domain.dto.Subject;

import huanju.chen.app.exception.BadCreateException;
import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.security.token.Token;
import huanju.chen.app.service.ProofService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProofServiceImpl implements ProofService {

    private static final Logger logger = LoggerFactory.getLogger(ProofServiceImpl.class);
    @Resource
    private ProofMapper proofMapper;
    @Resource
    private ProofItemMapper proofItemMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private CacheManager cacheManager;


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void save(Proof proof, String tokenId) {
        logger.debug("proof："+JSON.toJSONString(proof));
        logger.debug("token_id："+tokenId);
        //获取缓存中的数据并检查
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            logger.error("无法找到缓存容器...");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Token token=cache.get(tokenId,Token.class);
        if (token == null) {
            logger.error("缓存容器错误");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Integer userId = token.getUserId();
        proof.setRecorderId(userId);
        //调用DAO层保存到数据库
        int rows=proofMapper.save(proof);
        if (rows!=1){
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        for (ProofItem proofItem:proof.getItems()){
            proofItem.setProofId(proof.getId());
            if (proofItem.getCreditLedgerSubjectId() != null) {
                checkSubject(proofItem.getCreditLedgerSubjectId());
            }
            if (proofItem.getCreditSubSubjectId() != null) {
                checkSubject(proofItem.getCreditSubSubjectId());
            }
            if (proofItem.getDebitLedgerSubjectId() != null) {
                checkSubject(proofItem.getDebitLedgerSubjectId());
            }
            if (proofItem.getDebitSubSubjectId() != null) {
                checkSubject(proofItem.getDebitSubSubjectId());
            }
            //调用DAO层保存到数据库
            int resultRows=proofItemMapper.save(proofItem);
            if (resultRows!=1){
                throw new AccountingException(500, "系统出现了异常，请稍后重试");
            }
        }
    }

    /**
     *检查会计科目是否可用
     */
    private void checkSubject(Integer subjectId) {
        Subject subject = subjectMapper.find(subjectId);
        if (subject == null || subject.getValid().equals(false)) {
            throw new BadCreateException("未选择科目/科目不可用", HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public Proof find(Integer id) {
        return proofMapper.find(id);
    }

    @Override
    public List<Proof> list(Map<String, Object> map) {
        return proofMapper.list(map);
    }


}
