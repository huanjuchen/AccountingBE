package huanju.chen.app.controller;


import huanju.chen.app.domain.RespResult;
import huanju.chen.app.domain.dto.Subject;
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

    private Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;

    @PostMapping("/manage/subject")
    public RespResult createSubject(@RequestBody @Validated Subject subject) {

        Subject temp = subjectService.save(subject);
        return RespResult.okAndBody(temp.covert());
    }


    @GetMapping("/manage/subject/{id}")
    public RespResult getSubjectById(@PathVariable int id) {
        Subject subject = subjectService.find(id);

        return RespResult.okAndBody(subject);
    }


    @GetMapping("/subject/available")
    public RespResult listByAvailable() {
        List<Subject> subjectList = subjectService.listByEnabled();
        return RespResult.okAndBody(EntityUtils.covertToSubjectVoList(subjectList));
    }


    /**
     * 查询科目
     *
     * @param selectWord 科目名
     * @param category   科目类型
     * @param daysKind   日记账类型
     * @param valid      是否可用
     * @param desc       倒序
     * @param offset     筛选开始位置
     * @param count      筛选数量
     * @return list
     */
    @GetMapping({"/manage/subject", "/subject"})
    public RespResult list(String selectWord,
                           String category,
                           String daysKind,
                           String valid,
                           String desc,
                           String offset,
                           String count) {
        Map<String, Object> map = new HashMap<>();
        if (selectWord != null && selectWord.length() > 0) {
            map.put("selectWord", selectWord);
        }

        if (category != null && category.length() > 0) {
            map.put("category", Integer.valueOf(category));
        }

        if (daysKind != null && daysKind.length() > 0) {
            map.put("daysKind", Integer.valueOf(daysKind));
        }



        if (valid != null && valid.length() > 0) {
            map.put("valid", Boolean.valueOf(valid));
        }

        if (desc!=null&&desc.length()>0){
            map.put("desc",Boolean.valueOf(desc));
        }

        if (offset != null && offset.length() > 0) {
            map.put("offset", Integer.valueOf(offset));
        }else {
            map.put("offset",0);
        }

        if (count != null && count.length() > 0) {
            map.put("count", Integer.valueOf(count));
        }else {
            map.put("count",1000);
        }

        List<Subject> subjects = subjectService.list(map);
        return RespResult.okAndBody(EntityUtils.covertToSubjectVoList(subjects));
    }


}
