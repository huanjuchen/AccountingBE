package huanju.chen.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class AccountingApplicationTests {

    @Test
    void contextLoads() {
        BigDecimal bigDecimal=new BigDecimal(100);
        System.out.println(bigDecimal.multiply(new BigDecimal(-1)));

    }


}
