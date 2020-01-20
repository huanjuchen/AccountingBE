package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper extends BaseMapper<Subject> {

    List<Subject> listByLimit(@Param("start") int start, @Param("count") int count);

    List<Subject> listByEnabled();


}
