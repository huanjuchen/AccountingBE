package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;

import huanju.chen.app.dao.ProofMapper;

import huanju.chen.app.model.entity.Examination;
import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.model.entity.ProofItem;
import huanju.chen.app.model.entity.User;
import huanju.chen.app.service.ExaminationService;
import huanju.chen.app.service.ProofItemService;
import huanju.chen.app.service.ProofService;
import huanju.chen.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;

@Service
@DependsOn(value = {"userServiceImpl", "examinationServiceImpl", "proofItemServiceImpl"})
public class ProofServiceImpl implements ProofService {

    private static final Logger logger = LoggerFactory.getLogger(ProofServiceImpl.class);


    @Resource
    private ProofMapper proofMapper;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "examinationServiceImpl")
    private ExaminationService examinationService;

    @Resource(name = "proofItemServiceImpl")
    private ProofItemService proofItemService;

    @Autowired
    private CacheManager cacheManager;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(Proof proof) {
        logger.debug(JSON.toJSONString(proof));
        proofMapper.save(proof);
        if (proof.getItems() != null && proof.getItems().size() != 0) {
            for (ProofItem item : proof.getItems()) {
                item.setProofId(proof.getId());
                proofItemService.save(item);
            }
        }
    }


    @Override
    @Cacheable(value = "proofCache", key = "#id", condition = "#id>0", unless = "#result==null")
    public Proof find(Integer id) {
        Proof proof = proofMapper.find(id);
        if (proof != null) {
            List<ProofItem> itemList = proofItemService.listByProofId(proof.getId());
            proof.setItems(itemList);
            User recorder = userService.find(proof.getRecorderId());
            proof.setRecorder(recorder);
            if (proof.getExaminationId() != null) {
                Examination examination = examinationService.find(proof.getExaminationId());
                proof.setExamination(examination);
            }
        }
        return proof;
    }




    @Override
    @Cacheable(value = "proofListCache", key = "#userId", condition = "#userId>0", unless = "#result==null")
    public List<Proof> listByUserId(Integer userId) {
        logger.debug("userId" + userId);
        Cache cache = cacheManager.getCache("proofListCache");
        return proofMapper.listByUserId(userId);
    }
}
