package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.*;
import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.dto.ProofItem;
import huanju.chen.app.domain.dto.Subject;

import huanju.chen.app.exception.BadCreateException;
import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadUpdateException;
import huanju.chen.app.exception.v2.NotFoundException;
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
import java.util.Date;
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
    private BankAccountMapper bankAccountMapper;

    @Resource
    private CashAccountMapper cashAccountMapper;

    @Resource
    private LedgerAccountMapper ledgerAccountMapper;

    @Resource
    private SubAccountMapper subAccountMapper;

    @Resource
    private CacheManager cacheManager;


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void save(Proof proof, String tokenId) {
        logger.debug("proof：" + JSON.toJSONString(proof));
        logger.debug("token_id：" + tokenId);
        //获取缓存中的数据并检查
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            logger.error("无法找到缓存容器...");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Token token = cache.get(tokenId, Token.class);
        if (token == null) {
            logger.error("缓存容器错误");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Integer userId = token.getUserId();
        proof.setRecorderId(userId);
        //调用DAO层保存到数据库
        int rows = proofMapper.save(proof);
        if (rows != 1) {
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        for (ProofItem proofItem : proof.getItems()) {
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
            int resultRows = proofItemMapper.save(proofItem);
            if (resultRows != 1) {
                throw new AccountingException(500, "系统出现了异常，请稍后重试");
            }
        }
    }

    /**
     * 检查会计科目是否可用
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

    @Override
    public Integer count(Map<String, Object> map) {
        return proofMapper.count(map);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void verify(Integer proofId, Boolean result, String tokenId) {
        Proof proof = proofMapper.find(proofId);
        if (proof == null) {
            throw new NotFoundException(400, "未找到该凭证");
        }
        logger.debug("token_id：" + tokenId);
        //获取缓存中的数据并检查
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            logger.error("无法找到缓存容器...");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Token token = cache.get(tokenId, Token.class);
        if (token == null) {
            logger.error("缓存容器错误");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Integer userId = token.getUserId();
        if (result) {
            proof.setVerify(1);
        } else {
            proof.setVerify(-1);
        }
        proof.setVerifyUserId(userId);
        proof.setVerifyTime(new Date());
        int rows = proofMapper.update(proof);
        if (rows != 1) {
            throw new BadUpdateException(400, "更新失败");
        }
        if (result) {
            verifyPass(proof);
        } else {
            verifyFailed(proof);
        }

    }


    /**
     * 稽核通过
     */
    private void verifyPass(Proof proof) {
        List<ProofItem> items = proof.getItems();
        for (ProofItem item : items) {
            /*
            填写日记账
             */
            //现金
            if (item.getDebitLedgerSubject().getDaysKind() == 1) {

            }
            //银行
            if (item.getDebitLedgerSubject().getDaysKind() == 2) {

            }

            //明细账

            //总账
        }
    }

    /**
     * 现金日记账处理
     */
    private void cashAccountHandle(ProofItem item) {


    }

    /**
     * 银行日记账处理
     */
    private void bankAccountHandle() {

    }

    /**
     * 总账处理
     */
    private void ledgerAccountHandle() {

    }

    /**
     * 明细分类账处理
     */
    private void subAccountHandle() {

    }


    private void verifyFailed(Proof proof) {

    }


}
