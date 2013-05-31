package com.sishuok.es.common.plugin.service; /**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

import com.sishuok.es.common.plugin.entity.Tree;
import com.sishuok.es.common.test.BaseIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-9 下午3:52
 * <p>Version: 1.0
 */
public class TreeServiceIT extends BaseIT {
    @Autowired
    private TreeService treeService;

    private Tree root = null;

    @Before
    public void setUp() {
        root = createTree(0, "0/");
    }

    @Test
    public void testMoveAsChildWithNoChild() {
        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree target = createTree(root.getId(), root.makeSelfAsNewParentIds());
        flush();
        treeService.move(source, target, "inner");
        clear();
        assertEquals(target.getId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.makeSelfAsNewParentIds(), treeService.findOne(source.getId()).getParentIds());
        assertEquals(Integer.valueOf(1), treeService.findOne(source.getId()).getWeight());
    }

    @Test
    public void testMoveAsChildWithChild() {
        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree target = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree t = createTree(target.getId(), target.getParentIds());
        treeService.move(source, target, "inner");
        clear();
        assertEquals(target.getId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.makeSelfAsNewParentIds(), treeService.findOne(source.getId()).getParentIds());

        assertEquals(Integer.valueOf(2), treeService.findOne(source.getId()).getWeight());
    }

    @Test
    public void testMoveAsBeforeWithNoPrevSiblings() {
        Tree target = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldTargetWeight = target.getWeight();
        Tree t = createTree(target.getId(), target.makeSelfAsNewParentIds());
        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldSourceWeight = source.getWeight();
        treeService.move(source, target, "prev");
        clear();

        assertEquals(target.getParentId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.getParentIds(), treeService.findOne(source.getId()).getParentIds());

        assertEquals(Integer.valueOf(oldTargetWeight), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldSourceWeight), treeService.findOne(target.getId()).getWeight());


    }

    @Test
    public void testMoveAsBeforeWithPrevSiblings() {
        Tree t = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree target = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldTargetWeight = target.getWeight();
        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldSourceWeight = source.getWeight();
        treeService.move(source, target, "prev");

        clear();

        assertEquals(target.getParentId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.getParentIds(), treeService.findOne(source.getId()).getParentIds());

        assertEquals(Integer.valueOf(oldTargetWeight), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldSourceWeight), treeService.findOne(target.getId()).getWeight());

    }

    @Test
    public void testMoveAsBeforeWithPrevSiblings2() {
        Tree t11 = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree target = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldTargetWeight = target.getWeight();

        Tree t12 = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldT12Weight = t12.getWeight();

        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        int oldSourceWeight = source.getWeight();

        Tree t13 = createTree(root.getId(), root.makeSelfAsNewParentIds());

        treeService.move(source, target, "prev");

        clear();

        assertEquals(target.getParentId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.getParentId(), treeService.findOne(t12.getId()).getParentId());
        assertEquals(target.getParentIds(), treeService.findOne(source.getId()).getParentIds());

        assertEquals(Integer.valueOf(oldTargetWeight), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldSourceWeight), treeService.findOne(t12.getId()).getWeight());
        assertEquals(Integer.valueOf(oldT12Weight), treeService.findOne(target.getId()).getWeight());


    }

    @Test
    public void testMoveAsBeforeWithPrevSiblingsAndContains() {
        Tree newRoot = createTree(root.getId(), root.makeSelfAsNewParentIds());

        Tree t11 = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        Tree target = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        int oldTargetWeight = target.getWeight();
        Tree t12 = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        int oldT12Weight = t12.getWeight();

        Tree source = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree sourceChild = createTree(source.getId(), source.makeSelfAsNewParentIds());

        treeService.move(source, target, "prev");

        clear();

        assertEquals(target.getParentId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.getParentIds(), treeService.findOne(source.getId()).getParentIds());

        assertEquals(treeService.findOne(source.getId()).getId(), treeService.findOne(sourceChild.getId()).getParentId());
        assertEquals(treeService.findOne(source.getId()).makeSelfAsNewParentIds(), treeService.findOne(sourceChild.getId()).getParentIds());


        assertEquals(Integer.valueOf(oldTargetWeight), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldT12Weight), treeService.findOne(target.getId()).getWeight());
        assertEquals(Integer.valueOf(oldT12Weight + 1), treeService.findOne(t12.getId()).getWeight());


    }


    @Test
    public void testMoveAsAfterWithNoAfterSiblings() {
        Tree newRoot = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree t = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        Tree target = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());

        int oldTargetWeight = target.getWeight();
        Tree source = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        treeService.move(source, target, "next");

        clear();

        assertEquals(Integer.valueOf(oldTargetWeight + 1), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldTargetWeight), treeService.findOne(target.getId()).getWeight());

    }

    @Test
    public void testMoveAsAfterWithAfterSiblings() {
        Tree newRoot = createTree(root.getId(), root.makeSelfAsNewParentIds());
        Tree target = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        Tree t12 = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        int oldT12Weight = t12.getWeight();

        Tree source = createTree(newRoot.getId(), newRoot.makeSelfAsNewParentIds());
        treeService.move(source, target, "next");

        clear();

        assertEquals(target.getParentId(), treeService.findOne(source.getId()).getParentId());
        assertEquals(target.getParentIds(), treeService.findOne(source.getId()).getParentIds());


        assertEquals(Integer.valueOf(oldT12Weight), treeService.findOne(source.getId()).getWeight());
        assertEquals(Integer.valueOf(oldT12Weight + 1), treeService.findOne(t12.getId()).getWeight());


    }


    private Tree createTree(long parentId, String parentIds) {
        Tree tree = new Tree();
        tree.setParentId(parentId);
        tree.setParentIds(parentIds);
        treeService.save(tree);
        return tree;
    }

}
