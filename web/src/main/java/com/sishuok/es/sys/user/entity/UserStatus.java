package com.sishuok.es.sys.user.entity;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:19
 * <p>Version: 1.0
 */
public enum UserStatus {

    normal("正常状态"), blocked("封禁状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
