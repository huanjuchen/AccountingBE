package huanju.chen.app.dao;

import huanju.chen.app.domain.dto.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SubjectMapper extends BaseMapper<Subject> {


    Subject findByCode(String code);



}
