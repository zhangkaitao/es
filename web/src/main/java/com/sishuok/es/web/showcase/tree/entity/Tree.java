/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.tree.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.plugin.Treeable;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 上午9:38
 * <p>Version: 1.0
 */
@Entity
@Table(name = "tbl_tree")
public class Tree extends BaseEntity<Long> implements Treeable {

    /**
     * 标题
     */
    private String title;
    /**
     * 路径
     * 比如中国01 山东0101 河北0102
     */
    private String path;
    /**
     * 图标
     */
    private String icon;

    /**
     * 是否有叶子节点
     */
    @Formula(value = "(select count(*) from tbl_tree f_t where f_t.path like concat(path , '___'))")
    private boolean hasChildren;

    /**
     * 是否显示
     */
    @Column(name = "`show`")
    private Boolean show;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        if(!StringUtils.isEmpty(icon)) {
            return icon;
        }
        if(isRoot()) {
            return getRootDefaultIcon();
        }
        if(isLeaf()) {
            return getLeafDefaultIcon();
        }
        return getBranchDefaultIcon();
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getParentPath() {
        if(StringUtils.isEmpty(getPath())) {
            return "0";
        }
        int pathLength = getPath().length();
        //根
        if(pathLength == getPathLength()) {
            return "0";
        }

        return getPath().substring(0, pathLength - getPathLength());
    }

    @Override
    public boolean isRoot() {
        if(StringUtils.isEmpty(getPath())) {
            return false;
        }
        if (getPath().length() == getPathLength()) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isLeaf() {
        if(isRoot()) {
            return false;
        }
        if(isHasChildren()) {
            return false;
        }

        return true;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    /**
     * @see com.sishuok.es.common.entity.plugin.Treeable#getPathLength()
     * @return
     */
    @Override
    public int getPathLength() {
        return 3;
    }

    /**
     * 匹配儿子节点的后缀
     * @return
     */
    public String getChildPathSuffix() {
        return StringUtils.repeat("_", getPathLength());
    }

    /**
     * 根节点默认图标 如果没有默认 空即可
     * @return
     */
    @Override
    public String getRootDefaultIcon() {
        return "static/comp/zTree/css/zTreeStyle/img/diy/root.png";
    }

    /**
     * 树枝节点默认图标 如果没有默认 空即可
     * @return
     */
    @Override
    public String getBranchDefaultIcon() {
        return "static/comp/zTree/css/zTreeStyle/img/diy/branch.png";
    }

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getLeafDefaultIcon() {
        return "static/comp/zTree/css/zTreeStyle/img/diy/leaf.png";
    }
}
