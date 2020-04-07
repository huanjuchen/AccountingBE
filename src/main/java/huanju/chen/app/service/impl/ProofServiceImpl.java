package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        proof.setRecorderId(userId).setVerify(0).setVerifyTime(null).setTrash(0);
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
            throw new BadCreateException(400, "未选择科目/科目不可用");
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
        }

    }

    /**
     * 冲账
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void trashProof(Integer proofId, String tokenId) {
        Cache cache = cacheManager.getCache("tokenV2Cache");
        if (cache == null) {
            throw new AccountingException(500, "系统出现异常");
        }
        Token token = cache.get(tokenId, Token.class);
        if (token == null) {
            throw new AccountingException(500, "系统出现异常");
        }
        Proof proof = proofMapper.find(proofId);
        if (proof.getTrash() != 0) {
            throw new BadUpdateException(400, "不能重复冲账");
        }
        Integer recorderId = proof.getRecorderId();
        if (token.getRole() > 2 && !recorderId.equals(token.getUserId())) {
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
                .setVerifyUserId(proof.getVerifyUserId())
                .setVerifyTime(proof.getVerifyTime())
                .setVerify(proof.getVerify())
                .setTrash(1);
        if (proofMapper.save(trashProof) != 1) {
            throw new BadCreateException(500, "服务器错误");
        }
        for (ProofItem item : proof.getItems()) {
            ProofItem trashItem = new ProofItem();
            trashItem.setAbstraction(item.getAbstraction())
                    .setDebitSubSubjectId(item.getDebitSubSubjectId())
                    .setCreditSubSubjectId(item.getCreditSubSubjectId())
                    .setDebitLedgerSubjectId(item.getDebitLedgerSubjectId())
                    .setCreditLedgerSubjectId(item.getCreditLedgerSubjectId())
                    .setCharge(item.getCharge()).setProofId(trashProof.getId());
            trashItem.setMoney(item.getMoney().multiply(new BigDecimal(-1)));
            if (proofItemMapper.save(trashItem) != 1) {
                throw new BadCreateException(500, "服务器错误");
            }
        }
        verifyPass(proofMapper.find(trashProof.getId()));
    }

    private final static char DEBIT = 'D';
    private final static char CREDIT = 'C';

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
            if (item.getDebitLedgerSubject() != null && item.getDebitLedgerSubject().getDaysKind() == 1) {
                //借
                cashAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (item.getCreditLedgerSubject() != null && item.getCreditLedgerSubject().getDaysKind() == 1) {
                //贷
                cashAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }
            //银行
            if (item.getDebitLedgerSubject().getDaysKind() == 2) {
                bankAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (item.getCreditLedgerSubject() != null && item.getCreditLedgerSubject().getDaysKind() == 2) {
                bankAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }
            /*
            明细账
             */
            subAccountHandle(item, proof.getDate(), proof.getId());
            /*
            总账
             */
            ledgerAccountHandle(item, proof.getDate(), proof.getId());
            ProofItem itemNew = new ProofItem();
            itemNew.setId(item.getId());
            itemNew.setCharge(true);
            int rows = proofItemMapper.update(itemNew);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }
    }

    /**
     * 现金日记账处理
     */
    private void cashAccountHandle(ProofItem item, char t, Date date, Integer proofId) {
        CashAccount cashAccount = new CashAccount();
        if (t == DEBIT) {
            cashAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditLedgerSubjectId())
                    .setDebitMoney(item.getMoney());
        } else {
            cashAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitLedgerSubjectId())
                    .setCreditMoney(item.getMoney());
        }
        int rows = cashAccountMapper.save(cashAccount);
        if (rows != 1) {
            throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
        }
    }

    /**
     * 银行日记账处理
     */
    private void bankAccountHandle(ProofItem item, char t, Date date, Integer proofId) {
        BankAccount bankAccount = new BankAccount();
        if (t == DEBIT) {
            bankAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditLedgerSubjectId())
                    .setDebitMoney(item.getMoney());
        } else {
            bankAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitLedgerSubjectId())
                    .setCreditMoney(item.getMoney());
        }
        int rows = bankAccountMapper.save(bankAccount);
        if (rows != 1) {
            throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
        }
    }

    /**
     * 明细分类账处理
     */
    private void subAccountHandle(ProofItem item, Date date, Integer proofId) {
        int rows = 0;
        if (item.getDebitSubSubject() != null) {
            SubAccount subAccount = new SubAccount();
            subAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitSubSubjectId())
                    .setDebitMoney(item.getMoney());
            rows = subAccountMapper.save(subAccount);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }

        if (item.getCreditSubSubject() != null) {
            SubAccount subAccount = new SubAccount();
            subAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditSubSubjectId())
                    .setCreditMoney(item.getMoney());
            rows = subAccountMapper.save(subAccount);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }
    }

    /**
     * 总账处理
     */
    private void ledgerAccountHandle(ProofItem item, Date date, Integer proofId) {
        int rows = 0;
        if (item.getDebitLedgerSubject() != null) {
            LedgerAccount ledgerAccount = new LedgerAccount();
            ledgerAccount.setDate(date).setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitLedgerSubjectId())
                    .setDebitMoney(item.getMoney());
            rows = ledgerAccountMapper.save(ledgerAccount);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }
        if (item.getCreditLedgerSubject() != null) {
            LedgerAccount ledgerAccount = new LedgerAccount();
            ledgerAccount.setDate(date).setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditLedgerSubjectId())
                    .setCreditMoney(item.getMoney());
            rows = ledgerAccountMapper.save(ledgerAccount);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }
    }
}
