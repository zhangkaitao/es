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

package com.sishuok.es.common.web.bind.annotation;

import java.lang.annotation.*;

/**
 * <p>绑定请求参数到模型，并且暴露到模型中供页面使用</p>
 * <p>不同于@ModelAttribute</p>
 *
 * @author Zhang Kaitao
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormModel {

    /**
     * 指定请求参数的前缀和暴露到模型对象的名字供视图使用
     * <p/>
     * <p>1、绑定请求参数到模型，绑定规则<br/>
     * 如请求表单：<br>
     * <pre class="code">
     * <input name="student.name" value="Kate" /><br>
     * <input name="student.type" value="自费" /><br>
     * </pre>
     * 则请求处理方法：<br>
     * <pre class="code">
     *
     * @RequestMapping(value = "/test")
     * public String test(@FormModel("student") Student student) //这样将绑定  student.name student.type两个参数
     * </pre>
     * <p/>
     * 而springmvc默认<br>
     * 如请求表单：<br>
     * <pre class="code">
     * <input name="name" value="Kate" /><br>
     * <input name="type" value="自费" /><br>
     * </pre>
     * 则请求处理方法：<br>
     * <pre class="code">
     * public String test(@ModelAttribute("student") Student student) //这样将绑定name type两个参数
     * </pre>
     * <p/>
     * <p>具体可以参考iteye的问题：<a href="http://www.iteye.com/problems/89942">springMVC 数据绑定 多个对象 如何准确绑定</a>
     * <p/>
     * <p>2、根据value中的名字暴露到模型对象中供视图使用
     */
    String value();

}
