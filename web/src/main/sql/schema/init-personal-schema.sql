#如果复制到mysql中执行时 加上
#DELIMITER ;;

drop table if exists `personal_message`;;
drop table if exists `personal_message_content`;;

create table `personal_message`(
  `id`               bigint not null auto_increment,
  `sender_id`        bigint,
  `receiver_id`      bigint,
  `send_date`        timestamp ,
  `title`            varchar(500),
  `content_id`       bigint,
  `sender_state`     varchar(20),
  `sender_state_change_date`    timestamp ,
  `receiver_state`   varchar(20),
  `receiver_state_change_date`    timestamp ,
  `type`            varchar(20) ,
  `read`            bool ,
  `replied`          bool ,
  `parentIds`        varchar(500),

  constraint `pk_personal_message` primary key(`id`),
  index `idx_personal_message_sender_id_sender_state` (`sender_id`, `sender_state`),
  index `idx_personal_message_receiver_id_receiver_state` (`receiver_id`, `receiver_state`),
  index `idx_personal_message_type` (`type`)
) charset=utf8 ENGINE=InnoDB;;

create table `personal_message_content`(
  `id`               bigint not null auto_increment,
  `message_id`       bigint,
  `content`          longtext,
  constraint `pk_personal_message_content` primary key(`id`),
  index `idx_personal_message_content_message_id` (`message_id`)
) charset=utf8 ENGINE=InnoDB;;