/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.controller.permission;

import com.google.common.collect.Maps;
import com.sishuok.es.common.utils.MessageUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-7 下午2:37
 * <p>Version: 1.0
 */
public class PermissionList implements Serializable {

    public static final String CREATE_PERMISSION = "create";
    public static final String UPDATE_PERMISSION = "update";
    public static final String DELETE_PERMISSION = "delete";
    public static final String VIEW_PERMISSION = "view";

    /**
     * 资源前缀
     */
    private String resourceIdentity;

    /**
     * key:    权限
     * value : 资源
     */
    private Map<String, String> resourcePermissions = Maps.newHashMap();


    /**
     * 自动生成 create update delete 的权限串
     *
     * @param resourceIdentity
     * @return
     */
    public static PermissionList newPermissionList(String resourceIdentity) {

        PermissionList permissionList = new PermissionList();

        permissionList.resourceIdentity = resourceIdentity;

        permissionList.resourcePermissions.put(CREATE_PERMISSION, resourceIdentity + ":" + CREATE_PERMISSION);
        permissionList.resourcePermissions.put(UPDATE_PERMISSION, resourceIdentity + ":" + UPDATE_PERMISSION);
        permissionList.resourcePermissions.put(DELETE_PERMISSION, resourceIdentity + ":" + DELETE_PERMISSION);
        permissionList.resourcePermissions.put(VIEW_PERMISSION, resourceIdentity + ":" + VIEW_PERMISSION);

        return permissionList;
    }


    /**
     * 添加权限 自动生成如showcase:sample:permission
     *
     * @param permission
     */
    public void addPermission(String permission) {
        resourcePermissions.put(permission, resourceIdentity + ":" + permission);
    }


    public void assertHasCreatePermission() {
        assertHasPermission(CREATE_PERMISSION, "no.create.permission");
    }

    public void assertHasUpdatePermission() {
        assertHasPermission(UPDATE_PERMISSION, "no.update.permission");
    }

    public void assertHasDeletePermission() {
        assertHasPermission(DELETE_PERMISSION, "no.delete.permission");
    }


    public void assertHasViewPermission() {
        assertHasPermission(VIEW_PERMISSION, "no.view.permission");
    }

    /**
     * 即增删改中的任何一个即可
     */
    public void assertHasEditPermission() {
        assertHasAnyPermission(new String[]{
                CREATE_PERMISSION,
                UPDATE_PERMISSION,
                DELETE_PERMISSION
        });
    }

    public void assertHasPermission(String permission) {
        assertHasPermission(permission, null);
    }

    public void assertHasPermission(String permission, String errorCode) {
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }
        String resourcePermission = resourcePermissions.get(permission);
        if (resourcePermission == null) {
            resourcePermission = this.resourceIdentity + ":" + permission;
        }
        if (!SecurityUtils.getSubject().isPermitted(resourcePermission)) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourcePermission));
        }
    }

    public void assertHasAllPermission(String[] permissions) {
        assertHasAllPermission(permissions, null);
    }

    public void assertHasAllPermission(String[] permissions, String errorCode) {
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }

        if (permissions == null || permissions.length == 0) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
        }

        Subject subject = SecurityUtils.getSubject();

        for (String permission : permissions) {
            String resourcePermission = resourcePermissions.get(permission);
            if (resourcePermission == null) {
                resourcePermission = this.resourceIdentity + ":" + permission;
            }
            if (!subject.isPermitted(resourcePermission)) {
                throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
            }
        }

    }

    public void assertHasAnyPermission(String[] permissions) {
        assertHasAnyPermission(permissions, null);
    }

    public void assertHasAnyPermission(String[] permissions, String errorCode) {
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = getDefaultErrorCode();
        }
        if (permissions == null || permissions.length == 0) {
            throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
        }

        Subject subject = SecurityUtils.getSubject();

        for (String permission : permissions) {
            String resourcePermission = resourcePermissions.get(permission);
            if (resourcePermission == null) {
                resourcePermission = this.resourceIdentity + ":" + permission;
            }
            if (subject.isPermitted(resourcePermission)) {
                return;
            }
        }

        throw new UnauthorizedException(MessageUtils.message(errorCode, resourceIdentity + ":" + Arrays.toString(permissions)));
    }

    private String getDefaultErrorCode() {
        return "no.permission";
    }

}
