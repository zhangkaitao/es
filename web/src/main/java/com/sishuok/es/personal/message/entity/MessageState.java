package com.sishuok.es.personal.message.entity;

/**
 * 消息状态
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:01
 * <p>Version: 1.0
 */
public enum MessageState {

    /**
     * 在收件箱,365天后状态改成垃圾箱
     */
    in_box("收件箱"),
    /**
     * 在发件箱,365天后状态改成垃圾箱
     */
    out_box("发件箱"),
    /**
     * 在收藏箱,不允许删除
     */
    store_box("收藏箱"),
    /**
     * 在垃圾箱,30天后状态改成已删除
     */
    trash_box("垃圾箱"),
    /**
     * 在草稿箱,永久不删除
     */
    draft_box("草稿箱"),
    /**
     * 邮件删除了，只有收件人和发件人都删除了，才能真正删除
     */
    delete_box("已删除");

    private final String info;

    private MessageState(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
