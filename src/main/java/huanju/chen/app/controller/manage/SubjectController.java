package huanju.chen.app.controller.manage;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Subject;
import huanju.chen.app.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SubjectController {

    private Logger logger= LoggerFactory.getLogger(SubjectController.class);

    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;

    @PostMapping("/manage/subject")
    public ResponseEntity<RespBody> createSubject(@RequestBody @Validated Subject subject){
        return subjectService.createSubject(subject);
    }


    @GetMapping("/manage/subject/{id}")
    public ResponseEntity<RespBody> getSubjectById(@PathVariable int id){
        return subjectService.getSubjectById(id);
    }


    @GetMapping({"/manage/subject","/subject"})
    public ResponseEntity<RespBody> listByPage(int page){
        return subjectService.listByPage(page);
    }



}
