package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.dto.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SubjectMapperTest {

    @Resource
    SubjectMapper subjectMapper;


    @Autowired
    ApplicationContext context;


    @Test
    public void listTest(){
        Map<String,Object> map=new HashMap<>();
//        map.put("selectWord","库");
        map.put("category",1);
        map.put("valid",true);
        map.put("sortField","id");
        map.put("desc",true);
        map.put("offset",0);
        map.put("count",1000);
        List<Subject> subjectList=subjectMapper.list(map);

        context.getId();



        for (Subject subject:subjectList){
            System.out.println(JSON.toJSONString(subject));
        }



    }
}
