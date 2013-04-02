/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.exception;

import com.sishuok.es.common.exception.BaseException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-31 上午10:46
 * <p>Version: 1.0
 */
public class SystemableException extends BaseException {
    public SystemableException() {
        super("entity.systemed.error", new Object[0]);
    }
}
