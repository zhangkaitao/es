create table `tbl_sample`(
    `id`         bigint unsigned not null auto_increment,
    `name`      varchar(100),
    `age`        smallint,
    `birthday`   timestamp,
    `sex`        varchar(50),
    `show`       bool default false,
     `deleted`  bool default false,

    constraint `pk_sample` primary key(`id`),
    constraint `unique_sample_name` unique(`name`),
    index `idx_sample_birthday` (`birthday`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_moveable`(
    `id`         bigint unsigned not null auto_increment,
    `name`       varchar(100),
    `weight`    int unsigned default 0,
    `show`       bool default false,
    constraint `pk_moveable` primary key(`id`),
    index `idx_moveable_weight` (`weight`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_upload`(
    `id`         bigint unsigned not null auto_increment,
    `name`      varchar(100),
    `src`       varchar(500),
    constraint `pk_upload` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_tree`(
    `id`         bigint unsigned not null auto_increment,
    `name`      varchar(100),
    `path`       varchar(200),
    `icon`       varchar(200),
    `show`       bool,
    constraint `pk_tree` primary key(`id`),
    index idx_path (`path`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_parent`(
  `id`          bigint unsigned not null auto_increment,
  `name`        varchar(100),
  `type`        varchar(50),
  `beginDate`  timestamp,
  `endDate`    timestamp,
  `show`        bool,
  constraint `pk_parent` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_child`(
  `id`          bigint unsigned not null auto_increment,
  `parent_id`  bigint unsigned,
  `name`        varchar(100),
  `type`        varchar(50),
  `beginTime`  time,
  `endTime`    time,
  `show`        bool,
  constraint `pk_child` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_category`(
  `id`          bigint unsigned not null auto_increment,
  `name`        varchar(100),
  `weight`      int unsigned default 0,
  `show`        bool,
  constraint `pk_category` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;

create table `tbl_product`(
  `id`             bigint unsigned not null auto_increment,
  `category_id`  bigint unsigned,
  `name`          varchar(100),
  `price`         bigint unsigned default 0,
  `number`        bigint unsigned default 0,
  `beginDate`    timestamp ,
  `endDate`      timestamp ,
  `show`        bool,
  constraint `pk_product` primary key(`id`)
) charset=utf8 ENGINE=InnoDB;
