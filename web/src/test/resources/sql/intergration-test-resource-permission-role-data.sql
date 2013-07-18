
--资源
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
        values (1, 0, '0/', 1, '示例列表', 'example:example', '/showcase/sample', true);
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
        values (2, 0, '0/', 2, '逻辑删除列表', 'example:deleted', '/showcase/deleted', false);
insert into `sys_resource`(`id`, `parent_id`, `parent_ids`, weight, `name`, `identity`, `url`, `is_show`)
        values (3, 0, '0/', 4, '文件上传列表', 'example:upload', '/showcase/upload', true);

--权限
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (1, '所有', 'all', '所有数据操作的权限', 1);
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (2, '新增', 'save', '新增数据操作的权限', 1);
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (3,  '修改', 'update', '修改数据操作的权限', 1);
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (4,  '删除', 'delete', '删除数据操作的权限', 1);
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (5,  '查看', 'view', '查看数据操作的权限', 1);
insert into `sys_permission` (`id`, `name`, `permission`, `description`, `is_show`) values (6,  '不显示的权限', 'none', '不显示的权限', 0);

--角色
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (1,  '管理员', 'admin', '拥有所有权限', 1);
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (2,  '测试人员', 'test', '测试人员', 1);
insert into `sys_role` (`id`, `name`, `role`, `description`, `is_show`) values (3,  '不显示的角色', 'none', '测试人员', 0);

--角色--资源--权限
insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`)
    values(1, 1, 1, '1,2,6');

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`)
    values(2, 1, 2, '1,3,5');

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`)
    values(3, 2, 3, '1,3,6');

insert into `sys_role_resource_permission` (`id`, `role_id`, `resource_id`, `permission_ids`)
    values(4, 3, 1, '1,4,6');
