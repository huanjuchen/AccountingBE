package huanju.chen.app.controller;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Proof;
import huanju.chen.app.service.ProofService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ProofController {

    @Resource(name = "proofServiceImpl")
    private ProofService proofService;


    @PostMapping("/proof")
    public ResponseEntity<RespBody> createProof(@RequestBody Proof proof) {
        return proofService.create(proof);
    }


    @GetMapping("/proof/{id}")
    public ResponseEntity<RespBody> findProofById(@PathVariable int id) {
        return proofService.findProofById(id);
    }

}
