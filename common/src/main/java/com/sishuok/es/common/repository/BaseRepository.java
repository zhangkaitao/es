/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * <p>抽象DAO层基类 提供一些简便方法<br/>
 * 具体使用请参考测试用例：{@see com.sishuok.es.common.repository.UserRepository}
 *
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:46
 * <p>Version: 1.0
 */
public interface BaseRepository<M extends BaseEntity, ID extends Serializable>
        extends JpaRepository<M, ID>, JpaSpecificationExecutor<M> {

}
