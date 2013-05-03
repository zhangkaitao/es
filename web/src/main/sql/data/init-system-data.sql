delete from `sys_user` where id>=1 and id<=1;;
/*默认admin/123456*/
insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345678', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;


delete from `sys_organization` where id>=1 and id<=1;;
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '组织机构', true);;
delete from `sys_job` where id>=1 and id<=1;;
insert into `sys_job`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '职务', true);;


delete from `sys_resource` where id>=1 and id<=11;;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (1, 0, '0/', 1, '资源', '', '', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (2, 1, '0/1/', 1, '示例管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (3, 2, '0/1/2/', 1, '示例列表', 'example:example', '/showcase/sample', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (4, 2, '0/1/2/', 2, '逻辑删除列表', 'example:deleted', '/showcase/deleted', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (5, 2, '0/1/2/', 3, '可移动列表', 'example:move', '/showcase/move', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (6, 2, '0/1/2/', 4, '文件上传列表', 'example:upload', '/showcase/upload', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (7, 2, '0/1/2/', 5, '树列表', 'example:tree', '/showcase/tree', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (8, 2, '0/1/2/', 6, '编辑器列表', 'example:editor', '/showcase/editor', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (9, 2, '0/1/2/', 7, '父子表（小数据量）', 'example:parentchild', '/showcase/parentchild/parent', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (10, 2, '0/1/2/', 8, '父子表（大数据量）管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (11, 10, '0/1/2/10/', 1, '类别列表', 'example:product:category', '/showcase/product/category', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (12, 10, '0/1/2/10/', 2, '产品列表', 'example:product:product', '/showcase/product/product', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (13, 2, '0/1/2/', 9, '状态管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (14, 13, '0/1/2/13/', 1, '审核状态列表', 'example:status:audit', '/showcase/status/audit', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (15, 13, '0/1/2/13/', 2, '显示状态列表', 'example:status:show', '/showcase/status/show', true);;


insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (16, 1, '0/1/', 2, '系统管理', '', '', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (17, 16, '0/1/16/', 1, '用户管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (18, 17, '0/1/16/17/', 1, '用户列表', 'sys:user', '/admin/sys/user/main', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (19, 17, '0/1/16/17/', 2, '在线用户列表', 'sys:user:online', '/admin/sys/user/online', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (20, 17, '0/1/16/17/', 3, '状态变更历史列表', 'sys:user:statusHistory', '/admin/sys/user/statusHistory', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (21, 17, '0/1/16/17/', 4, '用户最后在线历史列表', 'sys:user:lastOnline', '/admin/sys/user/lastOnline', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (22, 16, '0/1/16/', 2, '组织机构滚傈', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (23, 22, '0/1/16/22/', 1, '组织机构列表', 'sys:organization', '/admin/sys/organization/organization', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (24, 22, '0/1/16/22/', 2, '工作职务列表', 'sys:job', '/admin/sys/organization/job', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (25, 16, '0/1/16/', 3, '资源列表', 'sys:resource', '/admin/sys/resource', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (26, 16, '0/1/16/', 4, '权限管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (27, 26, '0/1/16/26/', 1, '权限列表', 'sys:permission', '/admin/sys/permission/permission', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (28, 26, '0/1/16/26/', 2, '角色列表', 'sys:role', '/admin/sys/permission/role', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (29, 16, '0/1/16/', 5, '分组列表', 'sys:group', '/admin/sys/group', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (30, 16, '0/1/16/', 6, '授权列表', 'sys:auth', '/admin/sys/auth', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (31, 1, '0/1/', 3, '配置管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (32, 31, '0/1/31/', 1, '图标管理', 'conf:icon', '/admin/conf/icon', true);;


insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (1, '所有', 'all', '所有数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (2, '新增', 'save', '新增数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (3,  '修改', 'update', '修改数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (4,  '删除', 'delete', '删除数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (5,  '查看', 'view', '查看数据操作的权限', 1);;

insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (1,  '管理员', 'admin', '拥有所有权限', 1);;
