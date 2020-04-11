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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountBookController {

    @Resource
    private AccountBookService accountBookService;

    private static final char BANK = 'b';
    private static final char CASH = 'c';

    private static final char LEDGER = 'l';
    private static final char SUB = 's';

    private static final Logger logger = LoggerFactory.getLogger(AccountBookController.class);


    @GetMapping("/accountBook/bank")
    public ApiResult<List> getBankAccountList(String startDate, String endDate) {
        return bankAndCash(startDate, endDate, BANK);
    }


    @GetMapping("/accountBook/cash")
    public ApiResult<List> getCashAccountList(String startDate, String endDate) {
        return bankAndCash(startDate, endDate, CASH);
    }


    @GetMapping("/accountBook/ledger")
    public ApiResult<List> getLedgerAccountList(Integer subjectId, String startDate, String endDate) {
        return ledgerAndSub(subjectId, startDate, endDate,LEDGER);
    }


    @GetMapping("/accountBook/sub")
    public ApiResult<List> getSubAccountList(Integer subjectId, String startDate, String endDate) {
        return ledgerAndSub(subjectId, startDate, endDate, SUB);
    }

    private ApiResult<List> bankAndCash(String startDate, String endDate, char t) {
        Map<String, Object> map = paramEncapsulation(startDate, endDate);
        //封装结束
        if (t == BANK) {
            List<BankAccount> list = accountBookService.getBankAccountList(map);
            if (list != null) {
                return ApiResult.success(BankAccount.listCovert(list));
            } else {
                return ApiResult.success(null);
            }
        } else {
            List<CashAccount> list = accountBookService.getCashAccountList(map);
            if (list != null) {
                return ApiResult.success(CashAccount.listCovert(list));
            } else {
                return ApiResult.success(null);
            }
        }
    }

    private ApiResult<List> ledgerAndSub(Integer subjectId, String startDate, String endDate, char t) {
        Map<String, Object> map = paramEncapsulation(startDate, endDate);
        if (subjectId != null) {
            map.put("subjectId", subjectId);
        }
        if (t == LEDGER) {
            List<LedgerAccount> list = accountBookService.getLedgerAccountList(map);
            if (list != null) {
                return ApiResult.success(LedgerAccount.listCovert(list));
            } else {
                return ApiResult.success(null);
            }
        } else {
            List<SubAccount> list = accountBookService.getSubAccountList(map);
            if (list != null) {
                return ApiResult.success(SubAccount.listCovert(list));
            } else {
                return ApiResult.success(null);
            }
        }


    }

    /**
     * 参数封装
     */
    private Map<String, Object> paramEncapsulation(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        //封装请求参数开始
        if (startDate != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                map.put("startDate", format.parse(startDate));
            } catch (ParseException e) {
                logger.info("日期转换失败,已忽略");
                logger.debug("调试日志" + e.getMessage());
            }
            if (endDate != null) {
                try {
                    map.put("endDate", format.parse(endDate));
                } catch (ParseException e) {
                    logger.info("日期转换失败,已忽略");
                    logger.debug("调试日志" + e.getMessage());
                }

            } else {
                map.put("endDate", new Date());
            }
        }
        return map;
    }


}
