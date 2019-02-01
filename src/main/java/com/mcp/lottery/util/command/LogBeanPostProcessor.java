package com.mcp.lottery.util.command;

import com.mcp.lottery.util.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;


@Configuration
public class LogBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class clazz = o.getClass();
        initializeLog(o, clazz);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }


    private void initializeLog(final Object bean, final Class<? extends Object> clazz) {
        ReflectionUtils.doWithFields(clazz, field -> {
            boolean visible = field.isAccessible();
            try {
                field.setAccessible(true);
                if (field.get(bean) == null) {
                    field.set(bean, LoggerFactory.getLogger(clazz));
                }
            } catch (Exception e) {
                throw new BeanInitializationException(String.format(
                        "初始化logger失败!bean=%s;field=%s", bean, field));
            } finally {
                field.setAccessible(visible);
            }
        }, field -> {
            if (field.getAnnotation(Log.class) == null) {
                return false;
            }
            if (!field.getType().isAssignableFrom(Logger.class)) {
                return false;
            }
            return true;
        });
    }




}
