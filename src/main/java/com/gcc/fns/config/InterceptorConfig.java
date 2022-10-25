package com.gcc.fns.config;

import com.gcc.fns.interceptors.AuthorizeInterceptor;
import com.gcc.fns.interceptors.LoginInterceptor;
import com.gcc.fns.interceptors.VisitLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 拦截器配置
 * @create 2022-10-2022/10/21 12:24
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private AuthorizeInterceptor authorizeInterceptor;

    @Resource
    private VisitLoginInterceptor visitLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user/*");

        registry.addInterceptor(authorizeInterceptor)
                .addPathPatterns("/user/*")
                .excludePathPatterns("/user/updateUserInfo");

        registry.addInterceptor(visitLoginInterceptor)
                .addPathPatterns("/passport/codeLogin")
                .addPathPatterns("/passport/pwdLogin");
    }
}
