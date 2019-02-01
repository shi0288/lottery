package com.mcp.lottery.util.scanner;

import com.mcp.lottery.util.PluginCache;
import com.mcp.lottery.util.annotation.Type;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

public class PluginPathScanner extends ClassPathBeanDefinitionScanner {



    private Class<? extends Annotation> annotationClass;


    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void registerFilters() {
        if (this.annotationClass != null) {
            this.addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        }
    }


    public PluginPathScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }


    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            Class<?> clazz = null;
            try {
                clazz = Class.forName(String.valueOf(definition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//            BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
//            String beanName = beanNameGenerator.generateBeanName(definition, this.getRegistry());
            Type type = clazz.getAnnotation(Type.class);
            definition.setBeanClass(clazz);
            this.getRegistry().registerBeanDefinition(type.value(), definition);
            PluginCache.add(type.value(), type.name());
        }
        return beanDefinitions;
    }


}
