package huanju.chen.app.controller;

import huanju.chen.app.domain.EntityUtils;
import huanju.chen.app.domain.RespResult;
import huanju.chen.app.domain.dto.Proof;
import huanju.chen.app.domain.vo.ProofVO;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.ProofService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ProofController {

    @Resource(name = "proofServiceImpl")
    private ProofService proofService;

    @PostMapping("/proof")
    public ApiResult createProof(@Validated @RequestBody Proof proof, HttpServletRequest request) {
        String tokenId = request.getHeader("token_id");
        proofService.save(proof, tokenId);
        return ApiResult.success();
    }


    private Object object = new Object();


    private static String ID_DESC = "idDESC";
    private static String ID_ASC = "idASC";
    private static String DATE_DESC = "dateDESC";
    private static String DATE_ASC = "dateASC";
    private static String RID_DESC = "ridDESC";
    private static String RID_ASC = "ridASC";


    /**
     * 查询会计凭证
     *
     * @param rid       记账人用户编号
     * @param startDate 筛选开始日期
     * @param endDate   筛选结束日期
     * @param verify    审核类型
     * @param orderType 排序类型
     * @param page      页数
     * @param pageSize  每页数量
     */
    @GetMapping("/proof")
    public ApiResult<List<ProofVO>> list(Integer rid, Date startDate, Date endDate, Integer verify, String orderType, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        //包装查询条件
        if (rid != null) {
            map.put("rid", rid);
        }
        if (startDate != null) {
            map.put("startDate", startDate);
            if (endDate != null) {
                map.put("endDate", endDate);
            } else {
                map.put("endDate", new Date());
            }
        }
        if (verify != null) {
            if (verify == 0 || verify == 1 || verify == -1) {
                map.put("verify", verify);
            }
        }
        if (orderType != null) {
            if (ID_ASC.equals(orderType)) {
                map.put(ID_ASC, object);
            } else if (ID_DESC.equals(orderType)) {
                map.put(ID_DESC, object);
            } else if (DATE_ASC.equals(orderType)) {
                map.put(DATE_ASC, object);
            } else if (DATE_DESC.equals(orderType)) {
                map.put(DATE_DESC, object);
            } else if (RID_ASC.equals(orderType)) {
                map.put(RID_ASC, object);
            } else if (RID_DESC.equals(orderType)) {
                map.put(RID_DESC, object);
            }

        }
        if (page != null && pageSize != null) {
            map.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
            map.put("count", pageSize > 1 ? pageSize : 1);
        } else {
            map.put("offset", 0);
            map.put("count", 10);
        }

        //调用Service层获取数据
        List<Proof> proofs = proofService.list(map);
        if (proofs != null) {
            return ApiResult.success(EntityUtils.covertToProofVoList(proofs));
        } else {
            return ApiResult.success(null);
        }

    }


    @GetMapping("/proof/{id}")
    public ApiResult<ProofVO> find(@PathVariable Integer id) {
        Proof proof = proofService.find(id);
        if (proof != null) {
            return ApiResult.success(proof.covert());
        } else {
            return ApiResult.success(null);
        }
    }


}
