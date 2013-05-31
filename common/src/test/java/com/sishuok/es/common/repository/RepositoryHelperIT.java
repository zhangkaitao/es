/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.Sex;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.DefaultSearchCallback;
import com.sishuok.es.common.repository.callback.SearchCallback;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-5 上午9:10
 * <p>Version: 1.0
 */
public class RepositoryHelperIT extends BaseUserIT {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private RepositoryHelper repositoryHelper;

    @Before
    public void setUp() {
        RepositoryHelper.setEntityManagerFactory(entityManagerFactory);
        repositoryHelper = new RepositoryHelper(User.class);
    }


    @Test
    public void testGetEntityManager() {
        Assert.assertNotNull(repositoryHelper.getEntityManager());
    }

    @Test
    public void testCount() {
        String ql = "select count(o) from User o";
        long expectedCount = repositoryHelper.count(ql) + 1;

        User user = createUser();
        repositoryHelper.getEntityManager().persist(user);

        Assert.assertEquals(expectedCount, repositoryHelper.count(ql));

    }


    @Test
    public void testCountWithCondition() {

        User user = createUser();
        repositoryHelper.getEntityManager().persist(user);

        String ql = "select count(o) from User o where id >= ? and id <=?";
        Assert.assertEquals(1, repositoryHelper.count(ql, user.getId(), user.getId()));
        Assert.assertEquals(0, repositoryHelper.count(ql, user.getId(), 0L));
    }

    @Test
    public void testFindAll() {
        String ql = "select o from User o";

        List<User> before = repositoryHelper.findAll(ql);
        User user1 = createUser();
        User user2 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);

        List<User> after = repositoryHelper.findAll(ql);

        Assert.assertEquals(before.size() + 2, after.size());

