/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.service.PasswordService;
import com.sishuok.es.sys.user.service.UserLastOnlineService;
import com.sishuok.es.sys.user.service.UserService;
import com.sishuok.es.sys.user.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 登录用户的个人信息
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-30 下午2:00
 * <p>Version: 1.0
 */
@Controller()
@RequestMapping("/admin/sys/user/loginUser")
public class LoginUserController extends BaseController<User, Long> {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserLastOnlineService userLastOnlineService;


    @Override
    public void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
        model.addAttribute("statusList", UserStatus.values());
    }

    @RequestMapping("/viewInfo")
    public String viewInfo(@CurrentUser User user, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "查看个人资料");
        user = userService.findOne(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("lastOnline", userLastOnlineService.findByUserId(user.getId()));
        return viewName("editForm");
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.GET)
    public String updateInfoForm(@CurrentUser User user, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改个人资料");
        model.addAttribute("user", user);
        return viewName("editForm");
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public String updateInfo(
            @CurrentUser User user,
            @RequestParam("email") String email,
            @RequestParam("mobilePhoneNumber") String mobilePhoneNumber,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (email == null || !email.matches(User.EMAIL_PATTERN)) {
            model.addAttribute(Constants.ERROR, "请输入正确的邮箱地址");
            return updateInfoForm(user, model);
        }

        if (mobilePhoneNumber == null || !mobilePhoneNumber.matches(User.MOBILE_PHONE_NUMBER_PATTERN)) {
            model.addAttribute(Constants.ERROR, "请输入正确的手机号");
            return updateInfoForm(user, model);
        }

        User emailDbUser = userService.findByEmail(email);
        if (emailDbUser != null && !emailDbUser.equals(user)) {
            model.addAttribute(Constants.ERROR, "邮箱地址已经被其他人使用，请换一个");
            return updateInfoForm(user, model);
        }

        User mobilePhoneNumberDbUser = userService.findByMobilePhoneNumber(mobilePhoneNumber);
        if (mobilePhoneNumberDbUser != null && !mobilePhoneNumberDbUser.equals(user)) {
            model.addAttribute(Constants.ERROR, "手机号已经被其他人使用，请换一个");
            return updateInfoForm(user, model);
        }

        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        userService.update(user);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改个人资料成功");

        return redirectToUrl(viewName("updateInfo"));

    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePasswordForm(@CurrentUser User user, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改密码");
        model.addAttribute("user", user);
        return viewName("changePasswordForm");
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(
            @CurrentUser User user,
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam(value = "newPassword1") String newPassword1,
            @RequestParam(value = "newPassword2") String newPassword2,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!passwordService.matches(user, oldPassword)) {
            model.addAttribute(Constants.ERROR, "旧密码不正确");
            return changePasswordForm(user, model);
        }

        if (StringUtils.isEmpty(newPassword1) || StringUtils.isEmpty(newPassword2)) {
            model.addAttribute(Constants.ERROR, "必须输入新密码");
            return changePasswordForm(user, model);
        }

        if (!newPassword1.equals(newPassword2)) {
            model.addAttribute(Constants.ERROR, "两次输入的密码不一致");
            return changePasswordForm(user, model);
        }

        userService.changePassword(user, newPassword1);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改密码成功");
        return redirectToUrl(viewName("changePassword"));
    }


}
