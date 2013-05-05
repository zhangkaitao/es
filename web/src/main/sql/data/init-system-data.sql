delete from `sys_user` where id>=1 and id<=1000;;
/*默认admin/123456*/
insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345671', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (2, 'example', 'example@sishuok.com', '13412345672', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (3, 'sys', 'sys@sishuok.com', '13412345673', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (4, 'conf', 'conf@sishuok.com', '13412345674', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;

delete from `sys_organization` where id>=1 and id<=1000;;
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '组织机构', true);;
delete from `sys_job` where id>=1 and id<=1;;
insert into `sys_job`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '职务', true);;


delete from `sys_resource` where id>=1 and id<=1000;;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (1, 0, '0/', 1, '资源', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (2, 1, '0/1/', 1, '示例管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (3, 2, '0/1/2/', 1, '示例列表', 'showcase:example', '/showcase/sample', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (4, 2, '0/1/2/', 2, '逻辑删除列表', 'showcase:deleted', '/showcase/deleted', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (5, 2, '0/1/2/', 3, '可移动列表', 'showcase:move', '/showcase/move', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (6, 2, '0/1/2/', 4, '文件上传列表', 'showcase:upload', '/showcase/upload', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (7, 2, '0/1/2/', 5, '树列表', 'showcase:tree', '/showcase/tree', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (8, 2, '0/1/2/', 6, '编辑器列表', 'showcase:editor', '/showcase/editor', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (9, 2, '0/1/2/', 7, '父子表（小数据量）', 'showcase:parentchild', '/showcase/parentchild/parent', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (10, 2, '0/1/2/', 8, '父子表（大数据量）管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (11, 10, '0/1/2/10/', 1, '类别列表', 'showcase:productCategory', '/showcase/product/category', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (12, 10, '0/1/2/10/', 2, '产品列表', 'showcase:product', '/showcase/product/product', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (13, 2, '0/1/2/', 9, '状态管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (14, 13, '0/1/2/13/', 1, '审核状态列表', 'showcase:statusAudit', '/showcase/status/audit', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (15, 13, '0/1/2/13/', 2, '显示状态列表', 'showcase:statusShow', '/showcase/status/show', true);;


insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (16, 1, '0/1/', 2, '系统管理', '', '', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (17, 16, '0/1/16/', 1, '用户管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (18, 17, '0/1/16/17/', 1, '用户列表', 'sys:user', '/admin/sys/user/main', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (19, 17, '0/1/16/17/', 2, '在线用户列表', 'sys:userOnline', '/admin/sys/user/online', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (20, 17, '0/1/16/17/', 3, '状态变更历史列表', 'sys:userStatusHistory', '/admin/sys/user/statusHistory', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (21, 17, '0/1/16/17/', 4, '用户最后在线历史列表', 'sys:userLastOnline', '/admin/sys/user/lastOnline', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (22, 16, '0/1/16/', 2, '组织机构管理', '', '', true);;
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


delete from `sys_permission` where id>=1 and id<=1000;;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (1, '所有', '*', '所有数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (2, '新增', 'save', '新增数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (3,  '修改', 'update', '修改数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (4,  '删除', 'delete', '删除数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (5,  '查看', 'view', '查看数据操作的权限', 1);;

delete from `sys_role` where id>=1 and id<=1000;;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (1,  '管理员', 'admin', '拥有所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (2,  '示例管理员', 'example_admin', '拥有示例管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (3,  '系统管理员', 'sys_admin', '拥有系统管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (4,  '配置管理员', 'conf_admin', '拥有配置管理的所有权限', 1);;

delete from `sys_role_resource_permission` where id>=1 and id<=1000;;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(1, 2, 2, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(2, 2, 3, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(3, 2, 4, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(4, 2, 5, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(5, 2, 6, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(6, 2, 7, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(7, 2, 8, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(8, 2, 9, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(9, 2, 11, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(10, 2, 12, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(11, 2, 14, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(12, 2, 15, '1');;


insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(13, 3, 18, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(14, 3, 19, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(15, 3, 20, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(16, 3, 21, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(17, 3, 23, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(18, 3, 24, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(19, 3, 25, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(20, 3, 27, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(21, 3, 28, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(22, 3, 29, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(23, 3, 30, '1');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(24, 4, 32, '1');;



insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(25, 1, 2, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(26, 1, 3, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(27, 1, 4, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(28, 1, 5, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(29, 1, 6, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(30, 1, 7, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(31, 1, 8, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(32, 1, 9, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(33, 1, 11, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(34, 1, 12, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(35, 1, 14, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(36, 1, 15, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(37, 1, 18, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(38, 1, 19, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(39, 1, 20, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(40, 1, 21, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(41, 1, 23, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(42, 1, 24, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(43, 1, 25, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(44, 1, 27, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(45, 1, 28, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(46, 1, 29, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(47, 1, 30, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(48, 1, 32, '1');;

delete from `sys_auth` where id>=1 and id<=1000;;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(1, 0, 0, 1, 0, '1', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(2, 0, 0, 2, 0, '2', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(3, 0, 0, 3, 0, '3', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(4, 0, 0, 4, 0, '4', 'user');;
