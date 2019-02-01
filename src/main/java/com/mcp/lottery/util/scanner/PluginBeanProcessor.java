package com.mcp.lottery.util.scanner;


import com.mcp.lottery.util.annotation.Type;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PluginBeanProcessor implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        registerBean(beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }


    private void registerBean(BeanDefinitionRegistry registry) {
        PluginPathScanner pluginPathScanner = new PluginPathScanner(registry);
        pluginPathScanner.setAnnotationClass(Type.class);
        pluginPathScanner.registerFilters();
        pluginPathScanner.doScan("com.mcp.lottery.plugin");
    }


}
