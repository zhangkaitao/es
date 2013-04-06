package com.sishuok.es.sys.organization.entity;

/**
 * 组织机构类型
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午1:53
 * <p>Version: 1.0
 */
public enum OrganizationType {
    bloc("集团"), branch_office("分公司"), department("部门"), group("部门小组");

    private final String info;

    private OrganizationType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
