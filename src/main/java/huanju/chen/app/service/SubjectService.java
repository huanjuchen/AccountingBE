package huanju.chen.app.service;


import huanju.chen.app.domain.dto.Subject;


import java.util.List;
import java.util.Map;

public interface SubjectService {


    Subject save(Subject subject);

    int delete(Integer key);

    void update(Subject subject);

    Subject find(Integer id);

    List<Subject> list(Map<String, Object> map);

    Integer count(Map<String, Object> map);


}
