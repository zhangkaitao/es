/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.web.session.mgt;

import com.google.common.collect.Lists;
import com.sishuok.es.sys.user.entity.UserOnline;
import com.sishuok.es.sys.user.service.UserOnlineService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.ShiroConstants;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.SessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * 为OnlineSession定制的Web Session Manager
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao同步
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-21 下午2:28
 * <p>Version: 1.0
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {

    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    @Autowired
    private UserOnlineService userOnlineService;

    public void setUserOnlineService(UserOnlineService userOnlineService) {
        this.userOnlineService = userOnlineService;
    }

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
        super.setAttribute(sessionKey, attributeKey, value);
        if(value != null) {
            OnlineSession s = (OnlineSession) doGetSession(sessionKey);
            s.markAttributeChanged();
        }
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
        Object removed = super.removeAttribute(sessionKey, attributeKey);
        if(removed != null) {
            OnlineSession s = (OnlineSession) doGetSession(sessionKey);
            s.markAttributeChanged();
        }

        return removed;
    }

    /**
     * 验证session是否有效 用于删除过期session
     */
    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()) {
            log.info("invalidation sessions...");
        }

        int invalidCount = 0;

        int timeout = (int)getGlobalSessionTimeout();
        Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        PageRequest pageRequest = new PageRequest(0, 100);
        Page<UserOnline> page = userOnlineService.findExpiredUserOnlineList(expiredDate, pageRequest);

        List<String> needOfflineIdList = Lists.newArrayList();
        //改成批量过期删除
        while (page.hasContent()) {
            for(UserOnline userOnline : page.getContent()) {
                try {
                    SessionKey key = new DefaultSessionKey(userOnline.getId());
                    Session session = retrieveSession(key);
                    //仅从cache中删除 db的批量删除
                    if(session != null) {
                        session.setAttribute(ShiroConstants.ONLY_CLEAR_CACHE, true);
                    }
                    validate(session, key);
                } catch (InvalidSessionException e) {
                    if (log.isDebugEnabled()) {
                        boolean expired = (e instanceof ExpiredSessionException);
                        String msg = "Invalidated session with id [" + userOnline.getId() + "]" +
                                (expired ? " (expired)" : " (stopped)");
                        log.debug(msg);
                    }
                    invalidCount ++;
                    needOfflineIdList.add(userOnline.getId());
                }

                if(needOfflineIdList.size() > 0) {
                    try {
                        userOnlineService.batchOffline(needOfflineIdList);
                    } catch (Exception e) {
                        log.error("batch delete db session error.", e);
                    }
                }
            }

            pageRequest = new PageRequest((pageRequest.getPageNumber() + 1) * pageRequest.getPageSize(), pageRequest.getPageSize());
            page = userOnlineService.findExpiredUserOnlineList(expiredDate, pageRequest);
        }


        if (log.isInfoEnabled()) {
            String msg = "Finished invalidation session.";
            if (invalidCount > 0) {
                msg += "  [" + invalidCount + "] sessions were stopped.";
            } else {
                msg += "  No sessions were stopped.";
            }
            log.info(msg);
        }

    }

    @Override
    protected Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException("getActiveSessions method not supported");
    }
}
