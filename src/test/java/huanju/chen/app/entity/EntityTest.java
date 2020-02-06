package huanju.chen.app.entity;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.vo.SubjectVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityTest {


    @Test
    public void subjectTest(){
        SubjectVO subjectVo=new SubjectVO();
        subjectVo.setName("hhh");
        subjectVo.setRemark("flkdsjafls");

        System.out.println(JSON.toJSONString(subjectVo));


    }



}
