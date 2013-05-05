/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sishuok.es.common.inject.annotation;

import java.lang.annotation.*;

/**
 * 查找注解的字段作为BaseService/BaseRepository数据
 * 即
 * 1、查找对象中的注解了@BaseComponent的对象
 * 2、
 *  调用BaseCRUDController.setBaseService 设置BaseService
 *  调用BaseService.setBaseRepository 设置BaseRepository
 *
 * @author Zhang Kaitao
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseComponent {

}
