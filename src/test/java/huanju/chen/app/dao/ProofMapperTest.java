package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.model.entity.Proof;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


@SpringBootTest
public class ProofMapperTest {

    @Resource
    ProofMapper proofMapper;




    @Test
    public void saveTest(){
        Proof proof=new Proof();

        proof.setCreateTime(new Date(System.currentTimeMillis()));
        proof.setCategory(1);
        proof.setRecorderId(3);

        proofMapper.save(proof);

    }

    @Test
    public void findTest(){
        Proof proof=proofMapper.find(1);
        System.out.println(JSON.toJSONString(proof));
        System.out.println(proof.getCreateTime());
    }
}
