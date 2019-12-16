package huanju.chen.app.controller;

import huanju.chen.app.model.RespResult;
import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.service.ProofService;
import huanju.chen.app.utils.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProofController {

    @Resource(name = "proofServiceImpl")
    private ProofService proofService;


    @PostMapping("/proof")
    public RespResult createProof(@RequestBody Proof proof) {
        proofService.save(proof);
        return RespResult.ok();
    }


    @GetMapping("/proof/{id}")
    public RespResult findProofById(@PathVariable int id) {
        Proof proof=proofService.find(id);



        return RespResult.okAndBody(proof.covert());
    }


    @GetMapping("/proof")
    public RespResult listByRecorder(Integer recorderId){
        List<Proof> proofs=proofService.listByUserId(recorderId);


        return RespResult.okAndBody(EntityUtils.covertToProofVoList(proofs));
    }

}
