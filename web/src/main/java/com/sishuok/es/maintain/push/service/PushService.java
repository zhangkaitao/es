/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.push.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-17 下午2:14
 * <p>Version: 1.0
 */
@Service
public class PushService {

    private volatile Map<Long, Queue<DeferredResult<Object>>> userIdToDeferredResultMap = new ConcurrentHashMap();

    public boolean isOnline(final Long userId) {
        return userIdToDeferredResultMap.containsKey(userId);
    }

    /**
     * 上线后 创建一个空队列，防止多次判断
     * @param userId
     */
    public void online(final Long userId) {
        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
        if(queue == null) {

            queue = new LinkedBlockingDeque<DeferredResult<Object>>(); //如果jdk 1.7 可以换成ConcurrentLinkedQueue
            userIdToDeferredResultMap.put(userId, queue);
        }
    }


    public void offline(final Long userId) {

        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.remove(userId);
        if(queue != null) {
            for(DeferredResult<Object> result : queue) {
                try {
                    result.setResult("");
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    public DeferredResult newDeferredResult(final Long userId) {
        final DeferredResult<Object> deferredResult = new DeferredResult<Object>();
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
                if(queue != null) {
                    queue.remove(deferredResult);
                    deferredResult.setResult("");
                }
            }
        });
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                deferredResult.setErrorResult("");
            }
        });
        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
        if(queue == null) {
            queue = new LinkedBlockingDeque<DeferredResult<Object>>();
            userIdToDeferredResultMap.put(userId, queue);
        }
        queue.add(deferredResult);

        return deferredResult;
    }

    public void push(final Long userId, final Object data) {
        Queue<DeferredResult<Object>> queue =  userIdToDeferredResultMap.get(userId);
        if(queue == null) {
            return;
        }
        for(DeferredResult<Object> deferredResult : queue) {
            if(!deferredResult.isSetOrExpired()) {
                try {
                    deferredResult.setResult(data);
                } catch (Exception e) {
                    queue.remove(deferredResult);
                }
            }
        }
    }


    /**
     * 定期清空队列 防止中间推送消息时中断造成消息丢失
     */
    @Scheduled(fixedRate = 5L * 60 * 1000)
    public void sync() {
        Map<Long, Queue<DeferredResult<Object>>> oldMap = userIdToDeferredResultMap;
        userIdToDeferredResultMap = new ConcurrentHashMap<Long, Queue<DeferredResult<Object>>>();
        for(Queue<DeferredResult<Object>> queue : oldMap.values()) {
            if(queue == null) {
                continue;
            }

            for(DeferredResult<Object> deferredResult : queue) {
                try {
                    deferredResult.setResult("");
                } catch (Exception e) {
                    queue.remove(deferredResult);
                }
            }

        }
    }


}
