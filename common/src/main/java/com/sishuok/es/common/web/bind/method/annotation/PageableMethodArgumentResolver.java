/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.bind.method.annotation;

import com.google.common.collect.Lists;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>请求分页数据绑定到Pageable，支持请求参数和uri template数据的绑定</p>
 * <p>使用指南：
 * <pre>
 *   1.1、简单的分页请求参数格式如下：
 *     page.size=10  分页大小
 *     page.pn=1    页码 从1开始
 *   1.2、控制器处理方法写法
 *     public void test(Pageable page);
 * </pre>
 * <pre>
 *   2.1、带排序的分页请求参数格式如下：
 *     page.size=10  分页大小
 *     page.pn=1    页码 从1开始
 *     sort.a.b=desc
 *     sort.c=asc
 *   默认按照排序关键词的字典顺序排（因为Map存储是无序） 如果想有序排 可以在sort之后跟一个顺序号
 *     sort2.a.b=desc
 *     sort1.c=asc
 *   2.2、控制器处理方法写法
 *     public void test(Pageable page);
 * </pre>
 * <pre>
 *   3.1、带前缀的 排序分页请求参数格式如下：
 *     test_page.size=10  分页大小
 *     test_page.pn=1    页码 从1开始
 *     test_sort.a.b=desc
 *     test_sort.c=asc
 *     foo_page.size=10  分页大小
 *     foo_page.pn=1    页码 从1开始
 *     foo_sort.a.b=desc
 *     foo_sort.c=asc
 *
 *   排序默认按照请求时顺序排
 *   3.2、控制器处理方法写法
 *     public void test(@Qualifier("test") Pageable page1, @Qualifier("test") Pageable page2);
 * </pre>
 * <p/>
 * <pre>
 *     错误的用法，如果有多个请使用@Qualifier指定前缀
 *     public void fail(Pageable page1, Pageable page2);
 * </pre>
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-23 下午1:34
 * <p>Version: 1.0
 */
public class PageableMethodArgumentResolver extends BaseMethodArgumentResolver {

    private static final Pageable DEFAULT_PAGE_REQUEST = new PageRequest(0, 10);
    private static final String DEFAULT_PAGE_PREFIX = "page";
    private static final String DEFAULT_SORT_PREFIX = "sort";

    private Pageable fallbackPagable = DEFAULT_PAGE_REQUEST;
    private String pagePrefix = DEFAULT_PAGE_PREFIX;
    private String sortPrefix = DEFAULT_SORT_PREFIX;

    private int minPageSize = 5;
    private int maxPageSize = 100;

    /**
     * 设置最小分页大小 默认10
     *
     * @param minPageSize
     */
    public void setMinPageSize(int minPageSize) {
        this.minPageSize = minPageSize;
    }

