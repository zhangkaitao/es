/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.extra.aop;

import com.sishuok.es.common.cache.BaseCacheAspect;
import com.sishuok.es.sys.auth.entity.Auth;
import com.sishuok.es.sys.auth.service.AuthService;
import com.sishuok.es.sys.group.entity.Group;
import com.sishuok.es.sys.group.entity.GroupRelation;
import com.sishuok.es.sys.group.service.GroupRelationService;
import com.sishuok.es.sys.group.service.GroupService;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;
import com.sishuok.es.sys.organization.service.JobService;
import com.sishuok.es.sys.organization.service.OrganizationService;
import com.sishuok.es.sys.permission.entity.Permission;
import com.sishuok.es.sys.permission.entity.Role;
import com.sishuok.es.sys.permission.service.PermissionService;
import com.sishuok.es.sys.permission.service.RoleService;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.service.ResourceService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 用户权限的切面
 * <p/>
 * 1、当调用如下方法时，加缓存
 * {@link com.sishuok.es.sys.auth.service.UserAuthService#findRoles}
 * {@link com.sishuok.es.sys.auth.service.UserAuthService#findStringRoles}
 * {@link com.sishuok.es.sys.auth.service.UserAuthService#findStringPermissions}
 * <p/>
 * 2、授权（Auth）
 * 当增删改授权时，
 * 如果是用户相关的，只删用户的即可，
 * 其他的全部清理
 * <p/>
 * 3。1、资源（Resource）
 * 当修改资源时判断是否发生变化（如resourceIdentity，是否显示），如果变了清缓存
 * 当删除资源时，清缓存
 * 3.2、权限（Permission）
 * 当修改权限时判断是否发生变化（如permission，是否显示），如果变了清缓存
 * 当删除权限时，清缓存
 * <p/>
 * 4、角色（Role）
 * 当删除角色时，请缓存
 * 当修改角色show/role/resourcePermissions关系时，清缓存
 * <p/>
 * 5.1、组织机构
 * 当删除/修改show/parentId字段时，清缓存
 * 5.2、工作职务
 * 当删除/修改show/parentId字段时，清缓存
 * <p/>
 * 6。1、组
 * 当修改组的默认组/show时，清缓存
 * 当删除组时，清缓存
 * <p/>
 * 6.2、当删除组关系时
 * 当新增/修改/删除的是某个用户的，只清理这个用户的缓存
 * 其他清所有缓存
 * <p/>
 * 7、用户
 * 修改时，如果组织机构/工作职务变了，仅需清自己的缓存
 * <p/>
 * 清理自己时也同时清理菜单的缓存
 * <p/>
 * TODO
 * 1、异步失效缓存
 * 2、预填充缓存（即把此刻失效的再通过异步任务查一次） 目前只查前100个吧
 * 3、加二级缓存 提高失效再查的效率
 * <p/>
 * 此方法的一个缺点就是 只要改了一个，所有缓存失效。。。。
 * TODO 思考更好的做法？
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-15 下午2:16
 * <p>Version: 1.0
 */
@Component
@Aspect
public class UserAuthCacheAspect extends BaseCacheAspect {

    public UserAuthCacheAspect() {
        setCacheName("sys-authCache");
    }

    private String rolesKeyPrefix = "roles-";
    private String stringRolesKeyPrefix = "string-roles-";
    private String stringPermissionsKeyPrefix = "string-permissions-";

    @Autowired
    private ResourceMenuCacheAspect resourceMenuCacheAspect;

    @Autowired
    private AuthService authService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private JobService jobService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRelationService groupRelationService;
    @Autowired
    private UserService userService;


    ////////////////////////////////////////////////////////////////////////////////
    ////切入点
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * 2、授权（Auth）
     * 当增删改授权时，
     * 如果是用户相关的，只删用户的即可，
     * 其他的全部清理
     */
    @Pointcut(value = "target(com.sishuok.es.sys.auth.service.AuthService)")
    private void authServicePointcut() {
    }

    @Pointcut(value = "execution(* addGroupAuth(..)) " +
            "|| execution(* addOrganizationJobAuth(..)) " +
            "|| execution(* addOrganizationJobAuth(..))")
    private void authCacheEvictAllPointcut() {
    }

    @Pointcut(value = "(execution(* addUserAuth(*,..)) && args(arg, ..)) " +
            "|| (execution(* update(*)) && args(arg)) " +
            "|| (execution(* save(*)) && args(arg)) " +
            "|| (execution(* delete(*)) && args(arg))",
            argNames = "arg")
    private void authCacheEvictAllOrSpecialPointcut(Object arg) {
    }


    /**
     * 3.1、资源（Resource）
     * 当修改资源时判断是否发生变化（如resourceIdentity，是否显示），如果变了清缓存
     * 当删除资源时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.resource.service.ResourceService)")
    private void resourceServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void resourceCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void resourceMaybeCacheEvictAllPointcut(Resource arg) {
    }

    /**
     * 3.2、权限（Permission）
     * 当修改权限时判断是否发生变化（如permission，是否显示），如果变了清缓存
     * 当删除权限时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.permission.service.PermissionService)")
    private void permissionServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void permissionCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void permissionMaybeCacheEvictAllPointcut(Permission arg) {
    }


    /**
     * 4、角色（Role）
     * 当删除角色时，请缓存
     * 当修改角色show/role/resourcePermissions关系时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.permission.service.RoleService)")
    private void roleServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void roleCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void roleMaybeCacheEvictAllPointcut(Role arg) {
    }


    /**
     * 5.1、组织机构
     * 当删除/修改show字段时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.organization.service.OrganizationService)")
    private void organizationServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void organizationCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void organizationMaybeCacheEvictAllPointcut(Organization arg) {
    }

    /**
     * 5.2、工作职务
     * 当删除/修改show字段时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.organization.service.JobService)")
    private void jobServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void jobCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void jobMaybeCacheEvictAllPointcut(Job arg) {
    }


    /**
     * 6。1、组
     * 当修改组的默认组/show时，清缓存
     * 当删除组时，清缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.group.service.GroupService)")
    private void groupServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(..))")
    private void groupCacheEvictAllPointcut() {
    }

    @Pointcut(value = "execution(* update(*)) && args(arg)", argNames = "arg")
    private void groupMaybeCacheEvictAllPointcut(Group arg) {
    }

    /**
     * 6.2、当删除组关系时
     * 当添加/修改/删除的是某个用户的，只清理这个用户的缓存
     * 其他情况，清所有
     */
    @Pointcut(value = "target(com.sishuok.es.sys.group.service.GroupRelationService)")
    private void groupRelationServicePointcut() {
    }

    @Pointcut(value = "execution(* appendRelation(*,*))")
    private void groupRelationCacheEvictAllPointcut() {
    }

    @Pointcut(value = "(execution(* delete(*)) && args(arg)) " +
            "|| (execution(* update(*)) && args(arg)) " +
            "|| execution(* appendRelation(*,*,*,*)) && args(*,arg,*,*) ", argNames = "arg")
    private void groupRelationMaybeCacheEvictAllOrSpecialPointcut(Object arg) {
    }


    /**
     * 7、用户
     * 修改时，如果组织机构/工作职务变了，仅需清自己的缓存
     */
    @Pointcut(value = "target(com.sishuok.es.sys.user.service.UserService)")
    private void userServicePointcut() {
    }

    @Pointcut(value = "execution(* delete(*)) && args(arg) || execution(* update(*)) && args(arg)", argNames = "arg")
    private void userCacheEvictSpecialPointcut(Object arg) {
    }


    @Pointcut(value = "target(com.sishuok.es.sys.auth.service.UserAuthService)")
    private void userAuthServicePointcut() {
    }

    @Pointcut(value = "execution(* findRoles(*)) && args(arg)", argNames = "arg")
    private void cacheFindRolesPointcut(User arg) {
    }

    @Pointcut(value = "execution(* findStringRoles(*)) && args(arg)", argNames = "arg")
    private void cacheFindStringRolesPointcut(User arg) {
    }

    @Pointcut(value = "execution(* findStringPermissions(*)) && args(arg)", argNames = "arg")
    private void cacheFindStringPermissionsPointcut(User arg) {
    }


    //////////////////////////////////////////////////////////////////////////////////
    ////增强
    //////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////
    ////查询时 查缓存/加缓存
    //////////////////////////////////////////////////////////////////////////////////
    @Around(value = "userAuthServicePointcut() && cacheFindRolesPointcut(arg)", argNames = "pjp,arg")
    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = null;
        if (user != null) {
            key = rolesKey(user.getId());
        }

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }

        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }

    @Around(value = "userAuthServicePointcut() && cacheFindStringRolesPointcut(arg)", argNames = "pjp,arg")
    public Object findStringRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = null;
        if (user != null) {
            key = stringRolesKey(user.getId());
        }

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findStringRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findStringRolesCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }


    @Around(value = "userAuthServicePointcut() && cacheFindStringPermissionsPointcut(arg)", argNames = "pjp,arg")
    public Object findStringPermissionsCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = stringPermissionsKey(user.getId());

        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findStringPermissionsCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findStringPermissionsCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        this.put(key, retVal);

        return retVal;
    }

    //////////////////////////////////////////////////////////////////////////////////
    ////清空整个缓存
    //////////////////////////////////////////////////////////////////////////////////
    @Before(
            "(authServicePointcut() && authCacheEvictAllPointcut()) " +
                    "|| (resourceServicePointcut() && resourceCacheEvictAllPointcut()) " +
                    "|| (permissionServicePointcut() && permissionCacheEvictAllPointcut()) " +
                    "|| (roleServicePointcut() && roleCacheEvictAllPointcut()) " +
                    "|| (organizationServicePointcut() && organizationCacheEvictAllPointcut()) " +
                    "|| (jobServicePointcut() && jobCacheEvictAllPointcut()) " +
                    "|| (groupServicePointcut() && groupCacheEvictAllPointcut()) " +
                    "|| (groupRelationServicePointcut() && groupRelationCacheEvictAllPointcut())"
    )
    public void cacheClearAllAdvice() {
        log.debug("cacheName:{}, method:cacheClearAllAdvice, cache clear", cacheName);
        clear();
    }

    //////////////////////////////////////////////////////////////////////////////////
    ////可能清空特定/全部缓存
    //////////////////////////////////////////////////////////////////////////////////

    /**
     * @param auth
     * @return 如果清空所有返回true 否则false
     */
    private boolean evictWithAuth(Auth auth) {
        boolean needEvictSpecail = auth != null && auth.getUserId() != null && auth.getGroupId() == null && auth.getOrganizationId() == null;
        if (needEvictSpecail) {
            Long userId = auth.getUserId();
            log.debug("cacheName:{}, method:evictWithAuth, evict userId:{}", cacheName, userId);
            evict(userId);
            return false;
        } else {
            log.debug("cacheName:{}, method:evictWithAuth, cache clear", cacheName);
            clear();
            return true;
        }
    }

    @Before(value = "authServicePointcut() && authCacheEvictAllOrSpecialPointcut(arg)", argNames = "jp,arg")
    public void authCacheClearSpecialOrAllAdvice(JoinPoint jp, Object arg) {
        log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice begin", cacheName);
        String methodName = jp.getSignature().getName();
        if (arg instanceof Auth) {//只清除某个用户的即可
            Auth auth = (Auth) arg;

            log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth", cacheName);
            evictWithAuth(auth);
        } else if ("delete".equals(methodName)) { //删除方法
            if (arg instanceof Long) { //删除单个
                Long authId = (Long) arg;
                Auth auth = authService.findOne(authId);

                log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth", cacheName);
                evictWithAuth(auth);
            } else if (arg instanceof Long[]) { //批量删除
                for (Long authId : ((Long[]) arg)) {
                    Auth auth = authService.findOne(authId);

                    log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice delegate to evictWithAuth", cacheName);
                    if (evictWithAuth(auth)) {//如果清空的是所有 直接返回
                        return;
                    }
                }
            } else if ("addUserAuth".equals(methodName)) {
                Long[] userIds = (Long[]) arg;

                log.debug("cacheName:{}, method:authCacheClearSpecialOrAllAdvice, evict user ids:{}",
                        cacheName, Arrays.toString(userIds));
                evict(userIds);
            }
        }
    }


    @Before(value = "resourceServicePointcut() && resourceMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void resourceMaybeCacheClearAllAdvice(Resource arg) {
        Resource resource = arg;
        if (resource == null) {
            return;
        }
        Resource dbResource = resourceService.findOne(resource.getId());
        if (dbResource == null) {
            return;
        }

        //只有当show/identity发生改变时才清理缓存
        if (!dbResource.getShow().equals(resource.getShow())
                || !dbResource.getIdentity().equals(resource.getIdentity())) {

            log.debug("cacheName:{}, method:resourceMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "permissionServicePointcut() && permissionMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void permissionMaybeCacheClearAllAdvice(Permission arg) {

        Permission permission = arg;
        if (permission == null) {
            return;
        }
        Permission dbPermission = permissionService.findOne(permission.getId());
        if (dbPermission == null) {
            return;
        }

        //只有当show/permission发生改变时才清理缓存
        if (!dbPermission.getShow().equals(permission.getShow())
                || !dbPermission.getPermission().equals(permission.getPermission())) {

            log.debug("cacheName:{}, method:permissionMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "roleServicePointcut() && roleMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void roleMaybeCacheClearAllAdvice(Role arg) {
        Role role = arg;
        if (role == null) {
            return;
        }
        Role dbRole = roleService.findOne(role.getId());
        if (dbRole == null) {
            return;
        }

        //只有当show/role发生改变时才清理缓存
        if (!dbRole.getShow().equals(role.getShow())
                || !dbRole.getRole().equals(role.getRole())
                || !(dbRole.getResourcePermissions().size() == role.getResourcePermissions().size()
                && dbRole.getResourcePermissions().containsAll(role.getResourcePermissions()))) {

            log.debug("cacheName:{}, method:roleMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }


    @Before(value = "organizationServicePointcut() && organizationMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void organizationMaybeCacheClearAllAdvice(Organization arg) {

        Organization organization = arg;
        if (organization == null) {
            return;
        }
        Organization dbOrganization = organizationService.findOne(organization.getId());
        if (dbOrganization == null) {
            return;
        }

        //只有当show/parentId发生改变时才清理缓存
        if (!dbOrganization.getShow().equals(organization.getShow())
                || !dbOrganization.getParentId().equals(organization.getParentId())) {

            log.debug("cacheName:{}, method:organizationMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }


    @Before(value = "jobServicePointcut() && jobMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void jobMaybeCacheClearAllAdvice(Job arg) {
        Job job = arg;
        if (job == null) {
            return;
        }
        Job dbJob = jobService.findOne(job.getId());
        if (dbJob == null) {
            return;
        }

        //只有当show/parentId发生改变时才清理缓存
        if (!dbJob.getShow().equals(job.getShow())
                || !dbJob.getParentId().equals(job.getParentId())) {

            log.debug("cacheName:{}, method:jobMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    @Before(value = "groupServicePointcut() && groupMaybeCacheEvictAllPointcut(arg)", argNames = "arg")
    public void groupMaybeCacheClearAllAdvice(Group arg) {
        Group group = arg;
        if (group == null) {
            return;
        }
        Group dbGroup = groupService.findOne(group.getId());
        if (dbGroup == null) {
            return;
        }

        //只有当修改组的默认组/show时才清理缓存
        if (!dbGroup.getShow().equals(group.getShow())
                || !dbGroup.getDefaultGroup().equals(group.getDefaultGroup())) {

            log.debug("cacheName:{}, method:groupMaybeCacheClearAllAdvice, cache clear", cacheName);
            clear();
        }
    }

    /**
     * @param r
     * @return 如果清除所有 返回true，否则false
     */
    private boolean evictForGroupRelation(GroupRelation r) {
        //如果是非某个用户，清理所有缓存
        if (r.getStartUserId() != null || r.getEndUserId() != null || r.getOrganizationId() != null) {

            log.debug("cacheName:{}, method:evictForGroupRelation, cache clear", cacheName);
            clear();
            return true;
        }
        if (r.getUserId() != null) {// 当添加/修改/删除的是某个用户的，只清理这个用户的缓存
            evict(r.getUserId());
            GroupRelation dbR = groupRelationService.findOne(r.getId());
            if (dbR != null && !dbR.getUserId().equals(r.getUserId())) { //如果a用户替换为b用户时清理两个用户的缓存

                log.debug("cacheName:{}, method:evictForGroupRelation, evict userId:{}", cacheName, dbR.getUserId());
                evict(dbR.getUserId());
            }
        }
        return false;
    }

    @Before(value = "groupRelationServicePointcut() && groupRelationMaybeCacheEvictAllOrSpecialPointcut(arg)", argNames = "jp,arg")
    public void groupRelationMaybeCacheClearAllOrSpecialAdvice(JoinPoint jp, Object arg) {
        String methodName = jp.getSignature().getName();
        if (arg instanceof GroupRelation) {
            GroupRelation r = (GroupRelation) arg;

            log.debug("cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation", cacheName);
            if (evictForGroupRelation(r)) {
                return;
            }

        } else if ("delete".equals(methodName)) {//删除情况
            if (arg instanceof Long) {
                Long rId = (Long) arg;
                GroupRelation r = groupRelationService.findOne(rId);

                log.debug("cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation", cacheName);
                if (evictForGroupRelation(r)) {
                    return;
                }
            } else if (arg instanceof Long[]) {
                for (Long rId : (Long[]) arg) {
                    GroupRelation r = groupRelationService.findOne(rId);

                    log.debug("cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice delagate to evictForGroupRelation", cacheName);
                    if (evictForGroupRelation(r)) {
                        return;
                    }
                }

            }
        } else if ("appendRelation".equals(methodName)) {//添加的情况
            Long[] userIds = (Long[]) arg;

            log.debug("cacheName:{}, method:groupRelationMaybeCacheClearAllOrSpecialAdvice, evict user ids:{}",
                    cacheName, Arrays.toString(userIds));
            evict(userIds);
        }
    }


    @Before(value = "userServicePointcut() && userCacheEvictSpecialPointcut(arg)", argNames = "jp,arg")
    public void userMaybeCacheClearSpecialAdvice(JoinPoint jp, Object arg) {
        String methodName = jp.getSignature().getName();
        if ("update".equals(methodName)) {
            User user = (User) arg;
            User dbUser = userService.findOne(user.getId());

            if (!(user.getOrganizationJobs().size() == dbUser.getOrganizationJobs().size()
                    && dbUser.getOrganizationJobs().containsAll(user.getOrganizationJobs()))) {

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user id:{}",
                        cacheName, user.getId());
                evict(user.getId());
            }


        } else if ("delete".equals(methodName)) {//删除情况
            if (arg instanceof Long) {
                Long userId = (Long) arg;

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user id:{}",
                        cacheName, userId);
                evict(userId);
            } else if (arg instanceof Long[]) {

                Long[] userIds = (Long[]) arg;

                log.debug("cacheName:{}, method:userMaybeCacheClearSpecialAdvice, evict user ids:{}",
                        cacheName, Arrays.toString(userIds));

                evict(userIds);
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////
    ////缓存相关
    //////////////////////////////////////////////////////////////////////////////////

    @Override
    public void clear() {
        super.clear();
        resourceMenuCacheAspect.clear();//当权限过期 同时清理菜单的
    }

    public void evict(Long[] userIds) {
        for (Long userId : userIds) {
            evict(userId);
        }
    }

    public void evict(Long userId) {
        evict(rolesKey(userId));
        evict(stringRolesKey(userId));
        evict(stringPermissionsKey(userId));

        resourceMenuCacheAspect.evict(userId);//当权限过期 同时清理菜单的
    }

    private String rolesKey(Long userId) {
        return this.rolesKeyPrefix + userId;
    }

    private String stringRolesKey(Long userId) {
        return this.stringRolesKeyPrefix + userId;
    }

    private String stringPermissionsKey(Long userId) {
        return this.stringPermissionsKeyPrefix + userId;
    }


}