        Assert.assertTrue(after.contains(user1));

    }

    @Test
    public void testFindAllWithCondition() {
        String ql = "select o from User o where id>=? and id<=?";

        List<User> before = repositoryHelper.findAll(ql, 0L, Long.MAX_VALUE);
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        List<User> after = repositoryHelper.findAll(ql, 0L, user2.getId());

        Assert.assertEquals(before.size() + 2, after.size());

        Assert.assertTrue(after.contains(user1));
        Assert.assertTrue(after.contains(user2));

        Assert.assertFalse(after.contains(user3));
        Assert.assertFalse(after.contains(user4));
    }


    @Test
    public void testFindAllWithPage() {

        repositoryHelper.batchUpdate("delete from User");

        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        String ql = "select o from User o";

        Assert.assertEquals(4, repositoryHelper.findAll(ql, null).size());

        List<User> list = repositoryHelper.findAll(ql, new PageRequest(0, 2));
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(user1));
    }


    @Test
    public void testFindAllWithSort() {

        repositoryHelper.batchUpdate("delete from User");

        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        String ql = "select o from User o";


        List<User> list = repositoryHelper.findAll(ql, new Sort(Sort.Direction.DESC, "id"));

        Assert.assertEquals(4, list.size());

        Assert.assertTrue(list.get(0).equals(user4));
    }


    @Test
    public void testFindAllWithPageAndSort() {

        repositoryHelper.batchUpdate("delete from User");

        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        String ql = "select o from User o";


        List<User> list = repositoryHelper.findAll(ql, new PageRequest(0, 2, new Sort(Sort.Direction.DESC, "id")));

        Assert.assertEquals(2, list.size());

        Assert.assertTrue(list.get(0).equals(user4));
        Assert.assertTrue(list.contains(user3));

        Assert.assertFalse(list.contains(user1));
    }


    @Test
    public void testFindOne() {
        User user1 = createUser();
        User user2 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);

        String ql = "select o from User o where id=? and baseInfo.sex=?";
        Assert.assertNotNull(repositoryHelper.findOne(ql, user1.getId(), Sex.male));
        Assert.assertNull(repositoryHelper.findOne(ql, user1.getId(), Sex.female));
    }


    @Test
    public void testFindAllWithSearchableAndDefaultSearchCallbck() {


        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("id_in", new Long[]{user1.getId(), user2.getId(), user3.getId()});

        searchable.setPage(0, 2);
        searchable.addSort(Sort.Direction.DESC, "id");


        String ql = "from User where 1=1";
        List<User> list = repositoryHelper.findAll(ql, searchable, SearchCallback.DEFAULT);

        Assert.assertEquals(2, list.size());

        Assert.assertEquals(user3, list.get(0));

    }


    @Test
    public void testFindAllWithSearchableAndCustomSearchCallbck() {


        User user1 = createUser();
        User user2 = createUser();
        user2.getBaseInfo().setRealname("lisi");
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("realname", "zhang");
        searchable.addSearchParam("id_lt", user4.getId());

        searchable.setPage(0, 2);
        searchable.addSort(Sort.Direction.DESC, "id");


        SearchCallback customCallback = new DefaultSearchCallback() {
            @Override
            public void prepareQL(StringBuilder ql, Searchable search) {
                //默认的
                super.prepareQL(ql, search);
                //自定义的
                if (search.containsSearchKey("realname")) {//此处也可以使用realname_custom
                    ql.append(" and baseInfo.realname like :realname");
                }
            }

            @Override
            public void setValues(Query query, Searchable search) {
                //默认的
                super.setValues(query, search);
                //自定义的
                if (search.containsSearchKey("realname")) {
                    query.setParameter("realname", "%" + search.getValue("realname") + "%");
                }
            }
        };


        String ql = "from User where 1=1";
        List<User> list = repositoryHelper.findAll(ql, searchable, customCallback);

        Assert.assertEquals(2, list.size());

        Assert.assertEquals(user3, list.get(0));
        Assert.assertEquals(user1, list.get(1));

    }


    @Test
    public void testFindAllWithSearchableAndCustomSearchCallbck2() {


        User user1 = createUser();
        User user2 = createUser();
        user2.getBaseInfo().setRealname("lisi");
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("realname", "zhang");
        searchable.addSearchParam("id_lt", user4.getId());

        searchable.setPage(0, 2);
        searchable.addSort(Sort.Direction.DESC, "id");


        SearchCallback customCallback = new DefaultSearchCallback() {
            @Override
            public void prepareQL(StringBuilder ql, Searchable search) {
                //不调用默认的
                if (search.containsSearchKey("id_lt")) {
                    ql.append(" and id < :id");
                }
                //自定义的
                if (search.containsSearchKey("realname_custom")) {//此处也可以使用realname_custom
                    ql.append(" and baseInfo.realname like :realname");
                }
            }

            @Override
            public void setValues(Query query, Searchable search) {
                //不调用默认的
                if (search.containsSearchKey("id_lt")) {
                    query.setParameter("id", search.getValue("id_lt"));
                }
                //自定义的
                if (search.containsSearchKey("realname")) {
                    query.setParameter("realname", "%" + search.getValue("realname") + "%");
                }
            }
        };


        String ql = "from User where 1=1";
        List<User> list = repositoryHelper.findAll(ql, searchable, customCallback);

        Assert.assertEquals(2, list.size());

        Assert.assertEquals(user3, list.get(0));
        Assert.assertEquals(user1, list.get(1));

    }


    @Test
    public void testCountWithSearchableAndDefaultSearchCallbck() {


        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("id_in", new Long[]{user1.getId(), user2.getId(), user3.getId()});

        searchable.addSort(Sort.Direction.DESC, "id");


        String ql = "select count(*) from User where 1=1";
        long total = repositoryHelper.count(ql, searchable, SearchCallback.DEFAULT);

        Assert.assertEquals(3L, total);

    }


    @Test
    public void testCountWithSearchableAndCustomSearchCallbck() {


        User user1 = createUser();
        User user2 = createUser();
        user2.getBaseInfo().setRealname("lisi");
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("realname", "zhang");
        searchable.addSearchParam("id_lt", user4.getId());

        searchable.setPage(0, 2);
        searchable.addSort(Sort.Direction.DESC, "id");


        SearchCallback customCallback = new DefaultSearchCallback() {
            @Override
            public void prepareQL(StringBuilder ql, Searchable search) {
                //默认的
                super.prepareQL(ql, search);
                //自定义的
                if (search.containsSearchKey("realname")) {//此处也可以使用realname_custom
                    ql.append(" and baseInfo.realname like :realname");
                }
            }

            @Override
            public void setValues(Query query, Searchable search) {
                //默认的
                super.setValues(query, search);
                //自定义的
                if (search.containsSearchKey("realname")) {
                    query.setParameter("realname", "%" + search.getValue("realname") + "%");
                }
            }
        };


        String ql = "select count(*) from User where 1=1";
        long total = repositoryHelper.count(ql, searchable, customCallback);

        Assert.assertEquals(2, total);


    }


    @Test
    public void testCountWithSearchableAndCustomSearchCallbck2() {

        User user1 = createUser();
        User user2 = createUser();
        user2.getBaseInfo().setRealname("lisi");
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("realname", "zhang");
        searchable.addSearchParam("id_lt", user4.getId());

        searchable.setPage(0, 2);
        searchable.addSort(Sort.Direction.DESC, "id");


        SearchCallback customCallback = new DefaultSearchCallback() {
            @Override
            public void prepareQL(StringBuilder ql, Searchable search) {
                //不调用默认的
                if (search.containsSearchKey("id_lt")) {
                    ql.append(" and id < :id");
                }
                //自定义的
                if (search.containsSearchKey("realname_custom")) {//此处也可以使用realname_custom
                    ql.append(" and baseInfo.realname like :realname");
                }
            }

            @Override
            public void setValues(Query query, Searchable search) {
                //不调用默认的
                if (search.containsSearchKey("id_lt")) {
                    query.setParameter("id", search.getValue("id_lt"));
                }
                //自定义的
                if (search.containsSearchKey("realname")) {
                    query.setParameter("realname", "%" + search.getValue("realname") + "%");
                }
            }
        };


        String ql = "select count(*) from User where 1=1";
        long total = repositoryHelper.count(ql, searchable, customCallback);
        Assert.assertEquals(2, total);

    }


    @Test
    public void testBatchUpdate() {
        User user1 = createUser();
        User user2 = createUser();
        user2.getBaseInfo().setRealname("lisi");
        User user3 = createUser();
        User user4 = createUser();
        repositoryHelper.getEntityManager().persist(user1);
        repositoryHelper.getEntityManager().persist(user2);
        repositoryHelper.getEntityManager().persist(user3);
        repositoryHelper.getEntityManager().persist(user4);

        String newPassword = "123321";

        String updateQL = "update User set password=? where id=?";
        repositoryHelper.batchUpdate(updateQL, newPassword, user1.getId());

        clear();

        user1 = repositoryHelper.findOne("from User where id=?", user1.getId());

        Assert.assertEquals(newPassword, user1.getPassword());

    }


}