    /**
     * 设置最大分页大小 默认100
     *
     * @param maxPageSize
     */
    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }


    /**
     * Setter to configure a fallback instance of {@link Pageable} that is being used to back missing parameters. Defaults
     * to {@value #DEFAULT_PAGE_REQUEST}.
     *
     * @param fallbackPagable the fallbackPagable to set
     */
    public void setFallbackPagable(Pageable fallbackPagable) {
        this.fallbackPagable = null == fallbackPagable ? DEFAULT_PAGE_REQUEST : fallbackPagable;
    }

    /**
     * Setter to configure the prefix of request parameters to be used to retrieve paging information. Defaults to
     * {@link #DEFAULT_PAGE_PREFIX}.
     *
     * @param pagePrefix the prefix to set
     */
    public void setPagePrefix(String pagePrefix) {
        this.pagePrefix = null == pagePrefix ? DEFAULT_PAGE_PREFIX : pagePrefix;
    }

    public void setSortPrefix(String sortPrefix) {
        this.sortPrefix = null == sortPrefix ? DEFAULT_SORT_PREFIX : sortPrefix;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        PageableDefaults pageableDefaults = getPageableDefaults(parameter);
        //默认的page request
        Pageable defaultPageRequest = getDefaultFromAnnotationOrFallback(pageableDefaults);

        String pageableNamePrefix = getPagePrefix(parameter);
        String sortNamePrefix = getSortPrefix(parameter);
        Map<String, String[]> pageableMap = getPrefixParameterMap(pageableNamePrefix, webRequest, true);
        Map<String, String[]> sortMap = getPrefixParameterMap(sortNamePrefix, webRequest, false);

        Sort sort = getSort(sortNamePrefix, sortMap, defaultPageRequest, webRequest);
        if (pageableMap.size() == 0) {
            return new PageRequest(defaultPageRequest.getPageNumber(), defaultPageRequest.getPageSize(), sort == null ? defaultPageRequest.getSort() : sort);
        }

        int pn = getPn(pageableMap, defaultPageRequest);
        int pageSize = getPageSize(pageableMap, defaultPageRequest);

        return new PageRequest(pn - 1, pageSize, sort);

    }

    private Sort getSort(String sortNamePrefix, Map<String, String[]> sortMap, Pageable defaultPageRequest, NativeWebRequest webRequest) {
        Sort sort = null;
        List<OrderedSort> orderedSortList = Lists.newArrayList();
        for (String name : sortMap.keySet()) {

            //sort1.abc
            int propertyIndex = name.indexOf(".") + 1;

            int order = 0;
            String orderStr = name.substring(sortNamePrefix.length(), propertyIndex - 1);
            try {
                if (!StringUtils.isEmpty(orderStr)) {
                    order = Integer.valueOf(orderStr);
                }
            } catch (Exception e) {
            }

            String property = name.substring(propertyIndex);
            assertSortProperty(property);
            Sort.Direction direction = Sort.Direction.fromString(sortMap.get(name)[0]);

            orderedSortList.add(new OrderedSort(property, direction, order));
        }

        Collections.sort(orderedSortList);
        for (OrderedSort orderedSort : orderedSortList) {
            Sort newSort = new Sort(orderedSort.direction, orderedSort.property);
            if (sort == null) {
                sort = newSort;
            } else {
                sort = sort.and(newSort);
            }
        }

        if (sort == null) {
            return defaultPageRequest.getSort();
        }

        return sort;
    }

    /**
     * 防止sql注入，排序字符串只能包含字符 数字 下划线 点 ` "
     *
     * @param property
     */
    private void assertSortProperty(String property) {
        if (!property.matches("[a-zA-Z0-9_、.`\"]*")) {
            throw new IllegalStateException("Sort property error, only contains [a-zA-Z0-9_.`\"]");
        }
    }

    private int getPageSize(Map<String, String[]> pageableMap, Pageable defaultPageRequest) {
        int pageSize = 0;
        try {
            String pageSizeStr = pageableMap.get("size")[0];
            if (pageSizeStr != null) {
                pageSize = Integer.valueOf(pageSizeStr);
            } else {
                pageSize = defaultPageRequest.getPageSize();
            }
        } catch (Exception e) {
            pageSize = defaultPageRequest.getPageSize();
        }

        if (pageSize < minPageSize) {
            pageSize = minPageSize;
        }

        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }
        return pageSize;
    }

    private int getPn(Map<String, String[]> pageableMap, Pageable defaultPageRequest) {
        int pn = 1;
        try {
            String pnStr = pageableMap.get("pn")[0];
            if (pnStr != null) {
                pn = Integer.valueOf(pnStr);
            } else {
                pn = defaultPageRequest.getPageNumber();
            }
        } catch (Exception e) {
            pn = defaultPageRequest.getPageNumber();
        }

        if (pn < 1) {
            pn = 1;
        }

        return pn;
    }


    /**
     * Resolves the prefix to use to bind properties from. Will prepend a possible {@link Qualifier} if available or
     * return the configured prefix otherwise.
     *
     * @param parameter
     * @return
     */
    private String getPagePrefix(MethodParameter parameter) {

        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

        if (qualifier != null) {
            return new StringBuilder(((Qualifier) qualifier).value()).append("_").append(pagePrefix).toString();
        }

        return pagePrefix;
    }


    private String getSortPrefix(MethodParameter parameter) {

        Qualifier qualifier = parameter.getParameterAnnotation(Qualifier.class);

        if (qualifier != null) {
            return new StringBuilder(qualifier.value()).append("_").append(sortPrefix).toString();
        }

        return sortPrefix;
    }


    private Pageable getDefaultFromAnnotationOrFallback(PageableDefaults pageableDefaults) {

        Pageable defaultPageable = defaultPageable(pageableDefaults);
        if (defaultPageable != null) {
            return defaultPageable;
        }

        return fallbackPagable;
    }

    private PageableDefaults getPageableDefaults(MethodParameter parameter) {
        //首先从参数上找
        PageableDefaults pageableDefaults = parameter.getParameterAnnotation(PageableDefaults.class);
        //找不到从方法上找
        if (pageableDefaults == null) {
            pageableDefaults = parameter.getMethodAnnotation(PageableDefaults.class);
        }
        return pageableDefaults;
    }

    private Pageable defaultPageable(PageableDefaults pageableDefaults) {

        if (pageableDefaults == null) {
            return null;
        }

        int pageNumber = pageableDefaults.pageNumber();
        int pageSize = pageableDefaults.value();

        String[] sortStrArray = pageableDefaults.sort();
        Sort sort = null;

        for (String sortStr : sortStrArray) {
            String[] sortStrPair = sortStr.split("=");
            Sort newSort = new Sort(Sort.Direction.fromString(sortStrPair[1]), sortStrPair[0]);
            if (sort == null) {
                sort = newSort;
            } else {
                sort = sort.and(newSort);
            }
        }
        return new PageRequest(pageNumber, pageSize, sort);
    }


    /**
     * Asserts uniqueness of all {@link Pageable} parameters of the method of the given {@link MethodParameter}.
     *
     * @param parameter
     */
    private void assertPageableUniqueness(MethodParameter parameter) {

        Method method = parameter.getMethod();

        if (containsMoreThanOnePageableParameter(method)) {
            Annotation[][] annotations = method.getParameterAnnotations();
            assertQualifiersFor(method.getParameterTypes(), annotations);
        }
    }

    /**
     * Returns whether the given {@link Method} has more than one {@link Pageable} parameter.
     *
     * @param method
     * @return
     */
    private boolean containsMoreThanOnePageableParameter(Method method) {

        boolean pageableFound = false;

        for (Class<?> type : method.getParameterTypes()) {

            if (pageableFound && type.equals(Pageable.class)) {
                return true;
            }

            if (type.equals(Pageable.class)) {
                pageableFound = true;
            }
        }

        return false;
    }

    /**
     * Asserts that every {@link Pageable} parameter of the given parameters carries an {@link org.springframework.beans.factory.annotation.Qualifier} annotation to
     * distinguish them from each other.
     *
     * @param parameterTypes
     * @param annotations
     */
    private void assertQualifiersFor(Class<?>[] parameterTypes, Annotation[][] annotations) {

        Set<String> values = new HashSet<String>();

        for (int i = 0; i < annotations.length; i++) {

            if (Pageable.class.equals(parameterTypes[i])) {

                Qualifier qualifier = findAnnotation(annotations[i]);

                if (null == qualifier) {
                    throw new IllegalStateException(
                            "Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
                }

                if (values.contains(qualifier.value())) {
                    throw new IllegalStateException("Values of the user Qualifiers must be unique!");
                }

                values.add(qualifier.value());
            }
        }
    }

    /**
     * Returns a {@link Qualifier} annotation from the given array of {@link Annotation}s. Returns {@literal null} if the
     * array does not contain a {@link Qualifier} annotation.
     *
     * @param annotations
     * @return
     */
    private Qualifier findAnnotation(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof Qualifier) {
                return (Qualifier) annotation;
            }
        }

        return null;
    }


    static class OrderedSort implements Comparable<OrderedSort> {
        private String property;
        private Sort.Direction direction;
        private int order = 0; //默认0 即无序

        OrderedSort(String property, Sort.Direction direction, int order) {
            this.property = property;
            this.direction = direction;
            this.order = order;
        }

        @Override
        public int compareTo(OrderedSort o) {
            if (o == null) {
                return -1;
            }
            if (this.order > o.order) {
                return 1;
            } else if (this.order < o.order) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
