/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.serivce;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.plugin.entity.Movable;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.common.repository.BaseRepositoryImpl;
import com.sishuok.es.common.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-22 下午5:26
 * <p>Version: 1.0
 */
public abstract class BaseTreeableService<M extends BaseEntity & Treeable, ID extends Serializable> extends BaseService<M, ID> {

    private BaseRepositoryImpl<M, ID> baseRepositoryImpl;

    private final String DELETE_SELF_AND_CHILD_QL;
    private final String FIND_MAX_CHILD_PATH_QL;
    private final String FIND_BY_PATH_QL;
    private final String UPDATE_SELF_AND_CHILD_QL;
    private final String FIND_SELF_AND_NEXT_SIBLINGS_QL;

    protected <R extends BaseRepository<M, ID>> BaseTreeableService() {
        baseRepositoryImpl = BaseRepositoryImpl.<M, ID>defaultBaseRepositoryImpl(entityClass);
        String entityName = this.entityClass.getSimpleName();

        DELETE_SELF_AND_CHILD_QL = String.format("delete from %s where path like concat(?1, %s)", entityName, "'%'");
        FIND_MAX_CHILD_PATH_QL = String.format("select max(path) from %s where path like concat(?1, ?2)", entityName);
        FIND_BY_PATH_QL = String.format("from %s where path = ?1", entityName);
        UPDATE_SELF_AND_CHILD_QL =
                String.format("update %s set path = ?2 || substr(path, length(?1)+1) where path like concat(?1, %s)", entityName, "'%'");
        FIND_SELF_AND_NEXT_SIBLINGS_QL =
                String.format("from %s where path >= ?1 and path like concat(substr(?1, 1, length(?1) - 3), ?2) order by path asc", entityName);
    }

    @Transactional
    public void deleteSelfAndChild(M m) {
        baseRepositoryImpl.batchUpdate(DELETE_SELF_AND_CHILD_QL, m.getPath());
    }

    @Transactional
    public void appendChild(M parent, M child) {
        String nextChildPath = nextChildPath(parent.getPath(), parent.getChildPathSuffix());
        child.setPath(nextChildPath);
        save(child);
    }

    private String nextChildPath(String parentPath, String childPathSuffix) {
        String maxChildPath = baseRepositoryImpl.findOne(FIND_MAX_CHILD_PATH_QL, parentPath, childPathSuffix);
        if(StringUtils.isEmpty(maxChildPath)) {
            return parentPath + String.format("%0" + childPathSuffix.length() + "d", 1);
        }
        return String.format("%0" + maxChildPath.length() + "d", Long.valueOf(maxChildPath) + 1);
    }
    
    public M findByPath(String path) {
        return baseRepositoryImpl.findOne(FIND_BY_PATH_QL, path);
    }

    /**
     * 移动节点
     * 根节点不能移动
     * @param source 源节点
     * @param targetPath 目标节点
     * @param moveType 位置
     */
    @Transactional
    public void move(M source, String targetPath, String moveType) {
        M target = findByPath(targetPath);
        if(source == null || target == null || source.isRoot() || target.isRoot()) { //根节点不能移动
            return;
        }

        //移动到目标节点之后
        if ("next".equals(moveType)) {
            List<M> siblings = findSelfAndNextSiblings(target.getPath(), target.getChildPathSuffix());
            siblings.remove(0);//把自己移除

            if(siblings.size() == 0) { //如果没有兄弟了 则直接把源的设置为目标即可
                String nextTargetChildPath = nextChildPath(target.getParentPath(), target.getChildPathSuffix());
                updateSelftAndChild(source.getPath(), nextTargetChildPath);
                return;
            } else {
                moveType = "prev";
                target = siblings.get(0); //否则，相当于插入到实际目标节点下一个节点之前
            }
        }

        //移动到目标节点之前
        if("prev".equals(moveType)) {
            List<M> siblings = findSelfAndNextSiblings(target.getPath(), target.getChildPathSuffix());
            //兄弟节点中包含源节点
            if(siblings.contains(source)) {
                // 001 002 [003 source] 004
                siblings = siblings.subList(0, siblings.indexOf(source) + 1);
                String firstPath = siblings.get(0).getPath();
                for(int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setPath(siblings.get(i + 1).getPath());
                }
                siblings.get(siblings.size() - 1).setPath(firstPath);
            } else {
                // 001 002 003 004  [005 new]
                String nextTargetChildPath = nextChildPath(target.getParentPath(), target.getChildPathSuffix());
                String firstPath = siblings.get(0).getPath();
                for(int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setPath(siblings.get(i + 1).getPath());
                }
                siblings.get(siblings.size() - 1).setPath(nextTargetChildPath);
                source.setPath(firstPath);
            }
            return;
        }
        //否则作为最后孩子节点
        String nextTargetChildPath = nextChildPath(target.getPath(), target.getChildPathSuffix());
        updateSelftAndChild(source.getPath(), nextTargetChildPath);
    }


    /**
     * 把源节点全部变更为目标节点
     * @param sourcePath
     * @param nextTargetChildPath
     */
    @Transactional
    private void updateSelftAndChild(String sourcePath, String nextTargetChildPath) {
        baseRepositoryImpl.batchUpdate(UPDATE_SELF_AND_CHILD_QL, sourcePath, nextTargetChildPath);
    }

    /**
     * 查找目标节点及之后的兄弟
     * @param selfPath
     * @param childPathSuffix
     * @return
     */
    protected List<M> findSelfAndNextSiblings(String selfPath, String childPathSuffix) {
        return baseRepositoryImpl.findAll(FIND_SELF_AND_NEXT_SIBLINGS_QL, selfPath, childPathSuffix);
    }



}
