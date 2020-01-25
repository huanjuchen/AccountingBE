package huanju.chen.app.controller;


import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.domain.vo.SubjectVo;
import huanju.chen.app.response.ApiResult;
import huanju.chen.app.service.SubjectService;
import huanju.chen.app.domain.EntityUtils;
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

    /**
     * 创建科目
     *
     * @param subject 科目实体
     */
    @PostMapping("/manage/subject")
    public ApiResult<SubjectVo> createSubject(@RequestBody @Validated Subject subject) {
        Subject temp = subjectService.save(subject);
        return ApiResult.success(temp.covert());
    }


    /**
     * 根据ID获得科目
     *
     * @param id 科目Id
     */

    @GetMapping("/subject/{id}")
    public ApiResult<SubjectVo> getSubjectById(@PathVariable int id) {
        Subject subject = subjectService.find(id);
        return ApiResult.success(subject.covert());
    }


    /**
     * 获取科目列表
     *
     * @param selectType 获取类型 all or (id,code,name)
     * @param searchWord 查找关键字 code or name
     * @param valid      科目状态 启用或禁用 true or false
     * @param desc       根据 code倒序 not_null or null
     * @param page       分页当前页 number or null
     * @param pageSize   分页每页显示的条目 number or null
     */

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


    /**
     * 获取科目总条数
     *
     * @param searchWord 查找关键字 code or name
     * @param valid      科目状态 启用或禁用 true or false
     */
    @GetMapping("/subject/count")
    public ApiResult<Integer> count(String searchWord,
                                    String valid) {

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

    /**
     * 更新科目
     *
     * @param subject 科目数据
     */

    @PutMapping("/manage/subject")
    public ApiResult update(@RequestBody @Validated Subject subject) {
        subjectService.update(subject);
        return ApiResult.success();
    }


    /**
     * 启用科目
     *
     * @param subjectId 科目ID
     */
    @PutMapping("/manage/subject/lock/{subjectId}")
    public ApiResult lock(@PathVariable Integer subjectId) {
        subjectService.lock(subjectId);
        return ApiResult.success();
    }


    /**
     * 启用科目
     *
     * @param subjectId 科目ID
     */

    @PutMapping("/manage/subject/unlock/{subjectId}")
    public ApiResult unlock(@PathVariable Integer subjectId) {
        subjectService.unlock(subjectId);
        return ApiResult.success();
    }


    /**
     * 删除科目
     */
    @DeleteMapping("/manage/subject/{subjectId}")
    public ApiResult delete(@PathVariable Integer subjectId) {
        subjectService.delete(subjectId);
        return ApiResult.success();
    }


}
