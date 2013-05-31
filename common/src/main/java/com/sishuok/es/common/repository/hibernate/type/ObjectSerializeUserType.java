/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.hibernate.type;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Object序列化/反序列化
 * 数据库中以hex字符串存储
 * 参考http://jinnianshilongnian.iteye.com/blog/1497791
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-20 下午4:46
 * <p>Version: 1.0
 */
public class ObjectSerializeUserType implements UserType, Serializable {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == o1) {
            return true;
        }
        if (o == null || o == null) {
            return false;
        }

        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }


    /**
     * 从JDBC ResultSet读取数据,将其转换为自定义类型后返回
     * (此方法要求对克能出现null值进行处理)
     * names中包含了当前自定义类型的映射字段名称
     *
     * @param names
     * @param owner
     * @return
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        ObjectInputStream ois = null;
        try {
            String hexStr = rs.getString(names[0]);
            ois = new ObjectInputStream(new ByteArrayInputStream(Hex.decodeHex(hexStr.toCharArray())));
            return ois.readObject();
        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 本方法将在Hibernate进行数据保存时被调用
     * 我们可以通过PreparedStateme将自定义数据写入到对应的数据库表字段
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        ObjectOutputStream oos = null;
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                oos.close();

                byte[] objectBytes = bos.toByteArray();
                String hexStr = Hex.encodeHexString(objectBytes);

                st.setString(index, hexStr);
            } catch (Exception e) {
                throw new HibernateException(e);
            } finally {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * 提供自定义类型的完全复制方法
     * 本方法将用构造返回对象
     * 当nullSafeGet方法调用之后，我们获得了自定义数据对象，在向用户返回自定义数据之前，
     * deepCopy方法将被调用，它将根据自定义数据对象构造一个完全拷贝，并将此拷贝返回给用户
     * 此时我们就得到了自定义数据对象的两个版本，第一个是从数据库读出的原始版本，其二是我们通过
     * deepCopy方法构造的复制版本，原始的版本将有Hibernate维护，复制版由用户使用。原始版本用作
     * 稍后的脏数据检查依据；Hibernate将在脏数据检查过程中将两个版本的数据进行对比（通过调用
     * equals方法），如果数据发生了变化（equals方法返回false），则执行对应的持久化操作
     *
     * @param o
     * @return
     * @throws HibernateException
     */
    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) return null;
        return o;
    }

    /**
     * 本类型实例是否可变
     *
     * @return
     */
    @Override
    public boolean isMutable() {
        return true;
    }

    /* 序列化 */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return ((Serializable) value);
    }

    /* 反序列化 */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
