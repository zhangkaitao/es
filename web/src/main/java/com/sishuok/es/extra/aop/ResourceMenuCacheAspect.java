/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.extra.aop;

import com.sishuok.es.common.cache.BaseCacheAspect;
import com.sishuok.es.sys.user.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 缓存及清理菜单缓存
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-16 下午3:49
 * <p>Version: 1.0
 */
@Component
@Aspect
public class ResourceMenuCacheAspect extends BaseCacheAspect {

    public ResourceMenuCacheAspect() {
        setCacheName("sys-menuCache");
    }

    private String menusKeyPrefix = "menus-";


    @Pointcut(value = "target(com.sishuok.es.sys.resource.service.ResourceService)")
    private void resourceServicePointcut() {
    }

    @Pointcut(value = "execution(* save(..)) || execution(* update(..)) || execution(* delete(..))")
    private void resourceCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* findMenus(*)) && args(arg)", argNames = "arg")
    private void resourceCacheablePointcut(User arg) {
    }

    @Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
    public void findRolesCacheableAdvice() throws Throwable {
        clear();
    }

    @Around(value = "resourceServicePointcut() && resourceCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {

        User user = arg;

        String key = menusKey(user.getId());
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }


    public void evict(Long userId) {
        evict(menusKey(userId));
    }


    private String menusKey(Long userId) {
        return this.menusKeyPrefix + userId;
    }


}
