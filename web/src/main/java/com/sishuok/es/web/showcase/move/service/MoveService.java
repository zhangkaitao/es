/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.move.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.showcase.move.entity.Move;
import com.sishuok.es.web.showcase.move.repository.MoveRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class MoveService extends BaseService<Move, Long> {

    private MoveRepository moveRepository;

    @Autowired
    public void setMoveRepository(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
        setBaseRepository(moveRepository);
    }

    @Override
    public Move save(Move move) {
        if(move.getWeight() == null) {
            move.setWeight(findNextWeight());
        }
        return super.save(move);
    }

    @Override
    public Move saveAndFlush(Move move) {
        if (move.getWeight() == null) {
            move.setWeight(findNextWeight());
        }
        return super.save(move);
    }


    public Move findByName(String name) {
        return moveRepository.findByName(name);
    }

    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        moveRepository.deleteByIds(Arrays.asList(ids));
    }

    //权重的步长
    final Integer stepLength = 1000;

    /**
     * 查询下一个权重
     * @return
     */
    public Integer findNextWeight() {
        return moveRepository.findNextWeight(stepLength);
    }
    public Move findPreByWeight(Integer weight) {
        Pageable pageable = new PageRequest(0, 1);
        Page<Move> page = moveRepository.findPreByWeight(weight, pageable);
        if(page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    public Move findNextByWeight(Integer weight) {
        Pageable pageable = new PageRequest(0, 1);
        Page<Move> page = moveRepository.findNextByWeight(weight, pageable);
        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    /**
     * 按照降序进行移动
     * 把{fromId}移动到{}toId}之后
     * 如 fromWeight 2000 toWeight 1000   则新的为 500
     * @param fromId
     * @param toId
     */
    public void down(Long fromId, Long toId) {
        Move from = findOne(fromId);
        Move to = findOne(toId);
        if (from == null || to == null || from.equals(to)) {
            return;
        }
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();


        Move nextTo = findNextByWeight(toWeight);
        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(nextTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }

        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = moveRepository.countByBetween(minWeight, maxWeight).intValue();
        if(count > 0 && count < 20) {
            List<Move> moves = moveRepository.findByBetweenAndAsc(minWeight, maxWeight);
            if(fromWeight < toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(minWeight);
            }
            return;
        }

        Move preTo = findPreByWeight(toWeight);

        //计算新的权重
        int newWeight = 0;
        if (preTo == null) {
            newWeight = toWeight / 2;
        } else {
            newWeight = toWeight - (toWeight - preTo.getWeight()) / 2;
        }


        if(Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("up error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);
    }

    /**
     * 按照降序进行移动
     * 把{fromId}移动到toId之下
     * 如 fromWeight 1000 toWeight 2000  3000 则新的为 2500
     * @param fromId
     * @param toId
     */
    public void up(Long fromId, Long toId) {
        Move from = findOne(fromId);
        Move to = findOne(toId);
        if (from == null || to == null || from.equals(to)) {
            return;
        }
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();


        Move preTo = findPreByWeight(toWeight);
        //如果toId的下一个是fromId 则直接交换顺序即可
        if(from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }

        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = moveRepository.countByBetween(minWeight, maxWeight).intValue();
        if(count > 0 && count < 20) {
            List<Move> moves = moveRepository.findByBetweenAndDesc(minWeight, maxWeight);
            //123/124
            //5000 4000 3000

            if (fromWeight > toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(maxWeight);
            }
            return;
        }

        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }
        Move nextTo = findNextByWeight(toWeight);

        //计算新的权重
        int newWeight = 0;
        if(nextTo == null) {
            newWeight = toWeight + stepLength;
        } else {
            newWeight = toWeight + (nextTo.getWeight() - toWeight)/2;
        }

        if(Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("down error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);
    }

    public void reweight() {
        int batchSize = 100;
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable = new PageRequest(0, batchSize, sort);
        Page<Move> page = findAll(pageable);
        do {
            //doReweight需要requiresNew事务
            ((MoveService)AopContext.currentProxy()).doReweight(page);

            if(page.isLastPage()) {
                break;
            }

            pageable = new PageRequest((pageable.getPageNumber() + 1) * batchSize, batchSize, sort);
            page = findAll(pageable);
        } while (true);
    }

    public void doReweight(Page<Move> page) {
        int totalElements = (int)page.getTotalElements();
        List<Move> moves = page.getContent();

        int firstElement = page.getNumber() * page.getSize();

        for(int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            move.setWeight((totalElements - firstElement - i) * stepLength);
            update(move);
        }

    }


}
