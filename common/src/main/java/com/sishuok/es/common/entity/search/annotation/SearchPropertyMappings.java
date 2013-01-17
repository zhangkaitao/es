/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>domain的查询属性与实际属性的映射，用于方便类型转换</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午8:54
 * <p>Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SearchPropertyMappings {

    SearchPropertyMapping[] value() default {};

}
