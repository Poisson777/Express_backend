package com.example.express_end.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        registry.addInterceptor(new AllowOriginIntercepter()).addPathPatterns("/**");

//        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
//        registration.addPathPatterns("/**"); //所有路径都被拦截
//        registration.excludePathPatterns(    //添加不拦截路径
////                "/user/login",              //登录路径
//                "/user/register",           //注册路径
//                "/**/*.html",                //html静态资源
//                "/**/*.js",                  //js静态资源
//                "/**/*.css"                  //css静态资源
//        );
    }

}
