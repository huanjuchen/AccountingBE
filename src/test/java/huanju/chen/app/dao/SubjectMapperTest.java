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
        subject.setName("科目1");
        subject.setDescribe("科目测试1");

        subjectMapper.save(subject);
        System.out.println(subject.getId());
    }
}
