package huanju.chen.app.service;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Proof;
import org.springframework.http.ResponseEntity;

public interface ProofService {

    ResponseEntity<RespBody> create(Proof proof);

    ResponseEntity<RespBody> findProofById(int id);

}
