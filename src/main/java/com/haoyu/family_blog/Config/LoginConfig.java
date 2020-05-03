package com.haoyu.family_blog.Config;

import com.haoyu.family_blog.Component.LoginHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Haoyu WU on 2020/4/15.
 */

public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/blog/delete", "/blog/publish", "/blog/update")
                .addPathPatterns("/user/delete", "/user/update")
                .addPathPatterns("/logout");
    }
}
