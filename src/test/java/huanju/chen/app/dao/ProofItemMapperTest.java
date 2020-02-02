package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.dto.ProofItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ProofItemMapperTest {

    @Resource
    ProofItemMapper proofItemMapper;

    @Test
    public void saveTest() {


    }

    @Test
    public void findTest() {

        List<ProofItem> proofItems=proofItemMapper.items(6);

        for (ProofItem item:proofItems){
            System.out.println(JSON.toJSONString(item));
        }



    }

}
