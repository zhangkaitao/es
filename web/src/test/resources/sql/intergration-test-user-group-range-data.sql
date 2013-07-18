
--用户
insert into `sys_user`
  (`id`, `username`, `email`, `mobile_phone_number`, `password`, `salt`, `create_date`, `status`, `admin`, `deleted`)
values
  (1, 'admin', 'admin@sishuok.com', '13412345678', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', sysdate(), 'normal', 1, 0);

--分组
insert into `sys_group` (`id`, `name`, `type`, `is_show`, `default_group`)
   values(1, '默认', 'user', true, true);

insert into `sys_group` (`id`, `name`, `type`, `is_show`, `default_group`)
   values(2, '用户组', 'user', true, false);

insert into `sys_group_relation` (`id`, `group_id`, `start_user_id`, `end_user_id`)
    values(2, 2, 1, 10);

--用户组授权
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
    values(1, 0, 0, 0, 1, '1,3', 'user_group');
insert into sys_auth (`id`, `organization_id`, `job_id`, `user_id`, `group_id`, `role_ids`, `type`)
    values(2, 0, 0, 0, 2, '2,3', 'user_group');