/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.web.controller;

import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.service.MessageApi;
import com.sishuok.es.personal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:40
 * <p>Version: 1.0
 */
public class MessageController extends BaseController<Message, Long> {
    @Autowired
    @BaseComponent
    MessageService messageService;

    @Autowired
    private MessageApi messageApi;


}
