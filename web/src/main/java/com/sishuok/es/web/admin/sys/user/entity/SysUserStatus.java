package com.sishuok.es.web.admin.sys.user.entity;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:19
 * <p>Version: 1.0
 */
public enum SysUserStatus {

    normal("正常状态"), blocked("锁定状态");

    private final String info;

    private SysUserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
