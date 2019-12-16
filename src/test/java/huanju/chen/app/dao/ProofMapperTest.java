package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.model.entity.Proof;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SpringBootTest
public class ProofMapperTest {

    @Resource
    ProofMapper proofMapper;

    @Test
    public void listByUserId(){
        List<Proof> proofs=proofMapper.listByUserId(1);
        for (Proof item:proofs){
            System.out.println(JSON.toJSONString(item));
        }
    }



    @Test
    public void saveTest(){

    }

    @Test
    public void findTest(){

    }
}
