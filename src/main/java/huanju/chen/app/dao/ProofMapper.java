package huanju.chen.app.dao;

import huanju.chen.app.model.entity.Proof;

import java.util.List;

public interface ProofMapper extends BaseMapper<Proof> {

    List<Proof> listByUserId(Integer userId);
}
