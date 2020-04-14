package huanju.chen.app.controller;

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
    public ApiResult<List> getBankAccountList(String startDate, String endDate,Integer page) {
        return ApiResult.success(accountBookService.getBankAccount(startDate,endDate,page));
    }

    @GetMapping("/accountBook/bank/count")
    public ApiResult<Integer> getBankAccountCount(String startDate, String endDate) {
        Integer count = accountBookService.getBankAccountCount(startDate, endDate);
        return ApiResult.success(count == null ? 0 : count+1);
    }


    @GetMapping("/accountBook/cash")
    public ApiResult<List> getCashAccountList(String startDate, String endDate,Integer page) {

        return ApiResult.success(accountBookService.getCashAccount(startDate,endDate,page));
    }

    @GetMapping("/accountBook/cash/count")
    public ApiResult<Integer> getCashAccountCount(String startDate, String endDate) {
        Integer count = accountBookService.getCashAccountCount(startDate, endDate);
        return ApiResult.success(count == null ? 0 : count+1);
    }


    @GetMapping("/accountBook/ledger")
    public ApiResult<List> getLedgerAccountList(Integer subjectId,String startDate, String endDate) {
        return ApiResult.success(accountBookService.getLedgerAccount(subjectId, startDate,endDate));
    }


    @GetMapping("/accountBook/sub")
    public ApiResult<List> getSubAccountList(Integer subjectId, String startDate, String endDate,Integer page) {
        return ApiResult.success(accountBookService.getSubAccount(subjectId, startDate, endDate,page));
    }

    @GetMapping("/accountBook/sub/count")
    public ApiResult<Integer> getSubAccountCount(Integer subjectId, String startDate, String endDate) {
        Integer count = accountBookService.getSubAccountCount(subjectId, startDate, endDate);
        return ApiResult.success(count == null ? 0 : count+1);
    }



}
