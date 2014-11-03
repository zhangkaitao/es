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
  (2, 'showcase', 'showcase@sishuok.com', '13412345672', '5f915c55c6d43da136a42e3ebabbecfc', 'hSSixwNQwt', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (3, 'sys', 'sys@sishuok.com', '13412345673', 'a10b3c7af051a81fe2506318f982ce28', 'MANHOoCpnb', sysdate(), 'normal', 1, 0);;

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (4, 'maintain', 'maintain@sishuok.com', '13412345674', '594813c5eb02b210dacc1a36c2482fc1', 'iY71e4dtoa', sysdate(), 'normal', 1, 0);;


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

insert into `sys_user`
(`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
  values
  (10, 'monitor', 'monitor@sishuok.com', '1341234580', 'e1549e68ad21fe888ae36ec4965116cd', 'iY71e4d123', sysdate(), 'normal', 1, 0);;


delete from `sys_organization` where id>=1 and id<=1000;;
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (1, 0, '0/', 1, '组织机构', true);;
delete from `sys_job` where id>=1 and id<=1;;
insert into `sys_job`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (1, 0, '0/', 1, '职务', true);;


delete from `sys_resource` where id>=1 and id<=1000;;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (1, 0, '0/', 1, '资源', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (2, 1, '0/1/', 1, '示例管理', 'showcase', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (3, 2, '0/1/2/', 1, '示例列表', 'sample', '/showcase/sample', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (4, 2, '0/1/2/', 2, '逻辑删除列表', 'deleted', '/showcase/deleted', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (5, 2, '0/1/2/', 3, '可移动列表', 'move', '/showcase/move', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (6, 2, '0/1/2/', 4, '文件上传列表', 'upload', '/showcase/upload', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (7, 2, '0/1/2/', 5, '树列表', 'tree', '/showcase/tree', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (8, 2, '0/1/2/', 6, '编辑器列表', 'editor', '/showcase/editor', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (9, 2, '0/1/2/', 7, '父子表（小数据量）', 'parentchild', '/showcase/parentchild/parent', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (10, 2, '0/1/2/', 8, '父子表（大数据量）管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (11, 10, '0/1/2/10/', 1, '类别列表', 'productCategory', '/showcase/product/category', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (12, 10, '0/1/2/10/', 2, '产品列表', 'product', '/showcase/product/product', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (13, 2, '0/1/2/', 9, '状态管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (14, 13, '0/1/2/13/', 1, '审核状态列表', 'statusAudit', '/showcase/status/audit', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (15, 13, '0/1/2/13/', 2, '显示状态列表', 'statusShow', '/showcase/status/show', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (46, 2, '0/1/2/', 10, 'Excel导入/导出', 'excel', '/showcase/excel', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (16, 1, '0/1/', 2, '系统管理', 'sys', '', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (17, 16, '0/1/16/', 1, '用户管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (18, 17, '0/1/16/17/', 1, '用户列表', 'user', '/admin/sys/user/main', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (19, 17, '0/1/16/17/', 2, '在线用户列表', 'userOnline', '/admin/sys/user/online', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (20, 17, '0/1/16/17/', 3, '状态变更历史列表', 'userStatusHistory', '/admin/sys/user/statusHistory', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (21, 17, '0/1/16/17/', 4, '用户最后在线历史列表', 'userLastOnline', '/admin/sys/user/lastOnline', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (22, 16, '0/1/16/', 2, '组织机构管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (23, 22, '0/1/16/22/', 1, '组织机构列表', 'organization', '/admin/sys/organization/organization', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (24, 22, '0/1/16/22/', 2, '工作职务列表', 'job', '/admin/sys/organization/job', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (29, 16, '0/1/16/', 3, '分组列表', 'group', '/admin/sys/group', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (25, 16, '0/1/16/', 4, '资源列表', 'resource', '/admin/sys/resource', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (26, 16, '0/1/16/', 5, '权限管理', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (27, 26, '0/1/16/26/', 1, '权限列表', 'permission', '/admin/sys/permission/permission', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (28, 26, '0/1/16/26/', 2, '授权权限给角色', 'role', '/admin/sys/permission/role', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (30, 26, '0/1/16/26/', 3, '授权角色给实体', 'auth', '/admin/sys/auth', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (31, 1, '0/1/', 4, '个人中心', '', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (32, 31, '0/1/31/', 1, '我的消息', '', '/admin/personal/message', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (47, 31, '0/1/31/', 2, '我的通知', '', '/admin/personal/notification', true);;


insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (33, 1, '0/1/', 5, '开发维护', 'maintain', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (48, 33, '0/1/33/', 1, '通知模板', 'notificationTemplate', '/admin/maintain/notification/template', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (34, 33, '0/1/33/', 2, '图标管理', 'icon', '/admin/maintain/icon', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (35, 33, '0/1/33/', 3, '键值对', 'keyvalue', '/admin/maintain/keyvalue', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (37, 33, '0/1/33/', 4, '静态资源版本控制', 'staticResource', '/admin/maintain/staticResource', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (38, 33, '0/1/33/', 5, '在线编辑', 'onlineEditor', '/admin/maintain/editor', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (49, 33, '0/1/33/', 6, '任务调度', 'dynamicTask', '/admin/maintain/dynamicTask', true);;

insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (39, 1, '0/1/', 6, '系统监控', 'monitor', '', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (40, 39, '0/1/39/', 1, '在线用户列表', 'userOnline', '/admin/sys/user/online', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (41, 39, '0/1/39/', 2, '数据库监控', 'db', '/admin/monitor/druid/index.html', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (42, 39, '0/1/39/', 3, 'hibernate监控', 'hibernate', '/admin/monitor/hibernate', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (43, 39, '0/1/39/', 4, '执行SQL/JPA QL', 'ql', '/admin/monitor/db/sql', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (44, 39, '0/1/39/', 5, 'ehcache监控', 'ehcache', '/admin/monitor/ehcache', true);;
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
  values (45, 39, '0/1/39/', 6, 'jvm监控', 'jvm', '/admin/monitor/jvm', true);;




delete from `sys_permission` where id>=1 and id<=1000;;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (1, '所有', '*', '所有数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (2, '新增', 'create', '新增数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (3,  '修改', 'update', '修改数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (4,  '删除', 'delete', '删除数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (5,  '查看', 'view', '查看数据操作的权限', 1);;
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (6,  '审核', 'audit', '审核数据操作的权限', 1);;

delete from `sys_role` where id>=1 and id<=1000;;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (1,  '超级管理员', 'admin', '拥有所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (2,  '示例管理员', 'example_admin', '拥有示例管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (3,  '系统管理员', 'sys_admin', '拥有系统管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (4,  '维护管理员', 'conf_admin', '拥有维护管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (5,  '新增管理员', 'create_admin', '拥有新增/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (6,  '修改管理员', 'update_admin', '拥有修改/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (7,  '删除管理员', 'delete_admin', '拥有删除/查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (8,  '查看管理员', 'view_admin', '拥有查看管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (9,  '审核管理员', 'audit_admin', '拥有审核管理的所有权限', 1);;
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (10,  '监控管理员', 'audit_admin', '拥有审核管理的所有权限', 1);;

delete from `sys_role_resource_permission` where id>=1 and id<=1000;;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(1, 1, 2, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(2, 1, 16, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(3, 1, 33, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(4, 1, 39, '1');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(5, 2, 2, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(6, 3, 16, '1');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(7, 4, 33, '1');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(8, 5, 2, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(9, 5, 16, '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(10, 5, 33,  '2,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(11, 5, 39,  '2,5');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(12, 6, 2, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(13, 6, 16, '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(14, 6, 33,  '3,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(15, 6, 39,  '3,5');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(16, 7, 2, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(17, 7, 16, '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(18, 7, 33,  '4,5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(19, 7, 39,  '4,5');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(20, 8, 2, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(21, 8, 16, '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(22, 8, 33,  '5');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(23, 8, 39,  '5');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(24, 9, 7, '5,6');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(25, 9, 14, '5,6');;
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(26, 9, 15, '5,6');;

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`) values(27, 10, 39, '1');;



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
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
  values(10, 0, 0, 10, 0, '10', 'user');;