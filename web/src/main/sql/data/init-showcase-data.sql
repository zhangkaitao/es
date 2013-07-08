#如果复制到mysql中执行时 加上
#DELIMITER ;;

delete from `showcase_tree` where id >=1 and id<=11;;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (1, 0, '0/', 1, '根', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (2, 1, '0/1/', 1, '节点1', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (3, 2, '0/1/2/', 1, '叶子11', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (4, 2, '0/1/2/', 2, '叶子12', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (5, 1, '0/1/', 2, '节点2', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (6, 5, '0/1/5/', 1, '叶子21', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (7, 1, '0/1/', 3, '节点3', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (8, 7, '0/1/7/', 2, '叶子31', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (9, 7, '0/1/7/', 1, '叶子32', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (10, 7, '0/1/7/', 3, '节点31', true);;
insert into `showcase_tree`(`id`, `parent_id`, `parent_ids`, weight, `name`, `is_show`) values (11, 10, '0/1/7/10/', 1, '叶子311', true);;