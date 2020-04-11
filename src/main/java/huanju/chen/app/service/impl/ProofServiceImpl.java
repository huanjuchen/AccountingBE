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
import huanju.chen.app.utils.DateUtils;
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

    private static Map<Integer, String> MSG_MAP = new HashMap<>(1 >> 3);

    static {
        MSG_MAP.put(CLS, "贷方总账科目");
        MSG_MAP.put(DLS, "借方总账科目");
        MSG_MAP.put(CSS, "贷方明细账科目");
        MSG_MAP.put(DSS, "借方明细账科目");

    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void save(Proof proof, String tokenId) {
        Token token = getToken(tokenId);
        Integer userId = token.getUserId();
        proof.setRecorderId(userId).setVerify(0).setVerifyTime(null).setTrash(0);
        //调用DAO层保存到数据库
        int rows = proofMapper.save(proof);
        if (rows != 1) {
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }

        Subject clSubject = null;
        Subject dlSubject = null;
        Subject csSubject = null;
        Subject dsSubject = null;


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
        if (!Objects.equals(subject.getParent(), parent)) {
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
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
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
            verifyPass(proof);
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
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
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
            trashItem.setAbstraction(item.getAbstraction())
                    .setDebitSubSubjectId(item.getDebitSubSubjectId())
                    .setCreditSubSubjectId(item.getCreditSubSubjectId())
                    .setDebitLedgerSubjectId(item.getDebitLedgerSubjectId())
                    .setCreditLedgerSubjectId(item.getCreditLedgerSubjectId())
                    .setProofId(trashProof.getId())
                    .setCharge(false);
            trashItem.setMoney(item.getMoney().multiply(new BigDecimal(-1)));
            if (proofItemMapper.save(trashItem) != 1) {
                throw new BadCreateException(500, "服务器错误");
            }
        }
    }

    private final static char DEBIT = 'D';
    private final static char CREDIT = 'C';

    /**
     * 稽核通过
     */
    private void verifyPass(Proof proof) {
        List<ProofItem> items = proof.getItems();

        //借方总账科目
        Subject dls = null;
        //贷方总账科目
        Subject cls = null;

        for (ProofItem item : items) {
            dls = item.getDebitLedgerSubject();
            cls = item.getCreditLedgerSubject();
            /*
            填写日记账
             */
            //现金
            if (dls != null && Objects.equals(dls.getCode(), "1001")) {
                //借
                cashAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (cls != null &&
                    Objects.equals(cls.getCode(), "1001")) {
                //贷
                cashAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }
            //银行
            if (dls != null && Objects.equals(dls.getCode(), "1002")) {
                bankAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (cls != null && Objects.equals(cls.getCode(), "1002")) {
                bankAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }
            /*
            明细账
             */
            subAccountHandle(item, proof.getDate(), proof.getId());
            /*
            总账
             */
            ledgerAccountHandle(item, proof.getDate());
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

        if (item.getDebitSubSubject() != null) {
            SubAccount subAccount = new SubAccount();
            subAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitSubSubjectId())
                    .setDebitMoney(item.getMoney());
            int rows = subAccountMapper.save(subAccount);
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
            int rows = subAccountMapper.save(subAccount);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }

    }

    /**
     * 总账处理
     */
    private void ledgerAccountHandle(ProofItem item, Date date) {
        Date last = DateUtils.getMonthEnd(date);
        Subject dls = item.getDebitLedgerSubject();
        Subject cls = item.getCreditLedgerSubject();
        if (dls != null) {
            Integer dlsId = dls.getId();
            LedgerAccount la = ledgerAccountMapper.findBySubjectAndDate(dlsId, last);
            int row = 0;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本月合计")
                        .setDate(last)
                        .setSubjectId(dlsId)
                        .setDebitMoney(item.getMoney());
                row = ledgerAccountMapper.save(la);

            } else {
                BigDecimal money = la.getDebitMoney();
                LedgerAccount nla = new LedgerAccount();
                nla.setId(la.getId()).setDebitMoney(money.add(item.getMoney()));
                row = ledgerAccountMapper.update(nla);
            }
            if (row != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }

        if (cls != null) {
            Integer clsId = cls.getId();
            LedgerAccount la = ledgerAccountMapper.findBySubjectAndDate(clsId, last);
            int row = 0;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本月合计")
                        .setDate(last).setSubjectId(clsId).setCreditMoney(item.getMoney());
                row = ledgerAccountMapper.save(la);
            } else {
                BigDecimal money = la.getCreditMoney();
                if (money == null) {
                    money = new BigDecimal(0);
                }
                LedgerAccount nla = new LedgerAccount();
                nla.setId(la.getId()).setCreditMoney(money.add(item.getMoney()));
                row = ledgerAccountMapper.update(nla);
            }
            if (row != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
        }
    }
}
