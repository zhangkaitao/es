/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.monitor.web;

import com.sishuok.es.common.repository.hibernate.HibernateUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseController;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Cache;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-27 下午6:50
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/monitor/hibernate")
public class HibernateCacheMonitorController extends BaseController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @ModelAttribute
    public void setCommonData(Model model) {
        Statistics statistics = HibernateUtils.getSessionFactory(em).getStatistics();
        model.addAttribute("statistics", statistics);

        Date startDate = new Date(statistics.getStartTime());
        Date nowDate = new Date();
        long upSeconds = (nowDate.getTime() - startDate.getTime()) / 1000;
        model.addAttribute("upSeconds", upSeconds);
    }

    /**
     * 所有信息
     * @return
     */
    @RequestMapping("")
    public String index(Model model) {
        setMemoryInfo(model);
        model.addAttribute("sessionFactory", HibernateUtils.getSessionFactory(em));

        Map<String, Object> properties = new TreeMap<String, Object>(em.getEntityManagerFactory().getProperties());
        model.addAttribute("properties", properties);
        return viewName("index");
    }

    /**
     * 查询缓存统计
     * @return
     */
    @RequestMapping("/queryCache")
     public String queryCache() {
        return viewName("queryCache");
    }

    /**
     * 二级缓存统计
     * @return
     */
    @RequestMapping("/secondLevelCache")
    public String secondLevelCache(Model model) {
        setMemoryInfo(model);
        return viewName("secondLevelCache");
    }


    /**
     * 实体和集合 增删改查 次数 统计
     * @return
     */
    @RequestMapping("/entityAndCollectionCRUDCount")
    public String entityAndCollectionCRUDCount() {
        return viewName("entityAndCollectionCRUDCount");
    }


    /**
     * 实体和集合 增删改查 次数 统计
     * @return
     */
    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    public String showEvictForm() {
        return viewName("evictForm");
    }

    @RequestMapping(value = "/evictEntity")
    @ResponseBody
    public String evictEntity(
            @RequestParam(value = "entityNames", required = false) String[] entityNames,
            @RequestParam(value = "entityIds", required = false) Serializable[] entityIds) {

        boolean entityNamesEmpty = ArrayUtils.isEmpty(entityNames);
        boolean entityIdsEmpty = ArrayUtils.isEmpty(entityIds);

        Cache cache = HibernateUtils.getCache(em);

        if(entityNamesEmpty && entityIdsEmpty) {
            cache.evictEntityRegions();
        } else if(entityIdsEmpty) {
            for(String entityName : entityNames) {
                cache.evictEntityRegion(entityName);
            }
        } else {
            for(String entityName : entityNames) {
                for(Serializable entityId : entityIds) {
                    cache.evictEntity(entityName, entityId);
                }
            }
        }

        return "操作成功";
    }

    @RequestMapping(value = "/evictCollection")
    @ResponseBody
    public String evictCollection(
            @RequestParam(value = "collectionRoleNames", required = false) String[] collectionRoleNames,
            @RequestParam(value = "collectionEntityIds", required = false) Serializable[] collectionEntityIds) {


        boolean collectionRoleNamesEmpty = ArrayUtils.isEmpty(collectionRoleNames);
        boolean collectionEntityIdsEmpty = ArrayUtils.isEmpty(collectionEntityIds);

        Cache cache = HibernateUtils.getCache(em);

        if(collectionRoleNamesEmpty && collectionEntityIdsEmpty) {
            cache.evictEntityRegions();
        } else if(collectionEntityIdsEmpty) {
            for(String collectionRoleName : collectionRoleNames) {
                cache.evictCollectionRegion(collectionRoleName);
            }
        } else {
            for(String collectionRoleName : collectionRoleNames) {
                for(Serializable collectionEntityId : collectionEntityIds) {
                    cache.evictCollection(collectionRoleName, collectionEntityIds);
                }
            }
        }

        return "操作成功";
    }

    @RequestMapping(value = "/evictQuery")
    @ResponseBody
    public String evictQuery(
            @RequestParam(value = "queries", required = false) String[] queries) {


        boolean queriesEmpty = ArrayUtils.isEmpty(queries);

        Cache cache = HibernateUtils.getCache(em);

        if(queriesEmpty) {
            cache.evictQueryRegions();
            cache.evictDefaultQueryRegion();
        } else {
            for(String query : queries) {
                cache.evictQueryRegion(query);
            }
        }

        return "操作成功";
    }

    @RequestMapping(value = "/evictAll")
    @ResponseBody
    public String evictAll() {
        HibernateUtils.evictLevel2Cache(em);
        return "操作成功";
    }

    @RequestMapping(value = "/clearAll")
    @ResponseBody
    public String clearAll() {
        HibernateUtils.evictLevel2Cache(em);
        HibernateUtils.getSessionFactory(em).getStatistics().clear();
        return "操作成功";
    }


    @RequestMapping(value = "/ql", method = RequestMethod.GET)
    public String showQLForm() {
        return viewName("qlForm");
    }

    @PageableDefaults(pageNumber = 0, value = 10)
    @RequestMapping(value = "/ql", method = RequestMethod.POST)
    public String executeQL(
            final @RequestParam("ql") String ql, final Model model,
            final Pageable pageable
    ) {

        model.addAttribute("sessionFactory", HibernateUtils.getSessionFactory(em));

        new TransactionTemplate(transactionManager).execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                //1首先尝试执行ql更新
                try {
                    Query query = em.createQuery(ql);
                    int updateCount = query.executeUpdate();
                    model.addAttribute("updateCount", updateCount);
                    return null;
                } catch (Exception e) {
                }
                //2如果失败 尝试执行ql查询
                try {
                    String findQL = ql;
                    String alias = QueryUtils.detectAlias(findQL);
                    if(StringUtils.isEmpty(alias)) {
                        Pattern pattern = Pattern.compile("^(.*\\s*from\\s+)(.*)(\\s*.*)", Pattern.MULTILINE|Pattern.CASE_INSENSITIVE);
                        findQL = pattern.matcher(findQL).replaceFirst("$1$2 o $3");
                    }
                    String countQL = QueryUtils.createCountQueryFor(findQL);
                    Query countQuery = em.createQuery(countQL);
                    Query findQuery = em.createQuery(findQL);
                    findQuery.setFirstResult(pageable.getOffset());
                    findQuery.setMaxResults(pageable.getPageSize());

                    Page page = new PageImpl(
                            findQuery.getResultList(),
                            pageable,
                            (Long)countQuery.getSingleResult());

                    model.addAttribute("resultPage", page);
                    return null;
                } catch (Exception e) {
                    model.addAttribute("error", e);
                }

                return null;
            }
        });

        return viewName("qlForm");
    }


    private void setMemoryInfo(Model model) {
        //系统的
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long usedSystemMemory = heapMemoryUsage.getUsed();
        long maxSystemMemory = heapMemoryUsage.getMax();
        model.addAttribute("usedSystemMemory", usedSystemMemory);
        model.addAttribute("maxSystemMemory", maxSystemMemory);

        //二级缓存的
        Statistics statistics = (Statistics) model.asMap().get("statistics");
        String[] secondLevelCacheRegionNames = statistics.getSecondLevelCacheRegionNames();

        int totalMemorySize = 0;
        int totalMemoryCount = 0;
        int totalDiskCount = 0;

        for(String secondLevelCacheRegionName : secondLevelCacheRegionNames) {
            SecondLevelCacheStatistics secondLevelCacheStatistics =
                    statistics.getSecondLevelCacheStatistics(secondLevelCacheRegionName);
            totalMemorySize += secondLevelCacheStatistics.getSizeInMemory();
            totalMemoryCount += secondLevelCacheStatistics.getElementCountInMemory();
            totalDiskCount += secondLevelCacheStatistics.getElementCountOnDisk();
        }

        model.addAttribute("totalMemorySize", totalMemorySize);
        model.addAttribute("totalMemoryCount", totalMemoryCount);
        model.addAttribute("totalDiskCount", totalDiskCount);
    }
}
