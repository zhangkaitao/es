/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.exception;

import com.sishuok.es.common.exception.BaseException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午8:19
 * <p>Version: 1.0
 */
public class SysUserException extends BaseException {

    public SysUserException(String code, Object[] args) {
        super("sys.sysUser", code, args, null);
    }

}
