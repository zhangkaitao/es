#如果复制到mysql中执行时 加上
#DELIMITER ;;

drop table if exists `maintain_icon`;;
drop table if exists `maintain_map`;;
drop table if exists `maintain_notification_template`;;
drop table if exists `maintain_notification_data`;;
drop table if exists `maintain_task_definition`;;

create table `maintain_icon`(
  `id`         bigint not null auto_increment,
  `identity`  varchar(100),
  `css_class`  varchar(100),
  `img_src`  varchar(200),
  `width`     smallint,
  `height`   smallint,
  `sprite_src`  varchar(200),
  `left`     smallint,
  `top`      smallint,
  `style`    varchar(100),
  `type`   varchar(30),
  `description`  varchar(100),
  constraint `pk_maintain_icon` primary key(`id`),
  constraint `unique_maintain_icon_identity` unique(`identity`)
) charset=utf8 ENGINE=InnoDB;;
alter table `maintain_icon` auto_increment=2000;;


create table `maintain_map`(
  `id`         bigint not null auto_increment,
  `map_key`  varchar(200),
  `map_value`  varchar(500),
  constraint `pk_maintain_map` primary key(`id`),
  constraint `unique_maintain_map_key` unique(`map_key`)
) charset=utf8 ENGINE=InnoDB;;
alter table `maintain_map` auto_increment=2000;;

create table `maintain_notification_template`(
  `id`               bigint not null auto_increment,
  `name`            varchar(200),
  `system`           varchar(50),
  `title`         varchar(600),
  `template`         varchar(2000),
  `deleted`          bool,

  constraint `pk_maintain_notification_template` primary key(`id`),
  constraint `unique_maintain_notification_template_name` unique(`name`)
) charset=utf8 ENGINE=InnoDB;;
alter table `maintain_notification_template` auto_increment=2000;;

create table `maintain_notification_data`(
  `id`               bigint not null auto_increment,
  `user_id`          bigint,
  `system`           varchar(50),
  `title`          varchar(600),
  `content`          varchar(2000),
  `date`  timestamp default 0,
  `is_read`            bool,

  constraint `pk_maintain_notification_data` primary key(`id`),
  index `idx_maintain_notification_data_user_id_read` (`user_id`, `is_read`)
) charset=utf8 ENGINE=InnoDB;;

create table `maintain_task_definition`(
  `id`               bigint not null auto_increment,
  `name`           varchar(500),
  `cron`            varchar(200),
  `bean_class`      varchar(200),
  `bean_name`       varchar(200),
  `method_name`     varchar(200),
  `is_start`        bool,
  `description`     varchar(2000),

  constraint `pk_maintain_task_definition` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

