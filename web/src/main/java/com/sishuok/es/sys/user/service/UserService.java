/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserOrganization;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.entity.UserStatusHistory;
import com.sishuok.es.sys.user.exception.UserBlockedException;
import com.sishuok.es.sys.user.exception.UserNotExistsException;
import com.sishuok.es.sys.user.exception.UserPasswordNotMatchException;
import com.sishuok.es.sys.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
@DependsOn(value = {"userRepository", "userOnlineRepository", "userLastOnlineRepository", "userStatusHistoryRepository"})
public class UserService extends BaseService<User, Long> {

    private static final Logger log = LoggerFactory.getLogger("es-sys-user");

    private UserRepository userRepository;
    private UserStatusHistoryService userStatusHistoryService;
    private PasswordService passwordService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        setBaseRepository(userRepository);
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserStatusHistoryService(UserStatusHistoryService userStatusHistoryService) {
        this.userStatusHistoryService = userStatusHistoryService;
    }
    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public User save(User user) {
        if(user.getCreateDate() == null) {
            user.setCreateDate(new Date());
        }
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));


        return super.save(user);
    }


    @Override
    public User update(User user) {
        for(UserOrganization userOrganization : user.getOrganizations()) {
            UserOrganization dbUserOrganization = findUserOrganization(userOrganization);
            if(dbUserOrganization != null) {
                userOrganization.setId(dbUserOrganization.getId());
            }
        }

        super.update(user);

        return user;
    }

    public UserOrganization findUserOrganization(UserOrganization userOrganization) {
        return userRepository.findUserOrganization(userOrganization.getUser(), userOrganization.getOrganization());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User findByMobilePhoneNumber(String mobilePhoneNumber) {
        return userRepository.findByMobilePhoneNumber(mobilePhoneNumber);
    }



    public User changePassword(User user, String newPassword) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        update(user);
        return user;
    }

    public User changeStatus(User opUser, User user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus);
        update(user);
        userStatusHistoryService.log(opUser, user, newStatus, reason);
        return user;
    }

    public User login(String username, String password) {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        //密码如果不在指定范围内 肯定错误
        if(password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }

        User user = null;

        UserService proxyUserService = (UserService) AopContext.currentProxy();
        if(maybeUsername(username)) {
            user = proxyUserService.findByUsername(username);
        }

        if(user == null && maybeEmail(username)) {
            user = proxyUserService.findByEmail(username);
        }

        if(user == null && maybeMobilePhoneNumber(username)) {
            user = proxyUserService.findByMobilePhoneNumber(username);
        }

        if(user == null || Boolean.TRUE.equals(user.getDeleted())) {
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if(user.getStatus() == UserStatus.blocked) {
            throw new UserBlockedException(userStatusHistoryService.getLastReason(user));
        }
        return user;
    }


    private boolean maybeUsername(String username) {
        if(!username.matches(User.USERNAME_PATTERN)) {
            return false;
        }
        //如果用户名不在指定范围内也是错误的
        if(username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
            return false;
        }

        return true;
    }

    private boolean maybeEmail(String username) {
        if(!username.matches(User.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if(!username.matches(User.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    public void changePassword(Long[] ids, String newPassword) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for(Long id : ids) {
            proxyUserService.changePassword(findOne(id), newPassword);
        }
    }

    public void changeStatus(User opUser, Long[] ids, UserStatus newStatus, String reason) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for(Long id : ids) {
            proxyUserService.changeStatus(opUser, findOne(id), newStatus, reason);
        }
    }
}
