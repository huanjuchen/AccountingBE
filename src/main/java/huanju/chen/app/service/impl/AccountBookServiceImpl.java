package huanju.chen.app.service.impl;

import huanju.chen.app.dao.BankAccountMapper;
import huanju.chen.app.dao.CashAccountMapper;
import huanju.chen.app.dao.LedgerAccountMapper;
import huanju.chen.app.dao.SubAccountMapper;
import huanju.chen.app.domain.dto.BankAccount;
import huanju.chen.app.domain.dto.CashAccount;
import huanju.chen.app.domain.dto.LedgerAccount;
import huanju.chen.app.domain.dto.SubAccount;
import huanju.chen.app.service.AccountBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountBookServiceImpl implements AccountBookService {

    @Resource
    private BankAccountMapper bankAccountMapper;

    @Resource
    private CashAccountMapper cashAccountMapper;

    @Resource
    private LedgerAccountMapper ledgerAccountMapper;

    @Resource
    private SubAccountMapper subAccountMapper;

    @Override
    public List<BankAccount> getBankAccountList(Map<String, Object> map) {
        return bankAccountMapper.list(map);
    }

    @Override
    public List<CashAccount> getCashAccountList(Map<String, Object> map) {
        return cashAccountMapper.list(map);
    }

    @Override
    public List<SubAccount> getSubAccountList(Map<String, Object> map) {
        return subAccountMapper.list(map);
    }

    @Override
    public List<LedgerAccount> getLedgerAccountList(Map<String, Object> map) {
        return ledgerAccountMapper.list(map);
    }

}
