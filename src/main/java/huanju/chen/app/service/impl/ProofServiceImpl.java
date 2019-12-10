package huanju.chen.app.service.impl;

import huanju.chen.app.dao.ExaminationMapper;
import huanju.chen.app.dao.ProofItemMapper;
import huanju.chen.app.dao.ProofMapper;
import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.model.entity.ProofItem;
import huanju.chen.app.service.ProofService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProofServiceImpl implements ProofService {

    @Resource
    private ExaminationMapper examinationMapper;

    @Resource
    private ProofMapper proofMapper;

    @Resource
    private ProofItemMapper proofItemMapper;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<RespBody> create(Proof proof) {
        proofMapper.save(proof);

        if (proof.getItems()!=null&&proof.getItems().size()!=0){
            for (ProofItem item:proof.getItems()){
                item.setProofId(proof.getId());
                proofItemMapper.save(item);
            }
        }

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<RespBody> findProofById(int id) {
        Proof proof=proofMapper.find(id);
        if (proof!=null){
            List<ProofItem> itemList=proofItemMapper.listByProofId(proof.getId());
            proof.setItems(itemList);
        }

        RespBody body=new RespBody();
        body.setCode(200);
        body.setData(proof);

        return ResponseEntity.ok().body(body);
    }
}
