/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.repository;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-2 下午8:27
 * <p>Version: 1.0
 */
public class ExcelDataRepositoryImpl {


    @PersistenceContext
    private EntityManager em;


    public void truncate() {
        em.unwrap(Session.class).doWork(new Work() {
            @Override
            public void execute(final Connection connection) throws SQLException {
                connection.createStatement().execute("truncate table showcase_excel_data");
            }
        });


    }
}
