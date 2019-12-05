package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.SubjectMapper;
import huanju.chen.app.exception.AlreadyExistsException;
import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Subject;
import huanju.chen.app.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {


    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Resource
    private SubjectMapper subjectMapper;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<RespBody> createSubject(Subject subject) {
        logger.debug(JSON.toJSONString(subject));

        if (subjectMapper.findByName(subject.getName()) != null) {
            throw new AlreadyExistsException("该科目以存在", HttpStatus.BAD_REQUEST);
        }


        save(subject);
        RespBody body = new RespBody();
        body.setCode(200);
        body.setData(find(subject.getId()));


        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<RespBody> getSubjectById(int id) {
        Subject subject = find(id);
        RespBody body = new RespBody();
        body.setCode(200);
        body.setData(subject);

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<RespBody> listByPage(int page) {
        if (page<1){
            page=1;
        }

        List<Subject> subjectList=listByLimit((page-1)*10,10);
        RespBody body=new RespBody();
        body.setCode(200);
        body.setData(subjectList);

        return ResponseEntity.ok(body);
    }

    @Override
    public int save(Subject subject) {
        return subjectMapper.save(subject);
    }

    @Override
    public int delete(int key) {
        return subjectMapper.delete(key);
    }

    @Override
    public int update(Subject subject) {
        return subjectMapper.update(subject);
    }

    @Override
    public Subject find(int key) {
        return subjectMapper.find(key);
    }

    @Override
    public List<Subject> listByLimit(int start, int count) {
        return subjectMapper.listByLimit(start,count);
    }
}
