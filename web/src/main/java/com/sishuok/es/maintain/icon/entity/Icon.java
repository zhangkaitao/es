/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.icon.entity;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * 图标管理
 * class方式 和
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "maintain_icon")
public class Icon extends BaseEntity<Long> {

    /**
     * 标识 前台使用时的名称
     */
    private String identity;
    /**
     * 类名称
     */
    @Column(name = "css_class")
    private String cssClass;

    //////和 class、css sprite 三者选一
    /**
     * 图片地址
     */
    @Column(name = "img_src")
    private String imgSrc;

    //////和 class、css sprite 三者选一
    /**
     * css 背景图 位置
     * 绝对地址  如http://a.com/a.png
     * 相对于上下文地址 如/a/a.png 不加上下文
     */
    @Column(name = "sprite_src")
    private String spriteSrc;

    /**
     * 距离sprite图片左边多少
     */
    @Column(name = "left")
    private Integer left;

    /**
     * 距离sprite图片上边多少
     */
    @Column(name = "top")
    private Integer top;


    /**
     * 宽度
     */
    private Integer width;
    /**
     * 高度
     */
    private Integer height;

    /**
     * 额外添加的css样式
     */
    private String style = "";

    @Enumerated(EnumType.STRING)
    private IconType type;

    /**
     * 描述
     */
    private String description;


    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSpriteSrc() {
        return spriteSrc;
    }

    public void setSpriteSrc(String spriteSrc) {
        this.spriteSrc = spriteSrc;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public IconType getType() {
        return type;
    }

    public void setType(IconType type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
