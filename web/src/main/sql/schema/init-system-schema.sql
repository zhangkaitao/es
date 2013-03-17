drop table if exists `tbl_user`;
drop table if exists `tbl_user_status_history`;
drop table if exists `tbl_user_last_online_info`;
##user
create table `tbl_user`(
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

create table `tbl_user_status_history`(
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

create table `tbl_user_last_online_info`(
  `id`         bigint not null auto_increment,
  `user_id`    bigint,
  `jsession_id` varchar(100),
  `uid`        varchar(100),
  `system_ip`  varchar(100),
  `user_agent` varchar(200),
  `user_ip`    varchar(100),
  `last_login_date`    timestamp ,
  `last_logout_date`    timestamp ,
  `login_count`    bigint ,
  `total_online_time` bigint,
  constraint `pk_user_last_online_info` primary key(`id`),
  constraint `unique_user_last_online_info_uid` unique(`uid`),
  index `idx_user_last_online_info_local_jsession_id` (`local_jsession_id`),
  index `idx_user_last_online_info_user_id` (`user_id`),
  index `idx_user_last_online_info_user_ip` (`user_ip`),
  index `idx_user_last_online_infotem_id` (`system_ip`),
  index `idx_user_last_online_info_last_login_date` (`last_login_date`),
  index `idx_user_last_online_info_last_logout_date` (`last_logout_date`),
  index `idx_user_last_online_info_user_agent` (`user_agent`)
) charset=utf8 ENGINE=InnoDB;

