package huanju.chen.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountingApplicationTests {
    Integer int1 = -128;


    @Test
    void contextLoads() {
        Integer int2 = -128;
        System.out.println(int1 == int2);

    }


}
