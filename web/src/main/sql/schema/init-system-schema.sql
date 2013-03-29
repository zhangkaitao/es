drop table if exists `user`;
drop table if exists `user_status_history`;
drop trigger if exists `trigger_user_off_online`;
drop table if exists `user_online`;
drop table if exists `user_last_online`;
drop table if exists `menu`;
##user
create table `user`(
  `id`         bigint not null auto_increment,
  `username`  varchar(100),
  `email`  varchar(100),
  `mobile_phone_number`  varchar(20),
  `password`  varchar(100),
  `salt`       varchar(10),
  `create_date` timestamp,
  `status`    varchar(50),
  `deleted`   bool,
  `admin`     bool,
  constraint `pk_user` primary key(`id`),
  constraint `unique_user_username` unique(`username`),
  constraint `unique_user_email` unique(`email`),
  constraint `unique_user_mobile_phone_number` unique(`mobile_phone_number`),
  index `idx_user_status` (`status`)
) charset=utf8 ENGINE=InnoDB;

create table `user_status_history`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `status`    varchar(50),
  `reason`     varchar(200),
  `op_user_id`  bigint,
  `op_date`    timestamp ,
  constraint `pk_user_block_history` primary key(`id`),
  index `idx_user_block_history_user_id_block_date` (`user_id`,`op_date`),
  index `idx_user_block_history_op_user_id_op_date` (`op_user_id`, `op_date`)
) charset=utf8 ENGINE=InnoDB;


create table `user_online`(
  `id`         varchar(100) not null,
  `user_id`    bigint default 0,
  `username`    varchar(100),
  `host`  varchar(100),
  `system_host`  varchar(100),
  `user_agent` varchar(200),
  `status`  varchar(50),
  `start_timestsamp`    timestamp ,
  `last_access_time`    timestamp ,
  `timeout`    bigint ,
  `session` mediumtext,
  constraint `pk_user_online` primary key(`id`),
  index `idx_user_online_user_id` (`user_id`),
  index `idx_user_online_username` (`username`),
  index `idx_user_online_host` (`host`),
  index `idx_user_online_system_host` (`system_host`),
  index `idx_user_online_start_timestsamp` (`start_timestsamp`),
  index `idx_user_online_last_access_time` (`last_access_time`),
  index `idx_user_online_user_agent` (`user_agent`)
) charset=utf8 ENGINE=InnoDB;


create table `user_last_online`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `username`    varchar(100),
  `uid`        varchar(100),
  `host`    varchar(100),
  `user_agent` varchar(200),
  `system_host`  varchar(100),
  `last_login_timestamp`    timestamp ,
  `last_stop_timestamp`    timestamp ,
  `login_count`    bigint ,
  `total_online_time` bigint,
  constraint `pk_user_last_online` primary key(`id`),
  constraint `unique_user_last_online_user_id` unique(`user_id`),
  index `idx_user_last_online_username` (`username`),
  index `idx_user_last_online_host` (`host`),
  index `idx_user_last_online_system_host` (`system_host`),
  index `idx_user_last_online_last_login_timestamp` (`last_login_timestamp`),
  index `idx_user_last_online_last_stop_timestamp` (`last_logout_timestamp`),
  index `idx_user_last_online_user_agent` (`user_agent`)
) charset=utf8 ENGINE=InnoDB;

create trigger `trigger_user_off_online`
after delete
on `user_online`for each row
begin
   if OLD.`user_id` is not null then
      if not exists(select `user_id` from `user_last_online` where `user_id` = OLD.`user_id`) then
        insert into `user_last_online`
                  (`user_id`, `username`, `uid`, `host`, `user_agent`, `system_host`,
                   `last_login_timestamp`, `last_stop_timestamp`, `login_count`, `total_online_time`)
                values
                   (OLD.`user_id`,OLD.`username`, OLD.`id`, OLD.`host`, OLD.`user_agent`, OLD.`system_host`,
                    OLD.`start_timestsamp`, OLD.`last_access_time`,
                    1, (OLD.`last_access_time` - OLD.`start_timestsamp`));
      else
        update `user_last_online`
          set `username` = OLD.`username`, `uid` = OLD.`id`, `host` = OLD.`host`, `user_agent` = OLD.`user_agent`,
            `system_host` = OLD.`system_host`, `last_login_timestamp` = OLD.`start_timestsamp`,
             `last_stop_timestamp` = OLD.`last_access_time`, `login_count` = `login_count` + 1,
             `total_online_time` = `total_online_time` + (OLD.`last_access_time` - OLD.`start_timestsamp`)
        where `user_id` = OLD.`user_id`;
      end if ;
   end if;
end;


create table `menu`(
  `id`         bigint not null auto_increment,
  `name`      varchar(100),
  `parent_id` bigint,
  `parent_ids`  varchar(200) default '',
  `icon`       varchar(200),
  `weight`    int,
  `show`       bool,
  constraint `pk_menu` primary key(`id`),
  index idx_menu_parentId (`parent_id`),
  index idx_menu_parentIds_weight (`parent_ids`, `weight`)
) charset=utf8 ENGINE=InnoDB;
alter table `menu` auto_increment=1000;