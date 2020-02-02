package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.dto.Proof;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
public class ProofMapperTest {

    @Resource
    ProofMapper proofMapper;

    @Test
    public void listByUserId(){

    }



    @Test
    public void saveTest(){

    }

    @Test
    public void findTest(){
        Proof proof=proofMapper.find(2);
        System.out.println(JSON.toJSONString(proof));
    }
}
