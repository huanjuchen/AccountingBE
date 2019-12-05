package huanju.chen.app.service;

import huanju.chen.app.model.RespBody;
import huanju.chen.app.model.entity.Subject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {


    ResponseEntity<RespBody> createSubject(Subject subject);

    ResponseEntity<RespBody> getSubjectById(int id);

    ResponseEntity<RespBody> listByPage(int page);


    int save(Subject subject);

    int delete(int key);

    int update(Subject subject);

    Subject find(int key);

    List<Subject> listByLimit(int start, int count);


}
