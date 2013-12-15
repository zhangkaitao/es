/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.move.web.controller;

import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.validate.group.Create;
import com.sishuok.es.common.plugin.web.controller.BaseMovableController;
import com.sishuok.es.showcase.move.entity.Move;
import com.sishuok.es.showcase.move.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/move")
public class MoveController extends BaseMovableController<Move, Long> {


    public MoveController() {
        setResourceIdentity("showcase:move");
    }

    public void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    @Override
    public String create(Model model,
                         //表示只验证Create.class分组
                         @Validated(value = Create.class) @Valid Move move, BindingResult result,
                         RedirectAttributes redirectAttributes) {
        return super.create(model, move, result, redirectAttributes);
    }

}
