package huanju.chen.app.security.utils;

import huanju.chen.app.exception.CustomException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


public class AuthUtils {


    private static Logger logger = LoggerFactory.getLogger(AuthUtils.class);


    public static void userLoginCheck(String tempToken, String tokenStr) {

        if (!tokenStr.equals(tempToken)) {
            throw new CustomException("授权不正确", HttpStatus.UNAUTHORIZED);
        }
    }

    public static void managerCheck(String tempToken,String tokenStr,Claims claims){
        userLoginCheck(tempToken,tokenStr);
        int role=claims.get("role",Integer.class);
        if (role!=2&&role!=1){
            throw new CustomException("权限不足",HttpStatus.FORBIDDEN);
        }
    }


    public static void adminCheck(String tempToken,String tokenStr,Claims claims){
        userLoginCheck(tempToken,tokenStr);
        int role=claims.get("role",Integer.class);
        if (role!=1){
            throw new CustomException("权限不足",HttpStatus.FORBIDDEN);
        }
    }




}
