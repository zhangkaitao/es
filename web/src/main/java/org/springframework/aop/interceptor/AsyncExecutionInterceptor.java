/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.springframework.aop.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * AOP Alliance {@code MethodInterceptor} that processes method invocations
 * asynchronously, using a given {@link org.springframework.core.task.AsyncTaskExecutor}.
 * Typically used with the {@link org.springframework.scheduling.annotation.Async} annotation.
 *
 * <p>In terms of target method signatures, any parameter types are supported.
 * However, the return type is constrained to either {@code void} or
 * {@code java.util.concurrent.Future}. In the latter case, the Future handle
 * returned from the proxy will be an actual asynchronous Future that can be used
 * to track the result of the asynchronous method execution. However, since the
 * target method needs to implement the same signature, it will have to return
 * a temporary Future handle that just passes the return value through
 * (like Spring's {@link org.springframework.scheduling.annotation.AsyncResult}
 * or EJB 3.1's {@code javax.ejb.AsyncResult}).
 *
 * <p>As of Spring 3.1.2 the {@code AnnotationAsyncExecutionInterceptor} subclass is
 * preferred for use due to its support for executor qualification in conjunction with
 * Spring's {@code @Async} annotation.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 3.0
 * @see org.springframework.scheduling.annotation.Async
 * @see org.springframework.scheduling.annotation.AsyncAnnotationAdvisor
 * @see org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor
 */
public class AsyncExecutionInterceptor extends AsyncExecutionAspectSupport
        implements MethodInterceptor, Ordered {

    /**
     * Create a new {@code AsyncExecutionInterceptor}.
     * @param executor the {@link java.util.concurrent.Executor} (typically a Spring {@link org.springframework.core.task.AsyncTaskExecutor}
     * or {@link java.util.concurrent.ExecutorService}) to delegate to.
     */
    public AsyncExecutionInterceptor(Executor executor) {
        super(executor);
    }


    /**
     * Intercept the given method invocation, submit the actual calling of the method to
     * the correct task executor and return immediately to the caller.
     * @param invocation the method to intercept and make asynchronous
     * @return {@link java.util.concurrent.Future} if the original method returns {@code Future}; {@code null}
     * otherwise.
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        final Method setCurrentProxyMethod = ReflectionUtils.findMethod(AopContext.class, "setCurrentProxy", Object.class);

        final Object currentProxy = AopContext.currentProxy();
        ReflectionUtils.makeAccessible(setCurrentProxyMethod);
        Future<?> result = determineAsyncExecutor(specificMethod).submit(
                new Callable<Object>() {
                    public Object call() throws Exception {
                        try {
                            if(currentProxy != null) {
                                ReflectionUtils.invokeMethod(setCurrentProxyMethod, null, currentProxy);
                            }
                            Object result = invocation.proceed();
                            if (result instanceof Future) {
                                return ((Future<?>) result).get();
                            }
                        }
                        catch (Throwable ex) {
                            ReflectionUtils.rethrowException(ex);
                        } finally {
                            if(currentProxy != null) {
                                ReflectionUtils.invokeMethod(setCurrentProxyMethod, null, (Object) null);
                            }
                        }
                        return null;
                    }
                });

        if (Future.class.isAssignableFrom(invocation.getMethod().getReturnType())) {
            return result;
        }
        else {
            return null;
        }
    }

    /**
     * This implementation is a no-op for compatibility in Spring 3.1.2.
     * Subclasses may override to provide support for extracting qualifier information,
     * e.g. via an annotation on the given method.
     * @return always {@code null}
     * @see #determineAsyncExecutor(Method)
     * @since 3.1.2
     */
    @Override
    protected String getExecutorQualifier(Method method) {
        return null;
    }

    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
