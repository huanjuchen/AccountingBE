package huanju.chen.app.service;


import huanju.chen.app.domain.vo.BankAccountVO;
import huanju.chen.app.domain.vo.CashAccountVO;
import huanju.chen.app.domain.vo.LedgerAccountVO;
import huanju.chen.app.domain.vo.SubAccountVO;

import java.util.List;

public interface AccountBookService {

    List<BankAccountVO> getBankAccount(String startDate, String endDate);

    List<CashAccountVO> getCashAccount(String startDate, String endDate);

    List<SubAccountVO> getSubAccount(Integer subjectId, String startDate, String endDate);

    List<LedgerAccountVO> getLedgerAccount(Integer subjectId, Integer year);

}
