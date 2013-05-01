drop table if exists `conf_icon`;

create table `conf_icon`(
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
  constraint `pk_conf_icon` primary key(`id`),
  constraint `unique_conf_icon_identity` unique(`identity`)
) charset=utf8 ENGINE=InnoDB;
alter table `sys_user` auto_increment=2000;