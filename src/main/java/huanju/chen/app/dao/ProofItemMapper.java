package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.ProofItem;

import java.util.List;

public interface ProofItemMapper extends BaseMapper<ProofItem> {

    List<ProofItem> items(Integer proofId);

    List<ProofItem> listBySubject(Integer subjectId);

}
