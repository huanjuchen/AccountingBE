package huanju.chen.app.service;

import huanju.chen.app.domain.dto.Proof;


import java.util.List;

public interface ProofService {

    void save(Proof proof,String tokenId);

    Proof find(Integer id);


}
