package huanju.chen.app.security.utils;


import cn.hutool.crypto.SecureUtil;
import huanju.chen.app.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;


/**
 * @author HuanJu
 */
public class JwtUtils {

    private static Key key= new SecretKeySpec(SecureUtil.md5("M@$%r%F&G").getBytes(),SignatureAlgorithm.HS256.getJcaName());;



    public static String buildJwt(User user) {
        long version=System.currentTimeMillis();
        JwtBuilder builder= Jwts.builder();
        Map<String,Object> map=new HashMap<>(8);
        map.put("id",user.getId());
        map.put("role",user.getRole());
        map.put("version",version);
        builder.setClaims(map);
        builder.signWith(key,SignatureAlgorithm.HS256);

        return builder.compact();
    }


    public static Claims decodeJwt(String token) {
        Claims claims = Jwts.parser().
                setSigningKey(key).
                parseClaimsJws(token).getBody();

        return claims;
    }




}
