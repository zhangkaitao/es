/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.resource.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.plugin.entity.Movable;
import com.sishuok.es.common.repository.support.annotation.EnableQueryCache;
import com.sishuok.es.sys.user.entity.User;

/**
 * <p>User: xxs
 * <p>Date: 2015-03-14 14:48
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_resource_shortcut")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceShortcut extends BaseEntity<Long> implements Movable {
	
	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true)
    @Fetch(FetchMode.SELECT)
    private Resource resource;
    
    @OneToOne(optional = true)
    @Fetch(FetchMode.SELECT)
    private User user;

    @Column(name = "weight")
    private Integer weight;

    public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
