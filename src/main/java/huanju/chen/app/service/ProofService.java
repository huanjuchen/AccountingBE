package huanju.chen.app.service;

import huanju.chen.app.model.entity.Proof;


import java.util.List;

public interface ProofService {

    void save(Proof proof);

    Proof find(Integer id);



    List<Proof> listByUserId(Integer userId);

}
