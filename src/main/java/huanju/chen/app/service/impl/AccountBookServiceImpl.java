package huanju.chen.app.service.impl;

import huanju.chen.app.dao.*;
import huanju.chen.app.domain.dto.*;
import huanju.chen.app.domain.vo.BankAccountVO;
import huanju.chen.app.domain.vo.CashAccountVO;
import huanju.chen.app.domain.vo.LedgerAccountVO;
import huanju.chen.app.domain.vo.SubAccountVO;
import huanju.chen.app.service.AccountBookService;
import huanju.chen.app.utils.AccountBookUtils;
import huanju.chen.app.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountBookServiceImpl implements AccountBookService {

    private BankAccountMapper bankAccountMapper;
    private CashAccountMapper cashAccountMapper;
    private LedgerAccountMapper ledgerAccountMapper;
    private SubAccountMapper subAccountMapper;
    private SubjectMapper subjectMapper;

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
    public List<BankAccountVO> getBankAccount(String startDate, String endDate) {
        if (startDate == null || startDate.length() == 0) {
            String[] se = DateUtils.monthStartEnd(new Date());
            startDate = se[0];
            endDate = se[1];
        }
        Map<String, Object> map = new HashMap<>(1 << 2);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<BankAccount> list = bankAccountMapper.list(map);
        SumMoney sumMoney = bankAccountMapper.monthStartSumMoney(startDate);
        BigDecimal debitMoney = null;
        BigDecimal creditMoney = null;
        /*
        账簿处理
         */
        List<BankAccountVO> bavS = new ArrayList<>();
        BankAccountVO bav = null;
        BigDecimal temp = null;
        //月初余额处理
        if (sumMoney != null) {
            debitMoney = sumMoney.getDebitMoney();
            creditMoney = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, null, debitMoney, creditMoney);
            bav = new BankAccountVO();
            bav.setAbstraction("月初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
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
    public List<CashAccountVO> getCashAccount(String startDate, String endDate) {
        if (startDate == null || startDate.length() == 0) {
            String[] se = DateUtils.monthStartEnd(new Date());
            startDate = se[0];
            endDate = se[1];
        }
        Map<String, Object> map = new HashMap<>(1 << 2);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<CashAccount> cashAccounts = cashAccountMapper.list(map);
        SumMoney sumMoney = cashAccountMapper.monthStartSumMoney(startDate);
        BigDecimal dm = null;
        BigDecimal cm = null;
        /*
        账簿处理
         */
        List<CashAccountVO> cas = new ArrayList<>();
        CashAccountVO cav = null;
        BigDecimal temp = null;
        //上期金额处理
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, null, dm, cm);
            cav = new CashAccountVO();
            cav.setAbstraction("月初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
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
    public List<SubAccountVO> getSubAccount(Integer subjectId, String startDate, String endDate) {
        Subject subject = subjectMapper.find(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        if (startDate == null || startDate.length() == 0) {
            String[] se = DateUtils.monthStartEnd(new Date());
            startDate = se[0];
            endDate = se[1];
        }
        Map<String, Object> map = new HashMap<>(1 << 2);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("subjectId", subjectId);
        List<SubAccount> list = subAccountMapper.list(map);
        SumMoney sumMoney = subAccountMapper.monthStartSumMoney(startDate,subjectId);
        BigDecimal dm = null;
        BigDecimal cm = null;
        /*
        账簿处理
         */
        List<SubAccountVO> sas = new ArrayList<>();
        SubAccountVO sav = null;
        BigDecimal temp = null;
        int mark = -3;
        //上期金额
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            sav = new SubAccountVO();
            mark = AccountBookUtils.getMark(category, dm, cm);
            sav.setAbstraction("月初余额").setMoney(temp).setDate(DateUtils.getDate(startDate)).setMark(mark);
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
    public List<LedgerAccountVO> getLedgerAccount(Integer subjectId, Integer year) {
        Subject subject = subjectMapper.find(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        String[] dates = DateUtils.yearStartEnd(year);
        String startDate = dates[0];
        String endDate = dates[1];
        Map<String, Object> map = new HashMap<>(1 << 3);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("subjectId", subjectId);

        List<LedgerAccount> list = ledgerAccountMapper.list(map);
        SumMoney sumMoney = ledgerAccountMapper.monthStartSumMoney(startDate,subjectId);
        BigDecimal dm = null;
        BigDecimal cm = null;

        List<LedgerAccountVO> las = new ArrayList<>();
        LedgerAccountVO lav = null;
        BigDecimal temp = null;
        int mark = -3;
        if (sumMoney != null) {
            dm = sumMoney.getDebitMoney();
            cm = sumMoney.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            lav = new LedgerAccountVO();
            mark = AccountBookUtils.getMark(category, dm, cm);
            lav.setAbstraction("年初余额").setMoney(temp).setDate(DateUtils.getDate(startDate)).setMark(mark);
            las.add(lav);
        }
        for (LedgerAccount item:list){
            lav=item.covert();
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, temp, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            lav.setMoney(temp).setMark(mark);
            las.add(lav);
        }
        return las;
    }
}
