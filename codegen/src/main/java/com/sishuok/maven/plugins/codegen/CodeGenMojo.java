/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.maven.plugins.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import javax.swing.*;

/**
 * 代码生成
 *
 * @goal gen
 * @threadSafe
 * @requiresDirectInvocation true
 * @requiresDependencyResolution compile
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-1 下午10:00
 * <p>Version: 1.0
 */
public class CodeGenMojo extends AbstractMojo {

    @SuppressWarnings("deprecation")
    public void execute() throws MojoExecutionException {

        final Object waitFor = new Object();

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable throwable) {
                synchronized (waitFor) {
                    waitFor.notifyAll();
                }
            }
        });

        try {
            Application thisClass = new Application();
            thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            thisClass.setVisible(true);
        } catch (Exception e) {/*ignore*/}

        synchronized (waitFor) {
            try {
                waitFor.wait();
            } catch (InterruptedException ex) {/*ignore*/}
        }


    }


}
