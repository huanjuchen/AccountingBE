package huanju.chen.app.controller;

import huanju.chen.app.domain.dto.BankAccount;
import huanju.chen.app.domain.dto.CashAccount;
import huanju.chen.app.domain.dto.LedgerAccount;
import huanju.chen.app.domain.dto.SubAccount;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.AccountBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public final class AccountBookController {

    private AccountBookService accountBookService;

    private static final Logger logger = LoggerFactory.getLogger(AccountBookController.class);

    @Resource
    public void setAccountBookService(AccountBookService accountBookService) {
        this.accountBookService = accountBookService;
    }

    @GetMapping("/accountBook/bank")
    public ApiResult<List> getBankAccountList(String startDate, String endDate) {
        return ApiResult.success(accountBookService.getBankAccount(startDate,endDate));
    }


    @GetMapping("/accountBook/cash")
    public ApiResult<List> getCashAccountList(String startDate, String endDate) {

        return ApiResult.success(accountBookService.getCashAccount(startDate,endDate));
    }


    @GetMapping("/accountBook/ledger")
    public ApiResult<List> getLedgerAccountList(Integer subjectId,Integer year) {
//        return ledgerAndSub(subjectId, startDate, endDate,LEDGER);
        return ApiResult.success(accountBookService.getLedgerAccount(subjectId, year));
    }


    @GetMapping("/accountBook/sub")
    public ApiResult<List> getSubAccountList(Integer subjectId, String startDate, String endDate) {
//        return ledgerAndSub(subjectId, startDate, endDate, SUB);
        return ApiResult.success(accountBookService.getSubAccount(subjectId, startDate, endDate));
    }

}
