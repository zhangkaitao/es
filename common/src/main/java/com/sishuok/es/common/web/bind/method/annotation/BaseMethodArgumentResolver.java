/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.bind.method.annotation;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-23 下午3:28
 * <p>Version: 1.0
 */
public abstract class BaseMethodArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     * 获取指定前缀的参数：包括uri varaibles 和 parameters
     *
     * @param namePrefix
     * @param request
     * @return
     * @subPrefix 是否截取掉namePrefix的前缀
     */
    protected Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix) {
        Map<String, String[]> result = new HashMap<String, String[]>();

        Map<String, String> variables = getUriTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
            if (name.startsWith(namePrefix)) {

                //page.pn  则截取 pn
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), new String[]{variables.get(name)});
                } else {
                    result.put(name, new String[]{variables.get(name)});
                }
            }
        }

        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            if (name.startsWith(namePrefix)) {
                //page.pn  则截取 pn
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), request.getParameterValues(name));
                } else {
                    result.put(name, request.getParameterValues(name));
                }
            }
        }

        return result;
    }

    private boolean illegalChar(char ch) {
        return ch != '.' && ch != '_' && !(ch >= '0' && ch <= '9');
    }


    @SuppressWarnings("unchecked")
    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables =
                (Map<String, String>) request.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (variables != null) ? variables : Collections.<String, String>emptyMap();
    }


}
