/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search.annotation;

import java.lang.annotation.*;

/**
 * <p>将查询属性名映射到实际的属性名</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午8:58
 * <p>Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SearchPropertyMapping {

    /**
     * 查询时的属性名
     *
     * @return
     */
    String searchProperty();

    /**
     * 映射到的实际的属性名
     *
     * @return
     */
    String entityProperty();

    /**
     * 默认操作符
     * @return
     */
    String defaultOperator() default "";

}
