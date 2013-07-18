
--用户
insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345678', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);

--组织机构
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (1, 0, '0/', 1, '组织机构', true);
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (2, 1, '0/1/', 1, '组织机构2', true);
insert into `sys_organization`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (3, 1, '0/', 1, '组织机构3', false);

--用户--组织机构--工作职务关系
insert into `sys_user_organization_job` (`id`, `user_id`, `organization_id`, `job_id`) values(1, 1, 2, 0);
insert into `sys_user_organization_job` (`id`, `user_id`, `organization_id`, `job_id`) values(2, 1, 3, 0);

--组织机构单独授权
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
    values(1, 2, 0, 0, 0, '1', 'organization_job');
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
    values(2, 3, 0, 0, 0, '3', 'organization_job');
