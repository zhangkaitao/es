/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatusHistory;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.repository.SysUserStatusHistoryRepository;
import com.sishuok.es.web.admin.sys.user.repository.SysUserRepository;
import org.hibernate.criterion.Order;
import org.hibernate.ejb.criteria.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class SysUserStatusHistoryService extends BaseService<SysUserStatusHistory, Long> {

    private SysUserStatusHistoryRepository sysUserStatusHistoryRepository;

    @Autowired
    public void setSysUserStatusHistoryRepository(SysUserStatusHistoryRepository sysUserStatusHistoryRepository) {
        setBaseRepository(sysUserStatusHistoryRepository);
        this.sysUserStatusHistoryRepository = sysUserStatusHistoryRepository;
    }


    public void log(SysUser opUser, SysUser sysUser, SysUserStatus newStatus, String reason) {
        SysUserStatusHistory history = new SysUserStatusHistory();
        history.setUser(sysUser);
        history.setOpUser(opUser);
        history.setOpDate(new Date());
        history.setStatus(newStatus);
        history.setReason(reason);
        save(history);
    }

    public SysUserStatusHistory findLastHistory(final SysUser sysUser) {
        Specification<SysUserStatusHistory> specification = new Specification<SysUserStatusHistory>(){
            @Override
            public Predicate toPredicate(
                    Root<SysUserStatusHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                query.where(cb.equal(root.get("user"), sysUser));
                query.orderBy(cb.desc(root.get("opDate")));
                return query.getRestriction();
            }
        };
        Page<SysUserStatusHistory> page =
                sysUserStatusHistoryRepository.findAll(specification, new PageRequest(0, 1));

        if(page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }
}
