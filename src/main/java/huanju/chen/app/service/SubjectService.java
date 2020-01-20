package huanju.chen.app.service;


import huanju.chen.app.domain.dto.Subject;


import java.util.List;
import java.util.Map;

public interface SubjectService {


    Subject save(Subject subject);

    List<Subject> listByPage(int page);


    int delete(Integer key);

    int update(Subject subject);

    Subject find(Integer id);

    List<Subject> list(Map<String,Object> map);


    List<Subject> listByEnabled();


}
