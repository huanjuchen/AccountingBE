package huanju.chen.app.interceptor;

import com.alibaba.fastjson.JSON;
import huanju.chen.app.exception.CustomException;
import huanju.chen.app.security.utils.AuthUtils;
import huanju.chen.app.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenStr = request.getHeader("token");
        if (tokenStr == null || tokenStr.length() == 0) {
            throw new CustomException("未登录", HttpStatus.UNAUTHORIZED);
        }
        Cache cache = cacheManager.getCache("tokenCache");

        if (cache == null) {
            throw new CustomException("服务器错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {

            Claims claims = JwtUtils.decodeJwt(tokenStr);
            logger.debug("【DEBUG】："+this.getClass()+"---"+ JSON.toJSONString(claims));

            int tokenId = (int) claims.get("id");
            String tempToken = cache.get(tokenId, String.class);

            AuthUtils.userLoginCheck(tempToken,tokenStr);


        } catch (CustomException e) {
            logger.warn(this.getClass().getName() + "---" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "---" + e.getMessage());
            throw new CustomException("非法Token", HttpStatus.UNAUTHORIZED);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
