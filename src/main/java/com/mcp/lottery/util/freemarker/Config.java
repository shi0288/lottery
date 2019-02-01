package com.mcp.lottery.util.freemarker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Map;

/**
 * Created by shiqm on 2018/9/25.
 */

@Configuration
public class Config {


    /**
     * 增加静态地址
     *
     * @return
     */
    @Bean
    public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
        return strings -> {
            //添加自定义解析器
            Map map = resolver.getAttributesMap();
            map.put("convert", intiParamConvert());
        };
    }

    /**
     * 用户状态转换器
     *
     * @return
     */
    @Bean
    public ParamConvert intiParamConvert() {
        return new ParamConvert();
    }




}
