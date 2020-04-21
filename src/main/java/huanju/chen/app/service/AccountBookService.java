package huanju.chen.app.service;


import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.vo.BankAccountVO;
import huanju.chen.app.domain.vo.CashAccountVO;
import huanju.chen.app.domain.vo.LedgerAccountVO;
import huanju.chen.app.domain.vo.SubAccountVO;

import java.util.List;

public interface AccountBookService {

    List<BankAccountVO> getBankAccount(String startDate, String endDate, Integer page);

    List<CashAccountVO> getCashAccount(String startDate, String endDate,Integer page);

    List<SubAccountVO> getSubAccount(Integer subjectId, String startDate, String endDate,Integer page);

    List<LedgerAccountVO> getLedgerAccount(Integer subjectId, String startDate, String endDate);

    void accountBookHandle(Proof proof);

    void handleRollBack(Proof proof);

    Integer getBankAccountCount(String startDate, String endDate);

    Integer getCashAccountCount(String startDate, String endDate);

    Integer getLedgerAccountCount(Integer subjectId, String startDate, String endDate);

    Integer getSubAccountCount(Integer subjectId, String startDate, String endDate);

}
