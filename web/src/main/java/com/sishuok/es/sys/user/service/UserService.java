/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.exception.UserBlockedException;
import com.sishuok.es.sys.user.exception.UserNotExistsException;
import com.sishuok.es.sys.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
@DependsOn(value = {"userRepository", "userLastOnlineInfoRepository", "userStatusHistoryRepository"})
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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User findByMobilePhoneNumber(String mobilePhoneNumber) {
        return userRepository.findByMobilePhoneNumber(mobilePhoneNumber);
    }
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        update(user);
    }

    public void changeStatus(User user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus);
        update(user);
        //TODO 修改为当前登录人
        User opUser = user;
        userStatusHistoryService.log(opUser, user, newStatus, reason);
    }

    public User login(String username, String password) {

        User user = findByUsername(username);

        if(user == null) {
            user = findByEmail(username);
        }

        if(user == null) {
            user = findByMobilePhoneNumber(username);
        }

        if(user == null || Boolean.TRUE.equals(user.getDeleted())) {
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if(user.getStatus() == UserStatus.blocked) {
            throw new UserBlockedException(userStatusHistoryService.findLastHistory(user).getReason());
        }
        return user;
    }

}
