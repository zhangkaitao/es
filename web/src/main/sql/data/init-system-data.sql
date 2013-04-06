insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345678', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);

insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '根', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (2, 1, '0/1/', 1, '节点1', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (3, 2, '0/1/2/', 1, '叶子11', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (4, 2, '0/1/2/', 2, '叶子12', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (5, 1, '0/1/', 2, '节点2', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (6, 5, '0/1/5/', 1, '叶子21', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (7, 1, '0/1/', 3, '节点3', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (8, 7, '0/1/7/', 2, '叶子31', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (9, 7, '0/1/7/', 1, '叶子32', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (10, 7, '0/1/7/', 3, '节点31', true);
insert into `sys_resources_menu`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (11, 10, '0/1/7/10/', 1, '叶子311', true);


insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `type`, `show`) values (1, 0, '0/', 1, '组织机构', 'bloc', true);
insert into `sys_job`(`id`, `parent_id`, `parent_ids`, weight, `name`, `show`) values (1, 0, '0/', 1, '职务', true);