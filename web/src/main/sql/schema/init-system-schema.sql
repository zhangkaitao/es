#如果复制到mysql中执行时 加上
#DELIMITER ;;

drop table if exists `sys_user`;;
drop table if exists `sys_user_status_history`;;
drop trigger if exists `trigger_sys_user_off_online`;;
drop table if exists `sys_user_online`;;
drop table if exists `sys_user_last_online`;;
drop table if exists `sys_organization`;;
drop table if exists `sys_job`;;
drop table if exists `sys_user_organization_job`;;
drop table if exists `sys_resource`;;
drop table if exists `sys_permission`;;
drop table if exists `sys_role`;;
drop table if exists `sys_role_resource_permission`;;
drop table if exists `sys_group`;;
drop table if exists `sys_group_relation`;;
drop table if exists `sys_auth`;;
##user
create table `sys_user`(
  `id`         bigint not null auto_increment,
  `username`  varchar(100),
  `email`  varchar(100),
  `mobile_phone_number`  varchar(20),
  `password`  varchar(100),
  `salt`       varchar(10),
  `create_date` timestamp default 0,
  `status`    varchar(50),
  `deleted`   bool,
  `admin`     bool,
  constraint `pk_sys_user` primary key(`id`),
  constraint `unique_sys_user_username` unique(`username`),
  constraint `unique_sys_user_email` unique(`email`),
  constraint `unique_sys_user_mobile_phone_number` unique(`mobile_phone_number`),
  index `idx_sys_user_status` (`status`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_user` auto_increment=1000;;

create table `sys_user_status_history`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `status`    varchar(50),
  `reason`     varchar(200),
  `op_user_id`  bigint,
  `op_date`    timestamp default 0 ,
  constraint `pk_sys_user_block_history` primary key(`id`),
  index `idx_sys_user_block_history_user_id_block_date` (`user_id`,`op_date`),
  index `idx_sys_user_block_history_op_user_id_op_date` (`op_user_id`, `op_date`)
) charset=utf8 ENGINE=InnoDB;;


create table `sys_user_online`(
  `id`         varchar(100) not null,
  `user_id`    bigint default 0,
  `username`    varchar(100),
  `host`  varchar(100),
  `system_host`  varchar(100),
  `user_agent` varchar(200),
  `status`  varchar(50),
  `start_timestsamp`    timestamp default 0 ,
  `last_access_time`    timestamp default 0 ,
  `timeout`    bigint ,
  `session` mediumtext,
  constraint `pk_sys_user_online` primary key(`id`),
  index `idx_sys_user_online_sys_user_id` (`user_id`),
  index `idx_sys_user_online_username` (`username`),
  index `idx_sys_user_online_host` (`host`),
  index `idx_sys_user_online_system_host` (`system_host`),
  index `idx_sys_user_online_start_timestsamp` (`start_timestsamp`),
  index `idx_sys_user_online_last_access_time` (`last_access_time`),
  index `idx_sys_user_online_user_agent` (`user_agent`)
) charset=utf8 ENGINE=InnoDB;;


create table `sys_user_last_online`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `username`    varchar(100),
  `uid`        varchar(100),
  `host`    varchar(100),
  `user_agent` varchar(200),
  `system_host`  varchar(100),
  `last_login_timestamp`    timestamp default 0 ,
  `last_stop_timestamp`    timestamp default 0 ,
  `login_count`    bigint ,
  `total_online_time` bigint,
  constraint `pk_sys_user_last_online` primary key(`id`),
  constraint `unique_sys_user_last_online_sys_user_id` unique(`user_id`),
  index `idx_sys_user_last_online_username` (`username`),
  index `idx_sys_user_last_online_host` (`host`),
  index `idx_sys_user_last_online_system_host` (`system_host`),
  index `idx_sys_user_last_online_last_login_timestamp` (`last_login_timestamp`),
  index `idx_sys_user_last_online_last_stop_timestamp` (`last_stop_timestamp`),
  index `idx_sys_user_last_online_user_agent` (`user_agent`)
) charset=utf8 ENGINE=InnoDB;;

create trigger `trigger_sys_user_off_online`
after delete on `sys_user_online` for each row
begin
   if OLD.`user_id` is not null then
      if not exists(select `user_id` from `sys_user_last_online` where `user_id` = OLD.`user_id`) then
        insert into `sys_user_last_online`
                  (`user_id`, `username`, `uid`, `host`, `user_agent`, `system_host`,
                   `last_login_timestamp`, `last_stop_timestamp`, `login_count`, `total_online_time`)
                values
                   (OLD.`user_id`,OLD.`username`, OLD.`id`, OLD.`host`, OLD.`user_agent`, OLD.`system_host`,
                    OLD.`start_timestsamp`, OLD.`last_access_time`,
                    1, (OLD.`last_access_time` - OLD.`start_timestsamp`));
      else
        update `sys_user_last_online`
          set `username` = OLD.`username`, `uid` = OLD.`id`, `host` = OLD.`host`, `user_agent` = OLD.`user_agent`,
            `system_host` = OLD.`system_host`, `last_login_timestamp` = OLD.`start_timestsamp`,
             `last_stop_timestamp` = OLD.`last_access_time`, `login_count` = `login_count` + 1,
             `total_online_time` = `total_online_time` + (OLD.`last_access_time` - OLD.`start_timestsamp`)
        where `user_id` = OLD.`user_id`;
      end if ;
   end if;
end;;


create table `sys_organization`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `type`      varchar(20),
  `parent_id` bigint,
  `parent_ids`  varchar(200) default '',
  `icon`       varchar(200),
  `weight`    int,
  `is_show`       bool,
  constraint `pk_sys_organization` primary key(`id`),
  index `idx_sys_organization_name` (`name`),
  index `idx_sys_organization_type` (`type`),
  index `idx_sys_organization_parent_id` (`parent_id`),
  index `idx_sys_organization_parent_ids_weight` (`parent_ids`, `weight`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_organization` auto_increment=1000;;


create table `sys_job`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `parent_id` bigint,
  `parent_ids`  varchar(200) default '',
  `icon`       varchar(200),
  `weight`    int,
  `is_show`       bool,
  constraint `pk_sys_job` primary key(`id`),
  index `idx_sys_job_nam` (`name`),
  index `idx_sys_job_parent_id` (`parent_id`),
  index `idx_sys_job_parent_ids_weight` (`parent_ids`, `weight`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_job` auto_increment=1000;;


create table `sys_user_organization_job`(
  `id`         bigint not null auto_increment,
  `user_id`   bigint,
  `organization_id` bigint,
  `job_id` bigint,
  constraint `pk_sys_user_organization_job` primary key(`id`),
  constraint `unique_sys_user_organization_job` unique(`user_id`, `organization_id`, `job_id`)
) charset=utf8 ENGINE=InnoDB;;

create table `sys_resource`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `identity`  varchar(100),
  `url`      varchar(200),
  `parent_id` bigint,
  `parent_ids`  varchar(200) default '',
  `icon`       varchar(200),
  `weight`    int,
  `is_show`       bool,
  constraint `pk_sys_resource` primary key(`id`),
  index `idx_sys_resource_name` (`name`),
  index `idx_sys_resource_identity` (`identity`),
  index `idx_sys_resource_user` (`url`),
  index `idx_sys_resource_parent_id` (`parent_id`),
  index `idx_sys_resource_parent_ids_weight` (`parent_ids`, `weight`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_resource` auto_increment=1000;;


create table `sys_permission`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `permission`  varchar(100),
  `description`      varchar(200),
  `is_show`       bool,
  constraint `pk_sys_permission` primary key(`id`),
  index idx_sys_permission_name (`name`),
  index idx_sys_permission_permission (`permission`),
  index idx_sys_permission_show (`is_show`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_permission` auto_increment=1000;;

create table `sys_role`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `role`  varchar(100),
  `description`      varchar(200),
  `is_show`       bool,
  constraint `pk_sys_role` primary key(`id`),
  index `idx_sys_role_name` (`name`),
  index `idx_sys_role_role` (`role`),
  index `idx_sys_role_show` (`is_show`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_role` auto_increment=1000;;


create table `sys_role_resource_permission`(
  `id`         bigint not null auto_increment,
  `role_id`   bigint,
  `resource_id` bigint,
  `permission_ids` varchar(500),
  constraint `pk_sys_role_resource_permission` primary key(`id`),
  constraint `unique_sys_role_resource_permission` unique(`role_id`, `resource_id`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_role_resource_permission` auto_increment=1000;;

create table `sys_group`(
  `id`         bigint not null auto_increment,
  `name`       varchar(100),
  `type`       varchar(50),
  `is_show`       bool,
  `default_group` bool,
  constraint `pk_sys_group` primary key(`id`),
  index `idx_sys_group_type` (`type`),
  index `idx_sys_group_show` (`is_show`),
  index `idx_sys_group_default_group` (`default_group`)
) charset=utf8 ENGINE=InnoDB;;


create table `sys_group_relation`(
  `id`         bigint not null auto_increment,
  `group_id`       bigint,
  `organization_id`        bigint,
  `user_id`        bigint,
  `start_user_id`        bigint,
  `end_user_id`        bigint,
  constraint `pk_sys_group_relation` primary key(`id`),
  index `idx_sys_group_relation_group` (`group_id`),
  index `idx_sys_group_relation_organization` (`organization_id`),
  index `idx_sys_group_relation_user` (`user_id`),
  index `idx_sys_group_relation_start_user_id` (`start_user_id`),
  index `idx_sys_group_relation_end_user_id` (`end_user_id`)
) charset=utf8 ENGINE=InnoDB;;



create table `sys_auth`(
  `id`         bigint not null auto_increment,
  `organization_id`       bigint,
  `job_id`       bigint,
  `user_id`        bigint,
  `group_id`       bigint,
  `role_ids`       varchar(500),
  `type`           varchar(50),
  constraint `pk_sys_auth` primary key(`id`),
  index `idx_sys_auth_organization` (`organization_id`),
  index `idx_sys_auth_job` (`job_id`),
  index `idx_sys_auth_user` (`user_id`),
  index `idx_sys_auth_group` (`group_id`),
  index `idx_sys_auth_type` (`type`)
) charset=utf8 ENGINE=InnoDB;;
alter table `sys_auth` auto_increment=1000;;

