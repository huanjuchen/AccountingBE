package huanju.chen.app.dao;

import huanju.chen.app.model.entity.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SubjectMapperTest {

    @Resource
    SubjectMapper subjectMapper;

    @Test
    public void saveTest(){
        Subject subject=new Subject();

        subject.setId(1001);
        subject.setName("库存现金");
        subject.setSubjectType(1);
        subject.setValid(true);



    }
}
