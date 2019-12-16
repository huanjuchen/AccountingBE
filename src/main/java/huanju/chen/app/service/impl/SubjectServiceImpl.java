package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.SubjectMapper;
import huanju.chen.app.exception.AlreadyExistsException;
import huanju.chen.app.model.entity.Subject;
import huanju.chen.app.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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
    public Subject save(Subject subject) {
        logger.debug(JSON.toJSONString(subject));
        if (subjectMapper.find(subject.getId())!=null){
            throw new AlreadyExistsException("该科目编号已存在", HttpStatus.BAD_REQUEST);
        }
        if (subjectMapper.findByName(subject.getName()) != null) {
            throw new AlreadyExistsException("该科目名已存在", HttpStatus.BAD_REQUEST);
        }
        subject.setValid(true);

        subjectMapper.save(subject);
        return subjectMapper.find(subject);
    }



    @Override
    public List<Subject> listByPage(int page) {
        if (page<1){
            page=1;
        }

        return subjectMapper.listByLimit((page-1)*10,10);

    }


    @Override
    public int delete(Integer key) {
        return subjectMapper.delete(key);
    }

    @Override
    public int update(Subject subject) {
        return subjectMapper.update(subject);
    }

    @Override
    @Cacheable(cacheNames = "subjectCache",key = "#id",condition = "#id>0",unless = "#result==null")
    public Subject find(Integer id) {
        return subjectMapper.find(id);
    }



    @Override
    public List<Subject> listByEnabled() {
        return subjectMapper.listByEnabled();
    }
}
