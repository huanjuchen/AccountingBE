package huanju.chen.app.service.impl;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.dao.ExaminationMapper;
import huanju.chen.app.dao.ProofItemMapper;
import huanju.chen.app.dao.ProofMapper;
import huanju.chen.app.model.RespBody;
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
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@DependsOn(value = {"userServiceImpl", "examinationServiceImpl", "proofItemServiceImpl"})
public class ProofServiceImpl implements ProofService {

    private static final Logger logger= LoggerFactory.getLogger(ProofServiceImpl.class);


    @Resource
    private ProofMapper proofMapper;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "examinationServiceImpl")
    private ExaminationService examinationService;

    @Resource(name = "proofItemServiceImpl")
    private ProofItemService proofItemService;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<RespBody> create(Proof proof) {
        logger.debug(JSON.toJSONString(proof));
        proofMapper.save(proof);
        if (proof.getItems() != null && proof.getItems().size() != 0) {
            for (ProofItem item : proof.getItems()) {
                item.setProofId(proof.getId());
                proofItemService.save(item);
            }
        }

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<RespBody> findProofById(Integer id) {
        Proof proof = proofMapper.find(id);
        RespBody body = null;
        if (proof != null) {
            List<ProofItem> itemList = proofItemService.listByProofId(proof.getId());
            proof.setItems(itemList);
            User recorder = userService.find(proof.getRecorderId());
            proof.setRecorder(recorder);
            if (proof.getExaminationId() != null) {
                Examination examination = examinationService.find(proof.getExaminationId());
                proof.setExamination(examination);
            }
            body = new RespBody();
            body.setCode(200);
            body.setData(proof.covert());
        }

        return ResponseEntity.ok().body(body);
    }

    @Override
    public int save(Proof proof) {
        return proofMapper.save(proof);
    }

    @Override
    public Proof find(Integer id) {
        return proofMapper.find(id);
    }
}
