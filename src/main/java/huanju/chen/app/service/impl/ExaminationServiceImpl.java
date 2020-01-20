package huanju.chen.app.service.impl;

import huanju.chen.app.dao.ExaminationMapper;
import huanju.chen.app.domain.dto.Examination;
import huanju.chen.app.service.ExaminationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    @Resource
    private ExaminationMapper examinationMapper;


    @Override
    public int save(Examination examination) {
        return examinationMapper.save(examination);
    }

    @Override
    @Cacheable(cacheNames = "examinationCache",condition = "#id>0",unless = "#result==null")
    public Examination find(Integer id) {
        return examinationMapper.find(id);
    }
}
