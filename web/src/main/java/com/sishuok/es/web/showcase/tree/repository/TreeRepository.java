/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.tree.repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.web.showcase.tree.entity.Tree;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface TreeRepository extends BaseRepository<Tree, Long> {

    Tree findByPath(String targetPath);

    @Modifying
    @Query(value = "delete from Tree where id in (?1)")
    void deleteByIds(List<Long> ids);

    @Modifying
    @Query(value = "delete from Tree where path like concat(?1, '%')")
    void deleteSelfAndChild(String path);

    @Query(value = "select max(path) from Tree where path like concat(?1, ?2)")
    String findMaxChildPath(String parentPath, String childPathSuffix);


    /**
     * 把源节点全部变更为目标节点
     * @param sourcePath
     * @param nextTargetChildPath
     */
    @Modifying
    @Query(value = "update Tree set path = ?2 || substr(path, length(?1)+1) where path like concat(?1, '%')")
    void updateSelftAndChild(String sourcePath, String nextTargetChildPath);

    /**
     * 查找目标节点及之后的兄弟
     * @param selfPath
     * @param childPathSuffix
     * @return
     */
    @Query(value = "from Tree where path >= ?1 and path like concat(substr(?1, 1, length(?1) - 3), ?2) order by path asc")
    List<Tree> findSelfAndNextSiblings(String selfPath, String childPathSuffix);


}
