package huanju.chen.app.service;

import huanju.chen.app.domain.dto.BankAccount;
import huanju.chen.app.domain.dto.CashAccount;
import huanju.chen.app.domain.dto.LedgerAccount;
import huanju.chen.app.domain.dto.SubAccount;

import java.util.List;
import java.util.Map;

public interface AccountBookService {

    List<BankAccount> getBankAccountList(Map<String, Object> map);

    List<CashAccount> getCashAccountList(Map<String, Object> map);

    List<SubAccount> getSubAccountList(Map<String, Object> map);

    List<LedgerAccount> getLedgerAccountList(Map<String, Object> map);

    Integer getBankAccountCount(Map<String,Object> map);

    Integer getCashAccountCount(Map<String,Object> map);

    Integer getLedgerAccountCount(Map<String,Object> map);

    Integer getSubAccountCount(Map<String,Object> map);

}
