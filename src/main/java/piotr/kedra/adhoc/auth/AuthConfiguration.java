package piotr.kedra.adhoc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AuthConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthorizationHandlerInterceptor authorizationHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/**").excludePathPatterns("/auth/token/**");
    }
}
