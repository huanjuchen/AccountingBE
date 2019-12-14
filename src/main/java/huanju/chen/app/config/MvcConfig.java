package huanju.chen.app.config;

import huanju.chen.app.interceptor.AdminInterceptor;
import huanju.chen.app.interceptor.AuthInterceptor;
import huanju.chen.app.interceptor.ManageAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource(name = "authInterceptor")
    private AuthInterceptor authInterceptor;

    @Resource(name = "adminInterceptor")
    private AdminInterceptor adminInterceptor;

    @Resource(name = "manageAuthInterceptor")
    private ManageAuthInterceptor manageAuthInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login","/hello","/admin/**","/manage/**");

        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");

        registry.addInterceptor(manageAuthInterceptor).addPathPatterns("/manage/**");


    }

}
