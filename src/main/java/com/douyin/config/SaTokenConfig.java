package com.douyin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册 Sa-Token 拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SaInterceptor(handler -> {
//
//            SaRouter.notMatch("/users/login")
//                    .check(StpUtil::checkLogin);
//        })).addPathPatterns("/**");
//    }

    /**
     * 可选：配置 Sa-Token 上下文处理器（Spring Boot 3 通常无需手动配置）
     */
        // @Bean
    // public SaTokenContext saTokenContext() {
    //    return new SaTokenContextForSpring();
    // }
}