package huanju.chen.app;

import huanju.chen.app.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class AccountingApplicationTests {

    @Test
    void contextLoads() {
        String dateStr= "2020-06-11";

        Date date=new Date();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(DateUtils.getMonthEnd(dateStr));

        System.out.println(sdf.format(DateUtils.getMonthEnd(date)));

        System.out.println(DateUtils.getMonthLastDay(date));
        System.out.println(DateUtils.getMonthEnd(dateStr));

    }


}
