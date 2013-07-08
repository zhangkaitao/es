package com.sishuok.es.personal.message.entity;

/**
 * 消息类型
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午1:59
 * <p>Version: 1.0
 */
public enum MessageType {
    user_message("普通消息"),
    system_message("系统消息");

    private final String info;

    private MessageType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    

}
