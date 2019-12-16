package huanju.chen.app.service;


import huanju.chen.app.model.entity.Subject;


import java.util.List;

public interface SubjectService {


    Subject save(Subject subject);

    List<Subject> listByPage(int page);


    int delete(Integer key);

    int update(Subject subject);

    Subject find(Integer id);


    List<Subject> listByEnabled();


}
