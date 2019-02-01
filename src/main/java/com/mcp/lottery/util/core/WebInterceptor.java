package com.mcp.lottery.util.core;

import com.mcp.lottery.util.core.inter.ResourceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {


    @Bean
    public ResourceInterceptor getResourceInterceptor(){
        return new ResourceInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getResourceInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
