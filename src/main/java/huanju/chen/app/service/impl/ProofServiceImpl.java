package huanju.chen.app.service.impl;

import huanju.chen.app.dao.*;
import huanju.chen.app.handle.HandleCenter;
import huanju.chen.app.handle.ProofSyncQueue;
import huanju.chen.app.domain.dto.*;

import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadCreateException;
import huanju.chen.app.exception.v2.BadUpdateException;
import huanju.chen.app.exception.v2.NotFoundException;
import huanju.chen.app.security.token.Token;
import huanju.chen.app.service.ProofService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProofServiceImpl implements ProofService {

    private static final Logger logger = LoggerFactory.getLogger(ProofServiceImpl.class);
    private ProofMapper proofMapper;

    private ProofItemMapper proofItemMapper;

    private SubjectMapper subjectMapper;

    private BankAccountMapper bankAccountMapper;

    private CashAccountMapper cashAccountMapper;

    private LedgerAccountMapper ledgerAccountMapper;

    private SubAccountMapper subAccountMapper;

    private CacheManager cacheManager;

    private HandleCenter handleCenter;


    private final ProofSyncQueue queue=ProofSyncQueue.getInstance();

    @Resource
    public void setHandleCenter(HandleCenter handleCenter) {
        this.handleCenter = handleCenter;
    }

    @Resource
    public void setProofMapper(ProofMapper proofMapper) {
        this.proofMapper = proofMapper;
    }

    @Resource
    public void setProofItemMapper(ProofItemMapper proofItemMapper) {
        this.proofItemMapper = proofItemMapper;
    }

    @Resource
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Resource
    public void setBankAccountMapper(BankAccountMapper bankAccountMapper) {
        this.bankAccountMapper = bankAccountMapper;
    }

    @Resource
    public void setCashAccountMapper(CashAccountMapper cashAccountMapper) {
        this.cashAccountMapper = cashAccountMapper;
    }

    @Resource
    public void setLedgerAccountMapper(LedgerAccountMapper ledgerAccountMapper) {
        this.ledgerAccountMapper = ledgerAccountMapper;
    }

    @Resource
    public void setSubAccountMapper(SubAccountMapper subAccountMapper) {
        this.subAccountMapper = subAccountMapper;
    }

    @Resource
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    private static final int CLS = 1;

    private static final int DLS = 2;

    private static final int CSS = 3;

    private static final int DSS = 4;

    private static final Map<Integer, String> MSG_MAP = new HashMap<>(1 >> 3);

    static {
        MSG_MAP.put(CLS, "贷方总账科目");
        MSG_MAP.put(DLS, "借方总账科目");
        MSG_MAP.put(CSS, "贷方明细账科目");
        MSG_MAP.put(DSS, "借方明细账科目");

    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void save(Proof proof, String tokenId) {
        Token token = getToken(tokenId);
        Integer userId = token.getUserId();
        proof.setRecorderId(userId).setVerify(0).setVerifyTime(null).setTrash(0);
        //调用DAO层保存到数据库
        int rows = proofMapper.save(proof);
        if (rows != 1) {
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }
        Subject clSubject;
        Subject dlSubject;
        Subject csSubject;
        Subject dsSubject;
        for (ProofItem proofItem : proof.getItems()) {
            proofItem.setProofId(proof.getId());
            clSubject = subjectMapper.find(proofItem.getCreditLedgerSubjectId());
            dlSubject = subjectMapper.find(proofItem.getDebitLedgerSubjectId());
            csSubject = subjectMapper.find(proofItem.getCreditSubSubjectId());
            dsSubject = subjectMapper.find(proofItem.getDebitSubSubjectId());
            /*
            检查贷方总账科目CLS
             */
            checkLedSubject(clSubject, CLS);
            /*
            检查借方总账科目DLS
             */
            checkLedSubject(dlSubject, DLS);
            /*
            检查贷方明细账科目CSS
             */
            checkSubSubject(csSubject,clSubject,CSS,CLS);
            /*
            检查借方明细账科目
             */
            checkSubSubject(dsSubject,dlSubject,DSS,DLS);
            //调用DAO层保存到数据库
            int resultRows = proofItemMapper.save(proofItem);
            if (resultRows != 1) {
                throw new AccountingException(500, "系统出现了异常，请稍后重试");
            }
        }
    }

    /**
     * 验证总账科目
     */
    private void checkLedSubject(Subject subject, int subjectType) {
        String msg = MSG_MAP.get(subjectType);
        if (subject == null) {
            throw new BadCreateException(400, msg + "输入有误");
        }

        if (Objects.equals(subject.getValid(), Boolean.FALSE)) {
            throw new BadCreateException(400, "科目" + subject.getName() + "已被禁用");
        }
    }

    /**
     * 验证明细账科目
     */
    private void checkSubSubject(Subject subject, Subject parent, int subjectType,int parentType) {
        checkLedSubject(subject, subjectType);
        String msg = MSG_MAP.get(subjectType);
        String parentMsg=MSG_MAP.get(parentType);
        if (subject.getParent() == null) {
            throw new BadCreateException(400, "科目" + subject.getName() + "不是明细账科目");
        }
        if (!Objects.equals(subject.getParent().getId(), parent.getId())) {
            throw new BadCreateException(400, msg + "不是"+parentMsg+"的明细账科目");
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
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void verify(Integer proofId, Boolean result, String tokenId) {
        Proof proof = proofMapper.find(proofId);
        if (proof == null) {
            throw new NotFoundException(400, "未找到该凭证");
        }
        logger.debug("token_id：" + tokenId);
        Token token = getToken(tokenId);
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
            queue.add(proof);
            handleCenter.wakeHandle();
        }
    }

    /**
     * 获取Token
     */
    private Token getToken(String tokenId) {
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
        return token;
    }

    /**
     * 冲账
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void trashProof(Integer proofId, String tokenId) {
        Token token = getToken(tokenId);
        Proof proof = proofMapper.find(proofId);
        if (proof.getTrash() != 0) {
            throw new BadUpdateException(400, "不能重复冲账");
        }
        Integer recorderId = proof.getRecorderId();
        if (!recorderId.equals(token.getUserId())) {
            throw new BadUpdateException(400, "只能对自己创建的凭证冲账");
        }
        Proof temp = new Proof();
        temp.setId(proof.getId());
        temp.setTrash(1);
        if (proofMapper.update(temp) != 1) {
            throw new BadCreateException(500, "服务器错误");
        }
        Proof trashProof = new Proof();
        trashProof.setDate(proof.getDate())
                .setInvoiceCount(proof.getInvoiceCount())
                .setManager(proof.getManager())
                .setCollection(proof.getCollection())
                .setRecorderId(proof.getRecorderId())
                .setCashier(proof.getCashier())
                .setPayer(proof.getPayer())
                .setVerifyUserId(null)
                .setVerifyTime(null)
                .setVerify(0)
                .setTrash(1);
        if (proofMapper.save(trashProof) != 1) {
            throw new BadCreateException(500, "服务器错误");
        }
        List<ProofItem> items = proof.getItems();
        for (ProofItem item : items) {
            ProofItem trashItem = new ProofItem();
            trashItem.setAbstraction(item.getAbstraction()+"错误冲账")
                    //反向
                    .setDebitSubSubjectId(item.getCreditSubSubjectId())
                    .setCreditSubSubjectId(item.getDebitSubSubjectId())
                    .setDebitLedgerSubjectId(item.getCreditLedgerSubjectId())
                    .setCreditLedgerSubjectId(item.getDebitLedgerSubjectId())
                    .setProofId(trashProof.getId())
                    .setCharge(false);
            trashItem.setMoney(item.getMoney());
            if (proofItemMapper.save(trashItem) != 1) {
                throw new BadCreateException(500, "服务器错误");
            }
        }
    }
}
