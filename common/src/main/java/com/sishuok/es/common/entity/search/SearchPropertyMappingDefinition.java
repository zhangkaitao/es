/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.es.common.entity.search.annotation.SearchPropertyMapping;
import com.sishuok.es.common.entity.search.annotation.SearchPropertyMappings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     自定义查询字段的映射定义信息<br/>
 *     @see com.sishuok.es.common.entity.search.annotation.SearchPropertyMappings
 * </p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 下午6:15
 * <p>Version: 1.0
 */
public final class SearchPropertyMappingDefinition {

    public static final SearchPropertyMappingDefinition EMPTY = newInstance(null);

    private Map<String, SearchPropertyMappingInfo> mappingInfoMap = Maps.newHashMap();

    private SearchPropertyMappingDefinition() {
    }

    public static SearchPropertyMappingDefinition newInstance(Class<?> entityClass) {

        SearchPropertyMappingDefinition definition = new SearchPropertyMappingDefinition();

        if(entityClass == null) {
            return definition;
        }
        SearchPropertyMappings mappings = AnnotationUtils.findAnnotation(entityClass, SearchPropertyMappings.class);
        if(mappings == null) {
            return definition;
        }

        for (SearchPropertyMapping mapping : mappings.value()) {
            definition.addSearchPropertyMappingInfo(
                    mapping.searchProperty(),
                    mapping.entityProperty(),
                    trimAndlowercase(mapping.defaultOperator()));
        }
        return definition;
    }

    private static String trimAndlowercase(String str) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        return str.trim().toLowerCase();
    }

    public final void addSearchPropertyMappingInfo(String searchProperty, String entityProperty, String defaultOperator) {
        mappingInfoMap.put(
                searchProperty,
                new SearchPropertyMappingInfo(
                        searchProperty,
                        entityProperty,
                        trimAndlowercase(defaultOperator)));
    }

    public boolean containsSearchPropertyMapping(String searchProperty) {
        return mappingInfoMap.containsKey(searchProperty);
    }

    public SearchPropertyMappingInfo getSearchPropertyMapping(String searchProperty) {
        return mappingInfoMap.get(searchProperty);
    }

    public Map<String, SearchPropertyMappingInfo> getMappingInfoMap() {
        return mappingInfoMap;
    }

    public static class SearchPropertyMappingInfo {
        private final String searchProperty;
        private final String entityProperty;
        private final String defaultOperator;

        public SearchPropertyMappingInfo(String searchProperty, String entityProperty, String defaultOperator) {
            this.searchProperty = searchProperty;
            this.entityProperty = entityProperty;
            this.defaultOperator = defaultOperator;
        }

        public String getSearchProperty() {
            return searchProperty;
        }

        public String getEntityProperty() {
            return entityProperty;
        }

        public String getDefaultOperator() {
            return defaultOperator;
        }

        @Override
        public String toString() {
            return "SearchPropertyMappingInfo{" +
                    "searchProperty='" + searchProperty + '\'' +
                    ", entityProperty='" + entityProperty + '\'' +
                    ", defaultOperator='" + defaultOperator + '\'' +
                    '}';
        }
    }
}
