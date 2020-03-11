package huanju.chen.app.interceptor;

import huanju.chen.app.exception.v2.AccountingException;
import huanju.chen.app.exception.v2.BadRequestException;
import huanju.chen.app.exception.v2.UnAuthException;
import huanju.chen.app.security.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private CacheManager cacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenId=request.getHeader("token_id");
        Token token=null;

        String uri=request.getRequestURI();
        logger.debug("tokenId: "+tokenId);
        logger.debug("uri: "+uri);

        Cache cache=cacheManager.getCache("tokenV2Cache");

        if (tokenId==null){
            throw new UnAuthException(401,"未登录或已过期");
        }

        if (cache==null){
            logger.error("无法找到缓存容器...");
            throw new AccountingException(500,"系统出现了异常，请稍后重试");
        }

        token=cache.get(tokenId,Token.class);

        if (token==null){
            throw new UnAuthException(401,"未登录或已过期");
        }


        //验证是否为会计主管用户
        int role=token.getRole();

        if (uri.contains("/manager/")){



            if (role>2){
                throw new BadRequestException(400,"权限不足");
            }

        }

        //验证是否为超级管理员用户

        if (uri.contains("/admin/")){
            if (role>1){
                throw new BadRequestException(400,"权限不足");
            }
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
