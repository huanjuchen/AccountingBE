package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.Proof;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProofMapper extends BaseMapper<Proof> {

    List<Proof> listByUserId(@Param("userId") Integer userId, @Param("offset") int offset);

    int countByUserId(Integer userId);

    List<Proof> listByNotExamination(@Param("offset") int offset);
}
