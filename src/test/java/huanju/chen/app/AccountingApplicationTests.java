package huanju.chen.app;

import huanju.chen.app.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AccountingApplicationTests {

    @Test
    void contextLoads() {
        String dateStr= "2020-05-11";

        Date date=new Date();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(Arrays.toString(DateUtils.monthStartEnd(dateStr)));

        System.out.println(Arrays.toString(DateUtils.monthStartEnd(sdf.format(date))));


        BigDecimal bigDecimal=new BigDecimal(0.00);
        System.out.println(bigDecimal.subtract(new BigDecimal(50.00)));




    }


}
