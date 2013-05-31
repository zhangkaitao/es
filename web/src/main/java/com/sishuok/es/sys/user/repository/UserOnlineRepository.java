/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.sys.user.entity.UserOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface UserOnlineRepository extends BaseRepository<UserOnline, String> {

    @Query("from UserOnline o where o.lastAccessTime < ?1 order by o.lastAccessTime asc")
    Page<UserOnline> findExpiredUserOnlineList(Date expiredDate, Pageable pageable);

    @Modifying
    @Query("delete from UserOnline o where o.id in (?1)")
    void batchDelete(List<String> needExpiredIdList);
}
