package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.SubjectMapper;
import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.exception.v2.AlreadyExistsException;
import huanju.chen.app.exception.v2.BadCreateException;
import huanju.chen.app.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class SubjectServiceImpl implements SubjectService {


    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Resource
    private SubjectMapper subjectMapper;


    /**
     * 添加科目
     *
     * @param subject 科目实体
     * @return 科目
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public Subject save(Subject subject) {
        logger.debug(JSON.toJSONString(subject));

        try {
            Integer subjectCode=Integer.valueOf(subject.getCode());
        }catch (NumberFormatException e){
            throw new BadCreateException(400,"科目代码只能为纯数字");
        }

        if (subjectMapper.findByCode(subject.getCode()) != null) {
            throw new AlreadyExistsException(400, "科目代码已被使用");
        }

        if (subjectMapper.findByName(subject.getName()) != null) {
            throw new AlreadyExistsException(400, "科目名已存在");
        }

        subject.setValid(true);
        subjectMapper.save(subject);
        return subjectMapper.find(subject.getId());
    }


    @Override
    public List<Subject> listByPage(int page) {
        if (page < 1) {
            page = 1;
        }

        return subjectMapper.listByLimit((page - 1) * 10, 10);

    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int delete(Integer key) {
        return subjectMapper.delete(key);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Subject subject) {
        return subjectMapper.update(subject);
    }

    @Override
    public Subject find(Integer id) {
        return subjectMapper.find(id);
    }

    @Override
    public List<Subject> list(Map<String, Object> map) {
        logger.debug("==========");
        logger.debug("查询条件");
        logger.debug(JSON.toJSONString(map));
        for (String key : map.keySet()) {
            logger.debug("key:" + key + "  value:" + map.get(key));
        }
        logger.debug("==========");
        return subjectMapper.list(map);
    }


    @Override
    public List<Subject> listByEnabled() {
        return subjectMapper.listByEnabled();
    }
}
