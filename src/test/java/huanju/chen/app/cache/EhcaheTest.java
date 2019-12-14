package huanju.chen.app.cache;



import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;




@SpringBootTest
public class EhcaheTest {

    @Autowired
    CacheManager cacheManager;


    @Test
    void cacheTest(){

    }

}
