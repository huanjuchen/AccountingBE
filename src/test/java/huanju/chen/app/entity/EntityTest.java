package huanju.chen.app.entity;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.vo.SubjectVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityTest {


    @Test
    public void subjectTest(){
        SubjectVo subjectVo=new SubjectVo();
        subjectVo.setName("hhh");
        subjectVo.setRemark("flkdsjafls");

        System.out.println(JSON.toJSONString(subjectVo));


    }



}
