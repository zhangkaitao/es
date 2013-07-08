/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.organization.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.repository.support.annotation.EnableQueryCache;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

/**
 * 组织机构树
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 上午9:38
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_organization")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization extends BaseEntity<Long> implements Treeable<Long> {

    /**
     * 标题
     */
    private String name;

    /**
     * 组织机构类型 默认 分公司
     */
    @Enumerated(EnumType.STRING)
    private OrganizationType type = OrganizationType.branch_office;
    /**
     * 父路径
     */
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "parent_ids")
    private String parentIds;

    private Integer weight;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否有叶子节点
     */
    @Formula(value = "(select count(*) from sys_organization f_t where f_t.parent_id = id)")
    private boolean hasChildren;

    /**
     * 是否显示
     */
    @Column(name = "is_show")
    private Boolean show = Boolean.FALSE;

    public Organization() {
    }

    public Organization(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public String makeSelfAsNewParentIds() {
        return getParentIds() + getId() + getSeparator();
    }

    @Override
    public String getSeparator() {
        return "/";
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getIcon() {
        if (!StringUtils.isEmpty(icon)) {
            return icon;
        }
        if (isRoot()) {
            return getRootDefaultIcon();
        }
        if (isLeaf()) {
            return getLeafDefaultIcon();
        }
        return getBranchDefaultIcon();
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    @Override
    public boolean isRoot() {
        if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isLeaf() {
        if (isRoot()) {
            return false;
        }
        if (isHasChildren()) {
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
     * 根节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getRootDefaultIcon() {
        return "ztree_root_open";
    }

    /**
     * 树枝节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getBranchDefaultIcon() {
        return "ztree_branch";
    }

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getLeafDefaultIcon() {
        return "ztree_leaf";
    }

}
