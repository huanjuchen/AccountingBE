package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.ProofItemMapper;
import huanju.chen.app.dao.SubjectMapper;
import huanju.chen.app.domain.dto.ProofItem;
import huanju.chen.app.domain.dto.Subject;
import huanju.chen.app.exception.v2.*;
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
import java.util.Objects;

@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class SubjectServiceImpl implements SubjectService {
    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private ProofItemMapper proofItemMapper;

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
            Integer subjectCode = Integer.valueOf(subject.getCode());
        } catch (NumberFormatException e) {
            throw new BadCreateException(400, "科目代码只能为纯数字");
        }
        if (subjectMapper.findByCode(subject.getCode()) != null) {
            throw new AlreadyExistsException(400, "科目代码已被使用");
        }
        if (subjectMapper.findByName(subject.getName()) != null) {
            throw new AlreadyExistsException(400, "科目名已存在");
        }
        subject.setValid(true);
        if (subject.getParentId() == null) {
            subject.setParentId(0);
        }
        subjectMapper.save(subject);
        return subjectMapper.find(subject.getId());
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Integer id) {
        //检查科目是否被使用
        checkEdit(id, 'd');

        int rows = subjectMapper.delete(id);
        if (rows != 1) {
            throw new BadDeleteException(500, "删除失败");
        }
    }


    /**
     * 检查科目是否被使用
     */
    private void checkEdit(Integer id, char type) {
        List<ProofItem> proofItems = proofItemMapper.listBySubject(id);
        if (proofItems != null && proofItems.size() > 0) {
            if (type == 'u') {
                throw new BadUpdateException(400, "修改失败,科目已被使用");
            } else if (type == 'd') {
                throw new BadDeleteException(400, "删除失败,科目已被使用");
            } else {
                throw new AccountingException(400, "处理失败,科目已被使用");
            }

        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Subject subject) {
        Subject temp = null;
        //查看该科目是否被使用
        checkEdit(subject.getId(), 'u');
        //查看修改的科目代码是否被占用
        temp = subjectMapper.findByCode(subject.getCode());
        if (temp != null && !temp.getId().equals(subject.getId())) {
            throw new BadUpdateException(400, "该科目代码已使用");
        }

        //查看修改的科目名是否已被使用
        temp = subjectMapper.findByName(subject.getName());
        if (temp != null && !temp.getId().equals(subject.getId())) {
            throw new BadUpdateException(400, "该科目名已被使用");
        }

        if (Objects.equals(subject.getId(), subject.getParentId())) {
            throw new BadUpdateException(400, "无法将自己设为上级科目");
        }

        int rows = subjectMapper.update(subject);

        if (rows != 1) {
            throw new BadUpdateException(500, "修改失败");
        }

    }

    @Override
    public Subject find(Integer id) {
        logger.debug("正在获取ID为 " + id + " 的科目");
        return subjectMapper.find(id);
    }

    @Override
    public List<Subject> list(Map<String, Object> map) {
        logger.debug("查找条件：\t" + JSON.toJSONString(map));
        return subjectMapper.list(map);
    }

    @Override
    public Integer count(Map<String, Object> map) {
        return subjectMapper.count(map);
    }

    @Override
    public void lock(Integer id) {
        logger.debug("禁用Id为 " + id + " 的科目");
        Subject subject = subjectMapper.find(id);
        if (subject == null) {
            throw new NotFoundException(400, "未找到指定科目");
        }
        subject = new Subject();
        subject.setId(id);
        subject.setValid(false);
        int rows = subjectMapper.update(subject);
        if (rows != 1) {
            throw new BadUpdateException(500, "禁用失败");
        }
    }

    @Override
    public void unlock(Integer id) {
        logger.debug("启用用Id为 " + id + " 的科目");
        Subject subject = subjectMapper.find(id);
        if (subject == null) {
            throw new NotFoundException(400, "未找到指定科目");
        }
        subject = new Subject();
        subject.setId(id);
        subject.setValid(true);
        int rows = subjectMapper.update(subject);
        if (rows != 1) {
            throw new BadUpdateException(500, "启用失败");
        }
    }
}
