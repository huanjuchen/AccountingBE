package huanju.chen.app.dao;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.domain.dto.Proof;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class ProofMapperTest {

    @Resource
    ProofMapper proofMapper;

    @Test
    public void saveTest() {

    }

    @Test
    public void findTest() {
        Proof proof = proofMapper.find(2);
        System.out.println(JSON.toJSONString(proof));
    }


    @Test
    public void list() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        format.parse()

        Date startDate = format.parse("2020-01-03");

        Date endDate = new Date();


        Map<String, Object> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        List<Proof> proofs = proofMapper.list(map);

        for (Proof proof:proofs){
            System.out.println(JSON.toJSONString(proof));
        }

    }

}
