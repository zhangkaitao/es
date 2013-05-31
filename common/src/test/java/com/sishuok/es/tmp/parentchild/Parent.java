/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.tmp.parentchild;

import com.google.common.collect.Sets;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-8 下午2:56
 * <p>Version: 1.0
 */
@Entity
@Table(name = "parent")
public class Parent {

    @Id
    private String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Child.class, mappedBy = "parent", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Child> childs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Child> getChilds() {
        if (childs == null) {
            childs = Sets.newHashSet();
        }
        return childs;
    }

    public void setChilds(Set<Child> childs) {
        this.childs = childs;
    }

    public void addChild(Child c) {
        c.setParent(this);
        getChilds().add(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parent parent = (Parent) o;

        if (id != null ? !id.equals(parent.id) : parent.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
