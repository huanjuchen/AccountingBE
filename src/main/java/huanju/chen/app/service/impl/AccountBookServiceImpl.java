package huanju.chen.app.service.impl;

import huanju.chen.app.dao.*;
import huanju.chen.app.domain.dto.*;
import huanju.chen.app.domain.vo.BankAccountVO;
import huanju.chen.app.domain.vo.CashAccountVO;
import huanju.chen.app.domain.vo.LedgerAccountVO;
import huanju.chen.app.domain.vo.SubAccountVO;
import huanju.chen.app.handle.HandleCenter;
import huanju.chen.app.service.AccountBookService;
import huanju.chen.app.utils.AccountBookUtils;
import huanju.chen.app.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountBookServiceImpl implements AccountBookService {

    private BankAccountMapper bankAccountMapper;
    private CashAccountMapper cashAccountMapper;
    private LedgerAccountMapper ledgerAccountMapper;
    private SubAccountMapper subAccountMapper;
    private SubjectMapper subjectMapper;

    private ProofItemMapper proofItemMapper;

    private ProofMapper proofMapper;

    private Lock lock = new ReentrantLock();

    private HandleCenter handleCenter;

    @Resource
    public void setHandleCenter(HandleCenter handleCenter) {
        this.handleCenter = handleCenter;
    }


    @Resource
    public void setProofItemMapper(ProofItemMapper proofItemMapper) {
        this.proofItemMapper = proofItemMapper;
    }

    @Resource
    public void setProofMapper(ProofMapper proofMapper) {
        this.proofMapper = proofMapper;
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
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<BankAccountVO> getBankAccount(String startDate, String endDate, Integer page) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, page == null ? 0 : page);
        List<BankAccount> list = bankAccountMapper.list(map);
        SumMoney sumMoney = bankAccountMapper.monthStartSumMoney(startDate);
        BigDecimal debitMoney;
        BigDecimal creditMoney;
        /*
        账簿处理
         */
        List<BankAccountVO> bavS = new ArrayList<>();
        BankAccountVO bav;
        BigDecimal temp = null;
        //月初余额处理
        if (sumMoney != null) {
            debitMoney = sumMoney.getDebitMoney();
            creditMoney = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, null, debitMoney, creditMoney);
            bav = new BankAccountVO();
            bav.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
            bavS.add(bav);
        }
        //其他处理
        for (BankAccount item : list) {
            bav = item.covert();
            debitMoney = item.getDebitMoney();
            creditMoney = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, temp, debitMoney, creditMoney);
            bav.setMoney(temp);
            bavS.add(bav);
        }
        return bavS;
    }


    @Override
    public List<CashAccountVO> getCashAccount(String startDate, String endDate, Integer page) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, page == null ? 0 : page);

        List<CashAccount> cashAccounts = cashAccountMapper.list(map);
        SumMoney sumMoney = cashAccountMapper.monthStartSumMoney(startDate);
        BigDecimal dm;
        BigDecimal cm;
        /*
        账簿处理
         */
        List<CashAccountVO> cas = new ArrayList<>();
        CashAccountVO cav;
        BigDecimal temp = null;
        //上期金额处理
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, null, dm, cm);
            cav = new CashAccountVO();
            cav.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
            cas.add(cav);
        }
        //其他处理
        for (CashAccount item : cashAccounts) {
            cav = item.covert();
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, temp, dm, cm);
            cav.setMoney(temp);
            cas.add(cav);
        }
        return cas;
    }

    @Override
    public List<SubAccountVO> getSubAccount(Integer subjectId, String startDate, String endDate, Integer page) {
        Subject subject = subjectMapper.find(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, page == null ? 0 : page);
        List<SubAccount> list = subAccountMapper.list(map);

        SumMoney sumMoney = subAccountMapper.monthStartSumMoney(startDate, subjectId);
        BigDecimal dm;
        BigDecimal cm;
        /*
        账簿处理
         */
        List<SubAccountVO> sas = new ArrayList<>();
        SubAccountVO sav;
        BigDecimal temp = null;
        int mark;
        //上期金额
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            sav = new SubAccountVO();
            mark = AccountBookUtils.getMark(category, dm, cm);
            sav.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate)).setMark(mark);
            sas.add(sav);
        }

        for (SubAccount item : list) {
            sav = item.covert();
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, temp, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            sav.setMark(mark).setMoney(temp);
            sas.add(sav);
        }
        return sas;
    }


    @Override
    public List<LedgerAccountVO> getLedgerAccount(Integer subjectId, String startDate, String endDate) {
        Subject subject = subjectMapper.find(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        List<LedgerAccount> list = ledgerAccountMapper.list(map);
        SumMoney sumMoney = ledgerAccountMapper.monthStartSumMoney(startDate, subjectId);
        BigDecimal dm;
        BigDecimal cm;

        List<LedgerAccountVO> las = new ArrayList<>();
        LedgerAccountVO lav;
        BigDecimal temp = null;
        int mark;
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            lav = new LedgerAccountVO();
            mark = AccountBookUtils.getMark(category, dm, cm);
            lav.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate)).setMark(mark);
            las.add(lav);
        }
        for (LedgerAccount item : list) {
            lav = item.covert();
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, temp, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            lav.setMoney(temp).setMark(mark);
            las.add(lav);
        }
        return las;
    }

    private final static char DEBIT = 'D';
    private final static char CREDIT = 'C';

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void AccountBookHandle(Proof proof) {
        List<ProofItem> items = proof.getItems();

        //借方总账科目
        Subject dls;
        //贷方总账科目
        Subject cls;

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
            lock.lock();
            try {
                ledgerAccountHandle(item, proof.getDate());
            } finally {
                lock.unlock();
            }
            if (true) {
                throw new RuntimeException("模拟错误");
            }

            ProofItem itemNew = new ProofItem();
            itemNew.setId(item.getId());
            itemNew.setCharge(true);
            int rows = proofItemMapper.update(itemNew);
            if (rows != 1) {
                throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
            }
            handleCenter.wakeHandle();
        }
    }

    @Override
    public void HandleRollBack(Proof proof) {
        int rows = proofMapper.update(proof);
        if (rows != 1) {
            throw new huanju.chen.app.exception.v2.BadCreateException(500, "系统错误，处理失败");
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
        Subject dls = item.getDebitLedgerSubject();
        Subject cls = item.getCreditLedgerSubject();
        if (dls != null) {
            Integer dlsId = dls.getId();
            LedgerAccount la = ledgerAccountMapper.findBySubjectAndDate(dlsId, date);
            int row;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本日合计")
                        .setDate(date)
                        .setSubjectId(dlsId)
                        .setDebitMoney(item.getMoney());
                row = ledgerAccountMapper.save(la);
            } else {
                BigDecimal money = la.getDebitMoney();
                if (money == null) {
                    money = new BigDecimal("0.00");
                }
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
            LedgerAccount la = ledgerAccountMapper.findBySubjectAndDate(clsId, date);
            int row;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本日合计")
                        .setDate(date).setSubjectId(clsId).setCreditMoney(item.getMoney());
                row = ledgerAccountMapper.save(la);
            } else {
                BigDecimal money = la.getCreditMoney();
                if (money == null) {
                    money = new BigDecimal("0.00");
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


    @Override
    public Integer getBankAccountCount(String startDate, String endDate) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, null);
        return bankAccountMapper.count(map);
    }

    @Override
    public Integer getCashAccountCount(String startDate, String endDate) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, null);
        return cashAccountMapper.count(map);
    }

    @Override
    public Integer getLedgerAccountCount(Integer subjectId, String startDate, String endDate) {
        if (subjectId == null || subjectId == 0) {
            return 0;
        }
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        return ledgerAccountMapper.count(map);
    }

    @Override
    public Integer getSubAccountCount(Integer subjectId, String startDate, String endDate) {
        if (subjectId == null || subjectId == 0) {
            return 0;
        }
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        return subAccountMapper.count(map);
    }


    private Map<String, Object> paramHandle(Integer subjectId, String startDate, String endDate, Integer page) {
        Map<String, Object> map = new HashMap<>(1 << 3);
        if (startDate == null || startDate.length() == 0) {
            String[] se = DateUtils.monthStartEnd(new Date());
            startDate = se[0];
            endDate = se[1];
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        if (subjectId != null) {
            map.put("subjectId", subjectId);
        }
        if (page != null) {
            map.put("offset", page > 0 ? ((page - 1) * 10) : 0);
            map.put("count", 10);
        }
        return map;
    }

}
