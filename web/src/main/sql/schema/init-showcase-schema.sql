#如果复制到mysql中执行时 加上
#DELIMITER ;;

drop table if exists `showcase_sample`;;
drop table if exists `showcase_moveable`;;
drop table if exists `showcase_upload`;;
drop table if exists `showcase_tree`;;
drop table if exists `showcase_parent`;;
drop table if exists `showcase_child`;;
drop table if exists `showcase_category`;;
drop table if exists `showcase_product`;;
drop table if exists `showcase_status_audit`;;
drop table if exists `showcase_status_show`;;
drop table if exists `showcase_editor`;;
drop table if exists `showcase_excel_data`;;
##showcase
create table `showcase_sample`(
    `id`         bigint not null auto_increment,
    `name`      varchar(100),
    `age`        smallint,
    `birthday`   timestamp default 0,
    `sex`        varchar(50),
    `is_show`       bool default false,
     `deleted`  bool default false,

    constraint `pk_showcase_sample` primary key(`id`),
    constraint `unique_showcase_sample_name` unique(`name`),
    index `idx_showcase_sample_birthday` (`birthday`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_moveable`(
    `id`         bigint not null auto_increment,
    `name`       varchar(100),
    `weight`    int default 0,
    `is_show`       bool default false,
    constraint `pk_showcase_moveable` primary key(`id`),
    index `idx_showcase_moveable_weight` (`weight`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_upload`(
    `id`         bigint not null auto_increment,
    `name`      varchar(100),
    `src`       varchar(500),
    constraint `pk_showcase_upload` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_tree`(
    `id`         bigint not null auto_increment,
    `name`      varchar(100),
    `parent_id` bigint,
    `parent_ids`  varchar(200) default '',
    `icon`       varchar(200),
    `weight`    int,
    `is_show`       bool,
    constraint `pk_showcase_tree` primary key(`id`),
    index idx_showcase_tree_parentId (`parent_id`),
    index idx_showcase_tree_parentIds_weight (`parent_ids`, `weight`)
) charset=utf8 ENGINE=InnoDB;;
alter table `showcase_tree` auto_increment=1000;;

create table `showcase_parent`(
  `id`          bigint not null auto_increment,
  `name`        varchar(100),
  `type`        varchar(50),
  `beginDate`  timestamp default 0,
  `endDate`    timestamp default 0,
  `is_show`        bool,
  constraint `pk_showcase_parent` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_child`(
  `id`          bigint not null auto_increment,
  `parent_id`  bigint unsigned,
  `name`        varchar(100),
  `type`        varchar(50),
  `beginTime`  time,
  `endTime`    time,
  `is_show`        bool,
  constraint `pk_showcase_child` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_category`(
  `id`          bigint not null auto_increment,
  `name`        varchar(100),
  `weight`      int default 0,
  `is_show`        bool,
  constraint `pk_showcase_category` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_product`(
  `id`             bigint not null auto_increment,
  `category_id`  bigint unsigned,
  `name`          varchar(100),
  `price`         bigint default 0,
  `number`        bigint default 0,
  `beginDate`    timestamp default 0 ,
  `endDate`      timestamp default 0 ,
  `is_show`        bool,
  constraint `pk_showcase_product` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_status_audit`(
  `id`         bigint not null auto_increment,
  `name`       varchar(100),
  `status`     varchar(100),
  `comment`    varchar(500),
  constraint `pk_showcase_status_audit` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_status_show`(
  `id`         bigint not null auto_increment,
  `name`       varchar(100),
  `status`     varchar(100),
  constraint `pk_showcase_status_show` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

create table `showcase_editor`(
  `id`         bigint not null auto_increment,
  `title`       varchar(500),
  `content`     longtext,
  constraint `pk_showcase_editor` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;


create table `showcase_excel_data`(
  `id`         bigint not null auto_increment,
  `content`   longtext,
  constraint `pk_excel_data` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;;

