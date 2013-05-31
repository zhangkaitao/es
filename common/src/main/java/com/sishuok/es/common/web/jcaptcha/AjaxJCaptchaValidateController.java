/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.jcaptcha;

import com.sishuok.es.common.web.validate.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * jcaptcha 验证码验证
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-22 下午5:17
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/jcaptcha-validate")
public class AjaxJCaptchaValidateController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object jqueryValidationEngineValidate(
            HttpServletRequest request,
            @RequestParam(value = "fieldId", required = false) String fieldId,
            @RequestParam(value = "fieldValue", required = false) String fieldValue) {

        ValidateResponse response = ValidateResponse.newInstance();

        if (JCaptcha.hasCaptcha(request, fieldValue) == false) {
            response.validateFail(fieldId, messageSource.getMessage("jcaptcha.validate.error", null, null));
        } else {
            response.validateSuccess(fieldId, messageSource.getMessage("jcaptcha.validate.success", null, null));
        }

        return response.result();
    }
}
