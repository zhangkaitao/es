/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.tree.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.showcase.tree.entity.Tree;
import com.sishuok.es.web.showcase.tree.repository.TreeRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class TreeService extends BaseService<Tree, Long> {

    private TreeRepository treeRepository;

    @Autowired
    public void setTreeRepository(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
        setBaseRepository(treeRepository);
    }


    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        treeRepository.deleteByIds(Arrays.asList(ids));
    }

    public void deleteSelfAndChild(Tree tree) {
        treeRepository.deleteSelfAndChild(tree.getPath());
    }

    public void appendChild(Tree parent, Tree child) {
        String nextChildPath = nextChildPath(parent.getPath(), parent.getChildPathSuffix());
        child.setPath(nextChildPath);
        save(child);
    }

    private String nextChildPath(String parentPath, String childPathSuffix) {
        String maxChildPath = treeRepository.findMaxChildPath(parentPath, childPathSuffix);
        if(StringUtils.isEmpty(maxChildPath)) {
            return parentPath + String.format("%0" + childPathSuffix.length() + "d", 1);
        }
        return String.format("%0" + maxChildPath.length() + "d", Long.valueOf(maxChildPath) + 1);
    }


    public Tree findByPath(String path) {
        return treeRepository.findByPath(path);
    }

    /**
     * 移动节点
     * 根节点不能移动
     * @param source 源节点
     * @param targetPath 目标节点
     * @param moveType 位置
     */
    public void move(Tree source, String targetPath, String moveType) {
        Tree target = findByPath(targetPath);
        if(source == null || target == null || source.isRoot() || target.isRoot()) { //根节点不能移动
            return;
        }

        //移动到目标节点之后
        if ("next".equals(moveType)) {
            List<Tree> siblings = treeRepository.findSelfAndNextSiblings(target.getPath(), target.getChildPathSuffix());
            siblings.remove(0);//把自己移除

            if(siblings.size() == 0) { //如果没有兄弟了 则直接把源的设置为目标即可
                String nextTargetChildPath = nextChildPath(target.getParentPath(), target.getChildPathSuffix());
                treeRepository.updateSelftAndChild(source.getPath(), nextTargetChildPath);
                return;
            } else {
                moveType = "prev";
                target = siblings.get(0); //否则，相当于插入到实际目标节点下一个节点之前
            }
        }

        //移动到目标节点之前
        if("prev".equals(moveType)) {
            List<Tree> siblings = treeRepository.findSelfAndNextSiblings(target.getPath(), target.getChildPathSuffix());
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
        treeRepository.updateSelftAndChild(source.getPath(), nextTargetChildPath);
    }
}
