package huanju.chen.app.config;

import huanju.chen.app.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * MVC配置
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    private AuthInterceptor authInterceptor;

    @Resource
    public void setAuthInterceptor(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login", "/hello")
                .excludePathPatterns("/js/**").excludePathPatterns("/css/**")
                .excludePathPatterns("/").excludePathPatterns("/index")
                .excludePathPatterns("/favicon.ico").excludePathPatterns("/fonts/*");
    }


}
