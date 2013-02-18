/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.move.repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.web.showcase.move.entity.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface MoveRepository extends BaseRepository<Move, Long> {

    Move findByName(String name);

    @Modifying
    @Query(value = "delete from Move where id in (?1)")
    void deleteByIds(List<Long> ids);

    @Query(value = "select (case when (max(weight) is null) then 0 else max(weight) end) + ?1 from Move")
    Integer findNextWeight(Integer stepLength);

    @Query(value = "from Move m where m.weight < ?1 order by m.weight desc")
    Page<Move> findPreByWeight(Integer weight, Pageable pageable);

    @Query(value = "from Move m where m.weight > ?1 order by m.weight asc")
    Page<Move> findNextByWeight(Integer weight, Pageable pageable);

    @Query(value = "select count(m) from Move m where m.weight>=?1 and m.weight <= ?2")
    Long countByBetween(Integer minWeight, Integer maxWeight);

    @Query(value = "from Move m where m.weight>=?1 and m.weight <= ?2 order by m.weight asc")
    List<Move> findByBetweenAndAsc(Integer minWeight, Integer maxWeight);

    @Query(value = "from Move m where m.weight>=?1 and m.weight <= ?2 order by m.weight desc")
    List<Move> findByBetweenAndDesc(Integer minWeight, Integer maxWeight);
}


