/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.exception.web;

import com.sishuok.es.common.exception.web.entity.ExceptionResponse;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-7 下午3:38
 * <p>Version: 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    private ContentNegotiationManager contentNegotiationManager;

    public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
        this.contentNegotiationManager = contentNegotiationManager;
    }

    //没有权限 异常
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthenticatedException e) {
        ModelAndView mv = commonProcess(request, e);
        mv.setViewName("error/unauthenticated");
        return mv;
    }
    private ModelAndView commonProcess(NativeWebRequest request, Exception e) {
        MediaType mediaType = MediaType.TEXT_HTML;
        try {
            List<MediaType> mediaTypeList = contentNegotiationManager.resolveMediaTypes(request);

            if(mediaTypeList.contains(MediaType.APPLICATION_JSON)) { //返回josn数据
                mediaType = MediaType.APPLICATION_JSON;
            }

            if(mediaTypeList.contains(MediaType.APPLICATION_XML)) { // 返回xml数据
                mediaType = MediaType.APPLICATION_XML;
            }
        } catch (HttpMediaTypeNotAcceptableException e1) {
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.from(e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("error", exceptionResponse);

        if(mediaType == MediaType.APPLICATION_JSON) {
            mv.setView(new MappingJackson2JsonView());
            mv.addObject("error", exceptionResponse);
            return mv;
        }
        if(mediaType == MediaType.APPLICATION_JSON) {
            mv.setView(new MarshallingView());
            return mv;
        }

        mv.setViewName("error/exception");
        return mv;
    }
}
