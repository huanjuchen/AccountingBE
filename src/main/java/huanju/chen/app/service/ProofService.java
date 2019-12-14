package huanju.chen.app.service;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Proof;
import org.springframework.http.ResponseEntity;

public interface ProofService {

    ResponseEntity<RespBody> create(Proof proof);

    ResponseEntity<RespBody> findProofById(Integer id);


    int save(Proof proof);

    Proof find(Integer id);

}
