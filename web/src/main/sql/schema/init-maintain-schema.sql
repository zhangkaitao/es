#如果复制到mysql中执行时 加上
#DELIMITER ;;

drop table if exists `maintain_icon`;;
drop table if exists `maintain_keyvalue`;;

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


create table `maintain_keyvalue`(
  `id`         bigint not null auto_increment,
  `key`  varchar(200),
  `value`  varchar(500),
  constraint `pk_maintain_keyvalue` primary key(`id`),
  constraint `unique_maintain_keyvalue_key` unique(`key`)
) charset=utf8 ENGINE=InnoDB;;
alter table `maintain_icon` auto_increment=2000;;