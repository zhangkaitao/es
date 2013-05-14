/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.inject.support;

import com.google.common.collect.Sets;
import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 注入BaseRepository 及 BaseService 的工具类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-5 下午9:43
 * <p>Version: 1.0
 */
public class InjectBaseDependencyHelper {


    public static void findAndInjectBaseRepositoryDependency(BaseService<?, ?> baseService) {
        final Set<Object> candidates =
                findDependencies(baseService, BaseComponent.class);

        if(candidates.size() == 0 || candidates.size() > 1) {
            throw new IllegalStateException(
                    "expect 1 @BaseComponent anntation BaseRepository subclass bean, but found " + candidates.size() +
                   ", please check class [" + baseService.getClass() + "] @BaseComponent annotation.");
        }

        Object baseRepository = candidates.iterator().next();

        if (baseRepository.getClass().isAssignableFrom(BaseComponent.class)) {
            throw new IllegalStateException("[" + baseService.getClass() + "] @BaseComponent annotation bean " +
                    "must be BaseRepository subclass");
        }
        baseService.setBaseRepository( (BaseRepository)baseRepository );
    }



    public static void findAndInjectBaseServiceDependency(BaseController<?, ?> baseController) {
        final Set<Object> candidates =
                findDependencies(baseController, BaseComponent.class);

        if(candidates.size() > 1) {
            throw new IllegalStateException(
                    "expect 1 @BaseComponent anntation BaseService subclass bean, but found " + candidates.size() +
                    ", please check class [" + baseController.getClass() + "] @BaseComponent annotation.");
        }

        Object baseService = candidates.iterator().next();

        if (baseService.getClass().isAssignableFrom(BaseComponent.class)) {
            throw new IllegalStateException("[" + baseController.getClass() + "] @BaseComponent annotation bean " +
                    "must be BaseService subclass");
        }

        baseController.setBaseService( (BaseService)baseService );
    }



    /**
     * 根据注解在目标对象上的字段上查找依赖
     * @param target
     * @param annotation
     */
    private static Set<Object> findDependencies(final Object target, final Class<? extends Annotation> annotation) {

        final Set<Object> candidates = Sets.newHashSet();

        ReflectionUtils.doWithFields(
                target.getClass(),
                new ReflectionUtils.FieldCallback() {
                    @Override
                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                        ReflectionUtils.makeAccessible(field);
                        Object obj = ReflectionUtils.getField(field, target);
                        candidates.add(obj);
                    }
                },
                new ReflectionUtils.FieldFilter() {
                    @Override
                    public boolean matches(Field field) {
                        return field.isAnnotationPresent(annotation);
                    }
                }
        );

        ReflectionUtils.doWithMethods(
                target.getClass(),
                new ReflectionUtils.MethodCallback() {
                    @Override
                    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                        ReflectionUtils.makeAccessible(method);
                        PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
                        candidates.add(ReflectionUtils.invokeMethod(descriptor.getReadMethod(), target));
                    }
                },
                new ReflectionUtils.MethodFilter() {
                    @Override
                    public boolean matches(Method method) {
                        boolean hasAnnotation = false;
                        hasAnnotation = method.isAnnotationPresent(annotation);
                        if(!hasAnnotation) {
                            return false;
                        }

                        boolean hasReadMethod = false;
                        PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
                        hasReadMethod = descriptor != null && descriptor.getReadMethod() != null;

                        if(!hasReadMethod) {
                            return false;
                        }

                        return true;
                    }
                }
        );

        return candidates;
    }

}
