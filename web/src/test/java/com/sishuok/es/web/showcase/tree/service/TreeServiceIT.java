package com.sishuok.es.web.showcase.tree.service; /**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

import com.sishuok.es.web.showcase.move.entity.Move;
import com.sishuok.es.web.showcase.move.repository.MoveRepository;
import com.sishuok.es.web.showcase.move.service.MoveService;
import com.sishuok.es.web.showcase.tree.entity.Tree;
import com.sishuok.es.web.showcase.tree.repository.TreeRepository;
import com.sishuok.es.web.test.BaseIT;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-9 下午3:52
 * <p>Version: 1.0
 */
public class TreeServiceIT extends BaseIT {
    @Autowired
    private TreeService treeService;
    @Autowired
    private TreeRepository treeRepository;

    @Before
    public void setUp() {
        treeRepository.deleteAll();
    }

    @Test
    public void testMoveAsChildWithNoChild() {
        Tree t1 = createTree("998001");
        Tree t2 = createTree("999001");
        treeRepository.flush();
        treeService.move(t1, t2.getPath(), "inner");
        assertNotNull(treeService.findByPath("999001001"));
    }
    @Test
    public void testMoveAsChildWithChild() {
        Tree t1 = createTree("998001");
        Tree t2 = createTree("999001");
        Tree t21 = createTree("999001001");
        treeService.move(t1, t2.getPath(), "inner");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("999001002"));
    }

    @Test
    public void testMoveAsBeforeWithNoPrevSiblings() {
        Tree t11 = createTree("998001");
        Tree t12 = createTree("998002");
        Tree t21 = createTree("999001");
        treeService.move(t21, t11.getPath(), "prev");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("998001"));
        assertNotNull(treeService.findByPath("998002"));
        assertNotNull(treeService.findByPath("998003"));

        assertEquals(t21, treeService.findByPath("998001"));
        assertEquals(t11, treeService.findByPath("998002"));
        assertEquals(t12, treeService.findByPath("998003"));
    }

    @Test
    public void testMoveAsBeforeWithPrevSiblings() {
        Tree t11 = createTree("998001");
        Tree t12 = createTree("998002");
        Tree t21 = createTree("999001");
        treeService.move(t21, t12.getPath(), "prev");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("998001"));
        assertNotNull(treeService.findByPath("998002"));
        assertNotNull(treeService.findByPath("998003"));

        assertEquals(t11, treeService.findByPath("998001"));
        assertEquals(t21, treeService.findByPath("998002"));
        assertEquals(t12, treeService.findByPath("998003"));
    }

    @Test
    public void testMoveAsBeforeWithPrevSiblingsAndContains() {
        Tree t11 = createTree("998001");
        Tree t12 = createTree("998002");
        Tree t13 = createTree("998003");
        Tree t14 = createTree("998004");
        treeService.move(t13, t12.getPath(), "prev");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("998001"));
        assertNotNull(treeService.findByPath("998002"));
        assertNotNull(treeService.findByPath("998003"));
        assertNull(treeService.findByPath("998005"));

        assertEquals(t11, treeService.findByPath("998001"));
        assertEquals(t13, treeService.findByPath("998002"));
        assertEquals(t12, treeService.findByPath("998003"));
        assertEquals(t14, treeService.findByPath("998004"));

    }


    @Test
    public void testMoveAsAfterWithNoAfterSiblings() {
        Tree t11 = createTree("998001");
        Tree t12 = createTree("998002");
        Tree t21 = createTree("999001");
        treeService.move(t21, t12.getPath(), "next");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("998001"));
        assertNotNull(treeService.findByPath("998002"));
        assertNotNull(treeService.findByPath("998003"));

        assertEquals(t11, treeService.findByPath("998001"));
        assertEquals(t12, treeService.findByPath("998002"));
        assertEquals(t21, treeService.findByPath("998003"));
    }

    @Test
    public void testMoveAsAfterWithAfterSiblings() {
        Tree t11 = createTree("998001");
        Tree t12 = createTree("998002");
        Tree t21 = createTree("999001");
        treeService.move(t21, t11.getPath(), "next");
        treeRepository.flush();
        assertNotNull(treeService.findByPath("998001"));
        assertNotNull(treeService.findByPath("998002"));
        assertNotNull(treeService.findByPath("998003"));

        assertEquals(t11, treeService.findByPath("998001"));
        assertEquals(t21, treeService.findByPath("998002"));
        assertEquals(t12, treeService.findByPath("998003"));
    }


    private Tree createTree(String path) {
        Tree tree = new Tree();
        tree.setPath(path);
        treeService.save(tree);
        return tree;
    }

}
