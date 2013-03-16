drop table if exists `tbl_sys_user`;
drop table if exists `tbl_sys_user_status_history`;
drop table if exists `tbl_sys_user_online_info`;
##system
create table `tbl_sys_user`(
  `id`         bigint not null auto_increment,
  `username`  varchar(100),
  `password`  varchar(100),
  `salt`       varchar(10),
  `create_date` timestamp,
  `status`    varchar(50),
  `deleted`   bool,
  constraint `pk_sys_user` primary key(`id`),
  constraint `unique_sys_user_username` unique(`username`),
  index `idx_sys_user_status` (`status`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_sys_user_status_history`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `status`    varchar(50),
  `reason`     varchar(200),
  `op_user_id`  bigint,
  `op_date`    timestamp ,
  constraint `pk_sys_user_block_history` primary key(`id`),
  index `idx_sys_user_block_history_user_id_block_date` (`user_id`,`op_date`),
  index `idx_sys_user_block_history_op_user_id_op_date` (`op_user_id`, `op_date`)
) charset=utf8 ENGINE=InnoDB;


create table `tbl_sys_user_online_info`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `jsession_id` varchar(100),
  `sid`        varchar(100),
  `system_ip`  varchar(100),
  `user_ip`    varchar(100),
  `last_login_date`    timestamp ,
  `last_logout_date`    timestamp ,
  `login_count`    bigint ,
  `total_online_time` bigint,
  constraint `pk_sys_user_online_info` primary key(`id`),
  constraint unique_sys_user_online_info_sid unique(`sid`),
  index `idx_sys_user_online_info_user_id` (`user_id`),
  index `idx_sys_user_online_info_last_login_date` (`last_login_date`),
  index `idx_sys_user_online_info_last_logout_date` (`last_logout_date`),
  index `idx_sys_user_online_info_jsession_id` (`jsession_id`),
  index `idx_sys_user_online_info_user_ip` (`user_ip`),
  index `idx_sys_user_online_info_system_id` (`system_ip`)
) charset=utf8 ENGINE=InnoDB;

