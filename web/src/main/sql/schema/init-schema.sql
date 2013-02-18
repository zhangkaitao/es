create table `tbl_sample`(
    `id`         bigint unsigned not null auto_increment,
    `name`      varchar(100),
    `age`        smallint,
    `birthday`   timestamp,
    `sex`        varchar(50),
    `show`       bool default false,

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
    `title`      varchar(100),
    `path`       varchar(200),
    `icon`       varchar(200),
    `show`       bool,
    constraint `pk_tree` primary key(`id`),
    index idx_path (`path`)
) charset=utf8 ENGINE=InnoDB;




