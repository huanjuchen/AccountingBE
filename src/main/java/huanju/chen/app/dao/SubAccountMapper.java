package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.SubAccount;
import huanju.chen.app.domain.dto.SumMoney;
import org.apache.ibatis.annotations.Param;

public interface SubAccountMapper extends BaseMapper<SubAccount> {
    SumMoney monthStartSumMoney(@Param("date") String date,@Param("subjectId") Integer subjectId);

}
