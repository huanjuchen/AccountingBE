package huanju.chen.app.dao;


import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.dto.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class SubjectMapperTest {

    @Autowired
    public SubjectMapper mapper;


    @Test
    public void listTest(){
        Map<String,Object> map=new HashMap<>(8,1);
        map.put("selectType","all");
        map.put("codeSw",1);
        map.put("valid",true);
        map.put("offset",0);
        map.put("count",10);

        List<Subject> subjects=mapper.list(map);
        for (Subject subject:subjects){
            System.out.println(JSON.toJSONString(subject));
        }
    }



}
