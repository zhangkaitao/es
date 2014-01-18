package com.sishuok.es.maintain.dynamictask.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-18
 * <p>Version: 1.0
 */
@Service("testTask1")
public class TestTask1 {

    @Autowired
    private ApplicationContext ctx;

    public void run() {
        System.out.println("====hello test task1::" + ctx);
    }
}
