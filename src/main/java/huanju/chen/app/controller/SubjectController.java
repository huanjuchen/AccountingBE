package huanju.chen.app.controller;


import huanju.chen.app.domain.RespResult;
import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.domain.vo.SubjectVo;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.SubjectService;
import huanju.chen.app.domain.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubjectController {

    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;

    @PostMapping("/manage/subject")
    public ApiResult<SubjectVo> createSubject(@RequestBody @Validated Subject subject) {
        Subject temp = subjectService.save(subject);
        return ApiResult.success(temp.covert());
    }


    @GetMapping("/subject/{id}")
    public ApiResult<SubjectVo> getSubjectById(@PathVariable int id) {
        Subject subject = subjectService.find(id);
        return ApiResult.success(subject.covert());
    }


    @GetMapping({"/subject"})
    public ApiResult<List> list(
            String selectType,
            String searchWord,
            String valid,
            String desc,
            Integer page,
            Integer pageSize
    ) {
        Map<String, Object> map = new HashMap<>();
        if (selectType != null && selectType.length() > 0) {
            map.put("selectType", selectType);
        }
        if (searchWord != null && searchWord.length() > 0) {
            try {
                Integer codeNum = Integer.valueOf(searchWord);
                map.put("codeSw", searchWord);
            } catch (NumberFormatException e) {
                map.put("nameSw", searchWord);
            }
        }
        if (valid != null && valid.length() > 0) {
            map.put("valid", Boolean.valueOf(valid));
        }

        if (desc != null && desc.length() > 0) {
            map.put("desc", new Object());
        }

        if (page != null && pageSize != null) {
            map.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
            map.put("count", pageSize > 1 ? pageSize : 1);
        }

        List<Subject> subjects = subjectService.list(map);
        return ApiResult.success(EntityUtils.covertToSubjectVoList(subjects));
    }


    @GetMapping("/subject/count")
    public ApiResult<Integer> count(String searchWord,
                                    String valid,
                                    String desc) {

        Map<String, Object> map = new HashMap<>();
        if (searchWord != null && searchWord.length() > 0) {
            try {
                Integer codeNum = Integer.valueOf(searchWord);
                map.put("codeSw", searchWord);
            } catch (NumberFormatException e) {
                map.put("nameSw", searchWord);
            }
        }
        if (valid != null && valid.length() > 0) {
            map.put("valid", Boolean.valueOf(valid));
        }
        Integer result = subjectService.count(map);
        if (result == null) {
            return ApiResult.success(0);
        } else {
            return ApiResult.success(result);
        }

    }


    @PutMapping("/manage/subject")
    public ApiResult update(@RequestBody @Validated Subject subject) {

        subjectService.update(subject);
        return ApiResult.success();
    }


}
