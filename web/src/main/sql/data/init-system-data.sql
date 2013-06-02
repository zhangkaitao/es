#如果复制到mysql中执行时 加上
#DELIMITER ;;

delete from `sys_user` where id>=1 and id<=1000;;
/*默认admin/123456*/
insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345671', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (2, 'sample', 'sample@sishuok.com', '13412345672', '2bf1de3df3af11d3941a7a98dddb8392', 'hSSixwNQwt', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (3, 'sys', 'sys@sishuok.com', '13412345673', 'a10b3c7af051a81fe2506318f982ce28', 'MANHOoCpnb', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (4, 'conf', 'conf@sishuok.com', '13412345674', '9ff2950631346ced0d05d5fc031343dc', 'iY71e4dtoa', sysdate(), 'normal', 1, 0);;


insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (5, 'create', 'create@sishuok.com', '13412345675', 'a6d5988a698dec63c6eea71994dd7be0', 'iruPxupgfb', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (6, 'update', 'update@sishuok.com', '13412345676', 'fffa26ac5c47ec1bf9a37d9823816074', '2WQx5LmvlV', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (7, 'delete', 'delete@sishuok.com', '13412345677', '4c472bf1d56f440d2953803ab4eea8d4', 'E8KSvr1C7d', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (8, 'view', 'view@sishuok.com', '13412345678', 'c919215efcef4064858bf02f8776c00d', 'XFJZQOXWZW', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (9, 'audit', 'audit@sishuok.com', '13412345679', '15d8f7b8da8045d24c71a92a142ffad7', 'BI2XbXMUr7', sysdate(), 'normal', 1, 0);;


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
        values (3, 2, '0/1/2/', 1, '示例列表', 'showcase:sample', '/showcase/sample', true);;
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
        values (29, 16, '0/1/16/', 3, '分组列表', 'sys:group', '/admin/sys/group', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (25, 16, '0/1/16/', 4, '资源列表', 'sys:resource', '/admin/sys/resource', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (26, 16, '0/1/16/', 5, '权限管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (27, 26, '0/1/16/26/', 1, '权限列表', 'sys:permission', '/admin/sys/permission/permission', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (28, 26, '0/1/16/26/', 2, '授权权限给角色', 'sys:role', '/admin/sys/permission/role', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (30, 26, '0/1/16/26/', 3, '授权角色给实体', 'sys:auth', '/admin/sys/auth', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (31, 1, '0/1/', 3, '配置管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
        values (32, 31, '0/1/31/', 1, '图标管理', 'conf:icon', '/admin/conf/icon', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (33, 1, '0/1/', 4, '个人中心', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (34, 33, '0/1/33/', 1, '我的消息', '', '/admin/personal/message', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (35, 1, '0/1/', 5, '系统监控', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (36, 35, '0/1/35/', 1, '数据库监控', 'sys:monitor', '/admin/monitor/druid/index.html', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (37, 35, '0/1/35/', 2, 'hibernate监控', 'sys:monitor', '/admin/monitor/hibernate', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `show`)
         values (38, 35, '0/1/35/', 3, 'jmv监控', 'sys:monitor', '/admin/monitor/jvm', true);;



delete from `sys_permission` where id>=1 and id<=1000;;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (1, '所有', '*', '所有数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (2, '新增', 'create', '新增数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (3,  '修改', 'update', '修改数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (4,  '删除', 'delete', '删除数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (5,  '查看', 'view', '查看数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `show`) values (6,  '审核', 'audit', '审核数据操作的权限', 1);;

delete from `sys_role` where id>=1 and id<=1000;;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (1,  '超级管理员', 'admin', '拥有所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (2,  '示例管理员', 'example_admin', '拥有示例管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (3,  '系统管理员', 'sys_admin', '拥有系统管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (4,  '配置管理员', 'conf_admin', '拥有配置管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (5,  '新增管理员', 'create_admin', '拥有新增/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (6,  '修改管理员', 'update_admin', '拥有修改/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (7,  '删除管理员', 'delete_admin', '拥有删除/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (8,  '查看管理员', 'view_admin', '拥有查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `show`) values (9,  '审核管理员', 'audit_admin', '拥有审核管理的所有权限', 1);;

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
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(149, 1, 32, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(150, 1, 35, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(151, 1, 36, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(152, 1, 37, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(153, 1, 38, '1');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(48, 4, 32, '1');;


insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(49, 5, 2, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(50, 5, 3, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(51, 5, 4, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(52, 5, 5, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(53, 5, 6, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(54, 5, 7, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(55, 5, 8, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(56, 5, 9, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(57, 5, 11, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(58, 5, 12, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(59, 5, 14, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(60, 5, 15, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(61, 5, 18, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(62, 5, 19, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(63, 5, 20, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(64, 5, 21, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(65, 5, 23, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(66, 5, 24, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(67, 5, 25, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(68, 5, 27, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(69, 5, 28, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(70, 5, 29, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(72, 5, 30, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(73, 5, 32, '2,5');;



insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(74, 6, 2, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(75, 6, 3, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(76, 6, 4, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(77, 6, 5, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(78, 6, 6, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(79, 6, 7, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(80, 6, 8, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(81, 6, 9, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(82, 6, 11, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(83, 6, 12, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(84, 6, 14, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(85, 6, 15, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(86, 6, 18, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(87, 6, 19, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(88, 6, 20, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(89, 6, 21, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(90, 6, 23, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(91, 6, 24, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(92, 6, 25, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(93, 6, 27, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(94, 6, 28, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(95, 6, 29, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(96, 6, 30, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(97, 6, 32, '3,5');;



insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(98, 7, 2, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(99, 7, 3, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(100, 7, 4, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(101, 7, 5, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(102, 7, 6, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(103, 7, 7, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(104, 7, 8, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(105, 7, 9, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(106, 7, 11, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(107, 7, 12, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(108, 7, 14, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(109, 7, 15, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(110, 7, 18, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(111, 7, 19, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(112, 7, 20, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(113, 7, 21, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(114, 7, 23, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(115, 7, 24, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(116, 7, 25, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(117, 7, 27, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(118, 7, 28, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(119, 7, 29, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(120, 7, 30, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(121, 7, 32, '4,5');;


insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(122, 8, 2, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(123, 8, 3, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(124, 8, 4, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(125, 8, 5, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(126, 8, 6, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(127, 8, 7, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(128, 8, 8, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(129, 8, 9, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(130, 8, 11, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(131, 8, 12, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(132, 8, 14, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(133, 8, 15, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(134, 8, 18, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(135, 8, 19, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(136, 8, 20, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(137, 8, 21, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(138, 8, 23, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(139, 8, 24, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(140, 8, 25, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(141, 8, 27, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(142, 8, 28, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(143, 8, 29, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(144, 8, 30, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(145, 8, 32, '5');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(146, 9, 7, '5,6');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(147, 9, 14, '5,6');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(148, 9, 15, '5,6');;





delete from `sys_auth` where id>=1 and id<=1000;;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(1, 0, 0, 1, 0, '1', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(2, 0, 0, 2, 0, '2', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(3, 0, 0, 3, 0, '3', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(4, 0, 0, 4, 0, '4', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(5, 0, 0, 5, 0, '5', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(6, 0, 0, 6, 0, '6', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(7, 0, 0, 7, 0, '7', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(8, 0, 0, 8, 0, '8', 'user');;
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(9, 0, 0, 9, 0, '9', 'user');;