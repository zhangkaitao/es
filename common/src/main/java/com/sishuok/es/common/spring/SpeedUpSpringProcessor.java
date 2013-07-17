/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedMap;

import java.util.*;

/**
 * 加速spring启动，通过移除bean定义实现
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-3 下午9:47
 * <p>Version: 1.0
 */
public class SpeedUpSpringProcessor implements BeanFactoryPostProcessor {

    private final Logger log = LoggerFactory.getLogger(SpeedUpSpringProcessor.class);

    private String[] removedClassPatterns;
    private String[] includeClassPatterns;

    private String[] removedBeanNames;

    private Map<String, Set<String[]>> removedBeanProperties;
    private Map<String, String> replaceBeanProperties;

    private String[] noneLazyBeanNames;

    /**
     * 设置需要排除的bean的类的正则表达式
     * @param
     */
    public void setRemovedClassPatterns(String[] removedClassPatterns) {
        this.removedClassPatterns = removedClassPatterns;
    }

    /**
     * 设置需要包含的bean的类的正则表达式
     * @param includeClassPatterns
     */
    public void setIncludeClassPatterns(String[] includeClassPatterns) {
        this.includeClassPatterns = includeClassPatterns;
    }

    /**
     * 需要移除的bean的名字
     * @param removedBeanNames
     */
    public void setRemovedBeanNames(String[] removedBeanNames) {
        this.removedBeanNames = removedBeanNames;
    }

    /**
     * 需要移除的bean及属性名字 如 bean@property
     * 或
     * 需要替换掉值的bean及属性名字 如 bean@property=123
     * @param removeOrReplaceBeanProperties
     */
    public void setRemoveOrReplaceBeanProperties(String[] removeOrReplaceBeanProperties) {
        this.removedBeanProperties = new HashMap<String, Set<String[]>>(removeOrReplaceBeanProperties.length);
        for(String removeOrReplaceBeanProperty : removeOrReplaceBeanProperties) {

            int equalIndex = removeOrReplaceBeanProperty.indexOf('=');
            boolean isReplace = equalIndex >= 0;

            String removeBeanProperty = removeOrReplaceBeanProperty;
            if(isReplace) {
                removeBeanProperty = removeBeanProperty.substring(0, equalIndex);
            }

            String[] properties = removeBeanProperty.split("@");
            String beanName = properties[0];

            Set<String[]> propertiesSet = this.removedBeanProperties.get(beanName);

            if(propertiesSet == null) {
                propertiesSet = new HashSet<String[]>();
                this.removedBeanProperties.put(beanName, propertiesSet);
            }
            propertiesSet.add(Arrays.copyOfRange(properties, 1, properties.length));

            if (isReplace) {
                this.replaceBeanProperties = new HashMap<String, String>();
                String[] props = removeOrReplaceBeanProperty.split("=");
                this.replaceBeanProperties.put(props[0], props[1]);
            }

        }

    }

    /**
     * 设置非延迟加载的bean
     * @param noneLazyBeanNames
     */
    public void setNoneLazyBeanNames(String[] noneLazyBeanNames) {
        this.noneLazyBeanNames = noneLazyBeanNames;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        if(!(beanFactory instanceof DefaultListableBeanFactory)) {
            log.error("if speed up spring, bean factory must be type of DefaultListableBeanFactory");
            return;
        }

        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        for(String beanName : listableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            //如果匹配模式 就移除掉
            if(needRemove(beanName, beanDefinition)) {
                listableBeanFactory.removeBeanDefinition(beanName);
                continue;
            }

            //否则设置为lazy
            if(needLazyInit(beanName)) {
                beanDefinition.setLazyInit(true);
            }
        }
    }

    private boolean needLazyInit(String beanName) {
        if(ArrayUtils.isEmpty(noneLazyBeanNames)) {
            return true;
        }
        for(String noneLazyBeanName : noneLazyBeanNames) {
            if(beanName.equals(noneLazyBeanName)) {
                return false;
            }
        }
        return true;
    }

    private boolean needRemove(String beanName, BeanDefinition beanDefinition) {

        if(ArrayUtils.isNotEmpty(removedBeanNames)) {
            for(String removedBeanName : removedBeanNames) {
                if(beanName.equals(removedBeanName)) {
                    return true;
                }
                if(beanDefinition.getBeanClassName().equals(removedBeanName)) {
                    return true;
                }
            }
        }

        if(this.removedBeanProperties != null) {
            Set<String[]> propertiesSet = removedBeanProperties.get(beanName);
            if(propertiesSet != null) {
                Iterator<String[]> iter = propertiesSet.iterator();
                MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();

                while(iter.hasNext()) {
                    String[] properties = iter.next();
                    if(properties.length == 1) {

                        //先移除
                        propertyValues.removePropertyValue(properties[0]);

                        //如果需要替换，替换掉值（只支持基本属性）
                        if(this.replaceBeanProperties != null) {
                            String key = beanName + "@" + properties[0];
                            if(this.replaceBeanProperties.containsKey(key)) {
                                propertyValues.add(properties[0], this.replaceBeanProperties.get(key));
                            }
                        }
                    } else {
                        PropertyValue propertyValue = propertyValues.getPropertyValue(properties[0]);
                        if(propertyValue != null) {
                            Object nextValue = propertyValue.getValue();
                            //目前只支持 二级 + 移除Map的
                            if(nextValue instanceof ManagedMap) {

                                TypedStringValue typedStringValue = new TypedStringValue(properties[1]);
                                ((ManagedMap) nextValue).remove(typedStringValue);

                                //如果需要替换，替换掉值（只支持基本属性）
                                if(this.replaceBeanProperties != null) {
                                    String key = beanName + "@" + properties[0] + "@" + properties[1];
                                    if(this.replaceBeanProperties.containsKey(key)) {
                                        ((ManagedMap) nextValue).put(properties[1], this.replaceBeanProperties.get(key));
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        String className = beanDefinition.getBeanClassName();

        //spring data jpa
        if(className.equals("com.sishuok.es.common.repository.support.SimpleBaseRepositoryFactoryBean") ||
                className.equals("org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean")) {
            PropertyValue repositoryInterfaceValue =
                    beanDefinition.getPropertyValues().getPropertyValue("repositoryInterface");
            if(repositoryInterfaceValue != null) {
                className = repositoryInterfaceValue.getValue().toString();
            }
        }


        if(ArrayUtils.isEmpty(this.removedClassPatterns)) {
            return false;
        }

        if(ArrayUtils.isNotEmpty(this.includeClassPatterns)) {
            for(String includeClassPattern : includeClassPatterns) {
                if(className.matches(includeClassPattern)) {
                    return false;
                }
            }
        }

        for (String removedClassPattern : removedClassPatterns) {
            if (className.matches(removedClassPattern)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String c = "com.sishuok.es.sys.user.web.bind.A";
        System.out.println(c.matches("com\\.sishuok\\.es\\.sys.*\\.web\\.controller.*"));
    }
}
