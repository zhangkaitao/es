/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.task.web.controller;

import com.google.common.collect.Lists;
import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import com.sishuok.es.common.web.validate.ValidateResponse;
import com.sishuok.es.maintain.icon.entity.Icon;
import com.sishuok.es.maintain.icon.entity.IconType;
import com.sishuok.es.maintain.icon.service.IconService;
import com.sishuok.es.maintain.task.entity.DynamicTask;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/maintain/dynamicTask")
public class DynamicTaskController extends BaseCRUDController<DynamicTask, Long> {


}
