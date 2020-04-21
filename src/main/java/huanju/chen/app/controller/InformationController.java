package huanju.chen.app.controller;

import huanju.chen.app.domain.EntityUtils;
import huanju.chen.app.domain.dto.Information;
import huanju.chen.app.domain.vo.InformationVO;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/3/6 18:22
 */
@RestController
public class InformationController {

    private InformationService informationService;

    @Autowired
    public void setInformationService(InformationService informationService) {
        this.informationService = informationService;
    }

    @PostMapping("/manage/information")
    public ApiResult<Object> add(@Validated @RequestBody Information information, HttpServletRequest request) {
        String tokenId = getTokenId(request);
        informationService.add(information, tokenId);
        return ApiResult.success(null);
    }

    @GetMapping("/information/{id}")
    public ApiResult<InformationVO> get(@PathVariable Integer id) {
        Information information = informationService.find(id);
        return information == null ? ApiResult.success(null) : ApiResult.success(information.covert());
    }

    @GetMapping("/information")
    public ApiResult<List<InformationVO>> list(Integer uid, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (uid != null) {
            map.put("userId", uid);
        }
        if (page != null && pageSize != null) {
            map.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
            map.put("count", pageSize > 1 ? pageSize : 1);
        } else {
            map.put("offset", 0);
            map.put("count", 10);
        }
        List<Information> information = informationService.list(map);
        return information == null ? ApiResult.success(null) : ApiResult.success(EntityUtils.covertToInformationVOList(information));
    }

    @GetMapping("/information/count")
    public ApiResult<Integer> count(Integer uid) {
        Map<String, Object> map = new HashMap<>();
        if (uid != null) {
            map.put("userId", uid);
        }
        Integer count = informationService.count(map);

        return count == null ? ApiResult.success(null) : ApiResult.success(count);
    }

    @DeleteMapping("/manage/information/{id}")
    public ApiResult<Object> delete(@PathVariable Integer id, HttpServletRequest request) {
        String tokenId = getTokenId(request);
        informationService.delete(id, tokenId);
        return ApiResult.success(null);
    }


    private String getTokenId(HttpServletRequest request) {
        return request.getHeader("token_id");
    }


}
