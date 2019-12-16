package huanju.chen.app.controller;


import huanju.chen.app.model.RespResult;
import huanju.chen.app.model.entity.Subject;
import huanju.chen.app.service.SubjectService;
import huanju.chen.app.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SubjectController {

    private Logger logger= LoggerFactory.getLogger(SubjectController.class);

    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;

    @PostMapping("/manage/subject")
    public RespResult createSubject(@RequestBody @Validated Subject subject){

        Subject temp= subjectService.save(subject);
        return RespResult.okAndBody(temp.covert());
    }


    @GetMapping("/manage/subject/{id}")
    public RespResult getSubjectById(@PathVariable int id){
        Subject subject=subjectService.find(id);

        return RespResult.okAndBody(subject);
    }


    @GetMapping({"/manage/subject","/subject"})
    public RespResult listByPage(int page){
        List<Subject> subjects=subjectService.listByPage(page);
        return RespResult.okAndBody(EntityUtils.covertToSubjectVoList(subjects));
    }


    @GetMapping("/subject/available")
    public RespResult listByAvailable(){
        List<Subject> subjectList=subjectService.listByEnabled();
        return RespResult.okAndBody(EntityUtils.covertToSubjectVoList(subjectList));
    }



}
