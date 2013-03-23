/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatusHistory;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.repository.UserStatusHistoryRepository;
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
public class UserStatusHistoryService extends BaseService<UserStatusHistory, Long> {

    private UserStatusHistoryRepository userStatusHistoryRepository;

    @Autowired
    public void setUserStatusHistoryRepository(UserStatusHistoryRepository userStatusHistoryRepository) {
        setBaseRepository(userStatusHistoryRepository);
        this.userStatusHistoryRepository = userStatusHistoryRepository;
    }


    public void log(User opUser, User user, UserStatus newStatus, String reason) {
        UserStatusHistory history = new UserStatusHistory();
        history.setUser(user);
        history.setOpUser(opUser);
        history.setOpDate(new Date());
        history.setStatus(newStatus);
        history.setReason(reason);
        save(history);
    }

    public UserStatusHistory findLastHistory(final User user) {
        Specification<UserStatusHistory> specification = new Specification<UserStatusHistory>(){
            @Override
            public Predicate toPredicate(
                    Root<UserStatusHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                query.where(cb.equal(root.get("user"), user));
                query.orderBy(cb.desc(root.get("opDate")));
                return query.getRestriction();
            }
        };
        Page<UserStatusHistory> page = userStatusHistoryRepository.findAll(specification, new PageRequest(0, 1));

        if(page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    public String getLastReason(User user) {
        UserStatusHistory history = findLastHistory(user);
        if(history == null) {
            return "";
        }
        return history.getReason();
    }
}
