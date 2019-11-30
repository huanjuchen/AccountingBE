package huanju.chen.app.utils;


import huanju.chen.app.model.entity.User;
import huanju.chen.app.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JwtTest {

    @Test
    public void jwtTest(){
        User user=new User();
        user.setRole(1);
        user.setId(1);

        String token=JwtUtils.buildJwt(user);
        System.out.println(token);


        Claims claims=JwtUtils.decodeJwt(token);
        System.out.println(claims.get("id"));
        System.out.println(claims.get("role"));
        System.out.println(claims.get("version"));

    }

}
