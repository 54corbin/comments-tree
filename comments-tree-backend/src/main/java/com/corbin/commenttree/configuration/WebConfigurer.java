package com.corbin.commenttree.configuration;

import com.corbin.commenttree.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置检查token的拦截器
 * @author corbin
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 为了能顺利将需要的依赖注入到JwtInterceptor中必须以这样的方式创建
     * @return 登录校验拦截器
     */
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String [] excludes = new String[]{"/*.html","/html/**","/js/**","/css/**","/images/**"};
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);

    }

}
