package com.lmq.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 12:56
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //配置类

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //把拦截器加过来,添加过滤的路径,排除一些路径
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
