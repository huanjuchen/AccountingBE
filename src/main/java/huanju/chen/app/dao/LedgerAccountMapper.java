package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.LedgerAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LedgerAccountMapper extends BaseMapper<LedgerAccount> {


    LedgerAccount findBySubjectAndDate(@Param("subjectId") Integer subjectId, @Param("date") Date date);
}
