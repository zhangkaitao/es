/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.shiro.realm;

import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.exception.SysUserBlockedException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserNotExistsException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordNotMatchException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordRetryLimitExceedException;
import com.sishuok.es.web.admin.sys.user.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:05
 * <p>Version: 1.0
 */
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        try {
//            sysUserService.login(username, password);
        } catch (SysUserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (SysUserPasswordNotMatchException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (SysUserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (SysUserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
        return info;
    }

}
