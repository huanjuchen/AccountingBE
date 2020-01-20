package huanju.chen.app.service;

import huanju.chen.app.domain.dto.ProofItem;

import java.util.List;

public interface ProofItemService {

    int save(ProofItem proofItem);

    List<ProofItem> listByProofId(int proofId);

    ProofItem find(Integer itemId);


}
