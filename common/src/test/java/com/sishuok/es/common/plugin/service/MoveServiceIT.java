/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.service;

import com.sishuok.es.common.plugin.entity.Move;
import com.sishuok.es.common.test.BaseIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-20 下午2:22
 * <p>Version: 1.0
 */
public class MoveServiceIT extends BaseIT {

    @Autowired
    private MoveService moveService;

    @Test
    public void testSave() {

        Move m1 = new Move();
        Move m2 = new Move();
        moveService.save(m1);
        moveService.save(m2);

        Assert.assertEquals((Integer) (m1.getWeight() + moveService.getStepLength()), m2.getWeight());
    }


    //测试连续的且to是最后一个
    @Test
    public void testUpWithSerialAndToIsLast() {

        Move from = createMove(); //1000
        Move to = createMove();  //2000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();

        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        to = moveService.findOne(to.getId());
        assertEquals(toWeight, from.getWeight());
        assertEquals(fromWeight, to.getWeight());
    }


    //连续且不是最后一个
    @Test
    public void testUpWithSerialAndToNotLast() {
        Move from = createMove(); //1000
        Move to = createMove();  //2000
        Move last = createMove(); //3000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer lastWeight = last.getWeight();


        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());

        assertEquals(toWeight, from.getWeight());
        assertEquals(fromWeight, to.getWeight());

    }


    //不连续且是最后一个
    @Test
    public void testUpWithNoSerialAndToIsLast() {
        Move from = createMove(); //1000
        Move middle1 = createMove(); //2000
        Move middle2 = createMove(); //3000
        Move to = createMove();  //4000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();

        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());

        assertEquals(toWeight, from.getWeight());

    }

    //不连续且不是最后一个
    @Test
    public void testUpWithNoSerialAndToNotLast() {
        Move from = createMove(); //1000
        Move middle1 = createMove(); //2000
        Move middle2 = createMove(); //3000
        Move to = createMove();  //4000
        Move last = createMove(); //5000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer lastWeight = last.getWeight();

        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(toWeight, from.getWeight());
    }


    //测试连续的且to是第一个
    @Test
    public void testDownWithSerialAndToIsFirst() {

        Move to = createMove(); //1000
        Move from = createMove();  //2000

        Integer toWeight = to.getWeight();
        Integer fromWeight = from.getWeight();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        to = moveService.findOne(to.getId());

        assertEquals(toWeight, from.getWeight());
        assertEquals(fromWeight, to.getWeight());
    }


    //连续且不是第一个
    @Test
    public void testDownWithSerialAndToNotFirst() {
        Move first = createMove(); //1000
        Move to = createMove();  //2000
        Move from = createMove(); //3000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());

        assertEquals(toWeight, from.getWeight());
        assertEquals(fromWeight, to.getWeight());

    }


    //不连续且是第一个
    @Test
    public void testDownWithNoSerialAndToIsFirst() {
        Move to = createMove(); //1000
        Move middle1 = createMove(); //2000
        Move middle2 = createMove(); //3000
        Move from = createMove();  //4000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());

        assertEquals(toWeight, from.getWeight());

    }

    //不连续且不是第一个
    @Test
    public void testDownWithNoSerialAndToNotFirst() {
        Move first = createMove(); //1000
        Move to = createMove(); //2000
        Move middle1 = createMove(); //3000
        Move middle2 = createMove(); //4000
        Move from = createMove();  //5000

        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer firstWeight = first.getWeight();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());

        assertEquals(Integer.valueOf(toWeight), from.getWeight());
    }

    @Test(expected = IllegalStateException.class)
    public void testDownWithNotEnough() {
        Move first = createMove(); //1000
        Move to = createMove(); //2000

        for (int i = 0; i < 25; i++) {
            createMove();
        }
        Move from = createMove();
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer firstWeight = first.getWeight();
        first.setWeight(toWeight - 1);
        moveService.update(first);
        flush();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(toWeight, from.getWeight());
    }


    @Test
    public void testDownWithMiddle25AndNotFirst() {
        Move first = createMove(); //1000
        Move to = createMove(); //2000
        for (int i = 0; i < 25; i++) {
            createMove();
        }
        Move from = createMove();
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer firstWeight = first.getWeight();
        flush();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(Integer.valueOf(toWeight - (toWeight - firstWeight) / 2), from.getWeight());
    }


    @Test
    public void testDownWithMiddle25AndIsFirst() {
        Move to = createMove(); //1000
        for (int i = 0; i < 25; i++) {
            createMove();
        }
        Move from = createMove();
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        flush();

        moveService.down(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(Integer.valueOf(toWeight / 2), from.getWeight());
    }


    @Test
    public void testUpWithMiddle25AndNotLast() {

        Move from = createMove(); //2000
        for (int i = 0; i < 25; i++) {
            createMove();
        }
        Move to = createMove();
        Move last = createMove();
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        Integer lastWeight = last.getWeight();
        flush();

        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(Integer.valueOf(toWeight + (lastWeight - toWeight) / 2), from.getWeight());
    }


    @Test
    public void testUpWithMiddle25AndIsLast() {
        Move from = createMove();
        for (int i = 0; i < 25; i++) {
            createMove();
        }
        Move to = createMove();
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        flush();

        moveService.up(from.getId(), to.getId());
        from = moveService.findOne(from.getId());
        assertEquals(Integer.valueOf(toWeight + moveService.getStepLength()), from.getWeight());
    }

    @Test
    public void testReweight() {
        for (int i = 0; i < 20; i++) {
            Move move = createMove();
            move.setWeight(i);
        }
        flush();

        moveService.reweight();

        List<Move> moves = moveService.findAll(new Sort(Sort.Direction.ASC, "weight"));

        assertEquals(moveService.getStepLength(), moves.get(0).getWeight());
        assertEquals(Integer.valueOf(moveService.getStepLength() * 2), moves.get(1).getWeight());
        assertEquals(Integer.valueOf(moveService.getStepLength() * moves.size()), moves.get(moves.size() - 1).getWeight());

    }


    private Move createMove() {
        Move move = new Move();
        move.setName("zhang" + nextRandom());
        moveService.saveAndFlush(move);
        return move;
    }


}
