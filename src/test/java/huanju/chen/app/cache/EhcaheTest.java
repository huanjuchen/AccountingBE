package huanju.chen.app.cache;



import huanju.chen.app.security.token.Token;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;




@SpringBootTest
public class EhcaheTest {

    @Autowired
    CacheManager cacheManager;


    @Test
    void cacheTest(){
        Cache cache=cacheManager.getCache("tokenV2Cache");

        if (cache!=null){
            cache.put("hhh",new Token(1,1));


            cache.evict("hhh");

            System.out.println("hh");
        }


    }

}
