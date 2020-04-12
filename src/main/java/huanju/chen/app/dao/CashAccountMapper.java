package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.CashAccount;
import huanju.chen.app.domain.dto.SumMoney;

public interface CashAccountMapper extends BaseMapper<CashAccount> {
    SumMoney monthStartSumMoney(String date);
}
