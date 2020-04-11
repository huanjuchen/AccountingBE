package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.BankAccount;
import huanju.chen.app.domain.dto.SumMoney;

public interface BankAccountMapper extends BaseMapper<BankAccount> {

    SumMoney monthStartSumMoney(String date);

}
