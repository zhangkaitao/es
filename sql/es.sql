/*
Navicat MySQL Data Transfer

Source Server         : xxs
Source Server Version : 50154
Source Host           : localhost:3306
Source Database       : es

Target Server Type    : MYSQL
Target Server Version : 50154
File Encoding         : 65001

Date: 2014-11-01 01:37:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for maintain_map
-- ----------------------------
DROP TABLE IF EXISTS `maintain_map`;
CREATE TABLE `maintain_map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `map_key` varchar(200) DEFAULT NULL,
  `map_value` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_maintain_map_key` (`map_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain_map
-- ----------------------------

-- ----------------------------
-- Table structure for maintain_notification_data
-- ----------------------------
DROP TABLE IF EXISTS `maintain_notification_data`;
CREATE TABLE `maintain_notification_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `system` varchar(50) DEFAULT NULL,
  `title` varchar(600) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_read` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_maintain_notification_data_user_id_read` (`user_id`,`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain_notification_data
-- ----------------------------

-- ----------------------------
-- Table structure for maintain_notification_template
-- ----------------------------
DROP TABLE IF EXISTS `maintain_notification_template`;
CREATE TABLE `maintain_notification_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `system` varchar(50) DEFAULT NULL,
  `title` varchar(600) DEFAULT NULL,
  `template` varchar(2000) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_maintain_notification_template_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain_notification_template
-- ----------------------------
INSERT INTO `maintain_notification_template` VALUES ('1', 'excelInitDataSuccess', 'excel', '初始化Excel数据完成', '初始化Excel数据已完成，耗时{seconds}秒钟，可以尝试导入/导出操作啦！', '0');
INSERT INTO `maintain_notification_template` VALUES ('2', 'excelImportSuccess', 'excel', '导入Excel成功', '导入Excel成功，耗时{seconds}秒钟，<a onclick=\"$($.find(\'#menu a:contains(Excel导入/导出)\')).click();$(\'.notification-list .close-notification-list\').click();\">点击前往查看</a>', '0');
INSERT INTO `maintain_notification_template` VALUES ('3', 'excelImportError', 'excel', '导入Excel失败', '导入Excel失败了，请把错误报告给管理员，可能的失败原因：文件格式不对；错误码：{error}', '0');
INSERT INTO `maintain_notification_template` VALUES ('4', 'excelExportSuccess', 'excel', '导出Excel成功', '导出Excel成功，耗时{seconds}秒钟，<a href=\"{ctx}{url}\" target=\"_blank\">点击下载</a>（注意：导出的文件只保留3天，请尽快下载，过期将删除）', '0');
INSERT INTO `maintain_notification_template` VALUES ('5', 'excelExportError', 'excel', '导出Excel失败', '导出Excel失败了，请把错误报告给管理员，可能的失败原因：文件格式不对；错误码：{error}', '0');

-- ----------------------------
-- Table structure for maintain_task_definition
-- ----------------------------
DROP TABLE IF EXISTS `maintain_task_definition`;
CREATE TABLE `maintain_task_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `cron` varchar(100) DEFAULT NULL,
  `bean_class` varchar(100) DEFAULT NULL,
  `bean_name` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  `is_start` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain_task_definition
-- ----------------------------

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `remarks` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('2', '小明', '爱迪生发送到发送到');
INSERT INTO `member` VALUES ('3', '小王', '阿迪发送到');

-- ----------------------------
-- Table structure for personal_calendar
-- ----------------------------
DROP TABLE IF EXISTS `personal_calendar`;
CREATE TABLE `personal_calendar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `details` varchar(1000) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `background_color` varchar(100) DEFAULT NULL,
  `text_color` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `office_calendar_user_id_start_date` (`user_id`,`start_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_calendar
-- ----------------------------

-- ----------------------------
-- Table structure for personal_message
-- ----------------------------
DROP TABLE IF EXISTS `personal_message`;
CREATE TABLE `personal_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` bigint(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `send_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `title` varchar(200) DEFAULT NULL,
  `sender_state` varchar(20) DEFAULT NULL,
  `sender_state_change_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `receiver_state` varchar(20) DEFAULT NULL,
  `receiver_state_change_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` varchar(20) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT NULL,
  `is_replied` tinyint(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_personal_message_sender_id_sender_state` (`sender_id`,`sender_state`),
  KEY `idx_personal_message_receiver_id_receiver_state` (`receiver_id`,`receiver_state`,`is_read`),
  KEY `idx_personal_sender_state_change_date` (`sender_state_change_date`),
  KEY `idx_personal_receiver_state_change_date` (`receiver_state_change_date`),
  KEY `idx_personal_parent_id` (`parent_id`),
  KEY `idx_personal_parent_ids` (`parent_ids`),
  KEY `idx_personal_message_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_message
-- ----------------------------

-- ----------------------------
-- Table structure for personal_message_content
-- ----------------------------
DROP TABLE IF EXISTS `personal_message_content`;
CREATE TABLE `personal_message_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`id`),
  KEY `idx_personal_message_content_message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_message_content
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_category
-- ----------------------------
DROP TABLE IF EXISTS `showcase_category`;
CREATE TABLE `showcase_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `weight` int(11) DEFAULT '0',
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_category
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_child
-- ----------------------------
DROP TABLE IF EXISTS `showcase_child`;
CREATE TABLE `showcase_child` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `beginTime` time DEFAULT NULL,
  `endTime` time DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_child
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_editor
-- ----------------------------
DROP TABLE IF EXISTS `showcase_editor`;
CREATE TABLE `showcase_editor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_editor
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_excel_data
-- ----------------------------
DROP TABLE IF EXISTS `showcase_excel_data`;
CREATE TABLE `showcase_excel_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_excel_data
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_moveable
-- ----------------------------
DROP TABLE IF EXISTS `showcase_moveable`;
CREATE TABLE `showcase_moveable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `weight` int(11) DEFAULT '0',
  `is_show` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_showcase_moveable_weight` (`weight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_moveable
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_parent
-- ----------------------------
DROP TABLE IF EXISTS `showcase_parent`;
CREATE TABLE `showcase_parent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `beginDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_parent
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_product
-- ----------------------------
DROP TABLE IF EXISTS `showcase_product`;
CREATE TABLE `showcase_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` bigint(20) DEFAULT '0',
  `number` bigint(20) DEFAULT '0',
  `beginDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_product
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_sample
-- ----------------------------
DROP TABLE IF EXISTS `showcase_sample`;
CREATE TABLE `showcase_sample` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `age` smallint(6) DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sex` varchar(50) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT '0',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_showcase_sample_name` (`name`),
  KEY `idx_showcase_sample_birthday` (`birthday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_sample
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_status_audit
-- ----------------------------
DROP TABLE IF EXISTS `showcase_status_audit`;
CREATE TABLE `showcase_status_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_status_audit
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_status_show
-- ----------------------------
DROP TABLE IF EXISTS `showcase_status_show`;
CREATE TABLE `showcase_status_show` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_status_show
-- ----------------------------

-- ----------------------------
-- Table structure for showcase_tree
-- ----------------------------
DROP TABLE IF EXISTS `showcase_tree`;
CREATE TABLE `showcase_tree` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_showcase_tree_parentId` (`parent_id`),
  KEY `idx_showcase_tree_parentIds_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_tree
-- ----------------------------
INSERT INTO `showcase_tree` VALUES ('1', '根', '0', '0/', null, '1', '1');
INSERT INTO `showcase_tree` VALUES ('2', '节点1', '1', '0/1/', null, '1', '1');
INSERT INTO `showcase_tree` VALUES ('3', '叶子11', '2', '0/1/2/', null, '1', '1');
INSERT INTO `showcase_tree` VALUES ('4', '叶子12', '2', '0/1/2/', null, '2', '1');
INSERT INTO `showcase_tree` VALUES ('5', '节点2', '1', '0/1/', null, '2', '1');
INSERT INTO `showcase_tree` VALUES ('6', '叶子21', '5', '0/1/5/', null, '1', '1');
INSERT INTO `showcase_tree` VALUES ('7', '节点3', '1', '0/1/', null, '3', '1');
INSERT INTO `showcase_tree` VALUES ('8', '叶子31', '7', '0/1/7/', null, '2', '1');
INSERT INTO `showcase_tree` VALUES ('9', '叶子32', '7', '0/1/7/', null, '1', '1');
INSERT INTO `showcase_tree` VALUES ('10', '节点31', '7', '0/1/7/', null, '3', '1');
INSERT INTO `showcase_tree` VALUES ('11', '叶子311', '10', '0/1/7/10/', null, '1', '1');

-- ----------------------------
-- Table structure for showcase_upload
-- ----------------------------
DROP TABLE IF EXISTS `showcase_upload`;
CREATE TABLE `showcase_upload` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `src` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of showcase_upload
-- ----------------------------

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `role_ids` varchar(500) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_auth_organization` (`organization_id`),
  KEY `idx_sys_auth_job` (`job_id`),
  KEY `idx_sys_auth_user` (`user_id`),
  KEY `idx_sys_auth_group` (`group_id`),
  KEY `idx_sys_auth_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES ('1', '0', '0', '1', '0', '1', 'user');
INSERT INTO `sys_auth` VALUES ('2', '0', '0', '2', '0', '2', 'user');
INSERT INTO `sys_auth` VALUES ('3', '0', '0', '3', '0', '3', 'user');
INSERT INTO `sys_auth` VALUES ('4', '0', '0', '4', '0', '4', 'user');
INSERT INTO `sys_auth` VALUES ('5', '0', '0', '5', '0', '5', 'user');
INSERT INTO `sys_auth` VALUES ('6', '0', '0', '6', '0', '6', 'user');
INSERT INTO `sys_auth` VALUES ('7', '0', '0', '7', '0', '7', 'user');
INSERT INTO `sys_auth` VALUES ('8', '0', '0', '8', '0', '8', 'user');
INSERT INTO `sys_auth` VALUES ('9', '0', '0', '9', '0', '9', 'user');
INSERT INTO `sys_auth` VALUES ('10', '0', '0', '10', '0', '10', 'user');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  `default_group` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_group_type` (`type`),
  KEY `idx_sys_group_show` (`is_show`),
  KEY `idx_sys_group_default_group` (`default_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_relation`;
CREATE TABLE `sys_group_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `start_user_id` bigint(20) DEFAULT NULL,
  `end_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_group_relation_group` (`group_id`),
  KEY `idx_sys_group_relation_organization` (`organization_id`),
  KEY `idx_sys_group_relation_user` (`user_id`),
  KEY `idx_sys_group_relation_start_user_id` (`start_user_id`),
  KEY `idx_sys_group_relation_end_user_id` (`end_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_job_nam` (`name`),
  KEY `idx_sys_job_parent_id` (`parent_id`),
  KEY `idx_sys_job_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '职务', '0', '0/', null, '1', '1');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_name` (`name`),
  KEY `idx_sys_organization_type` (`type`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '组织机构', null, '0', '0/', null, '1', '1');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_permission_name` (`name`),
  KEY `idx_sys_permission_permission` (`permission`),
  KEY `idx_sys_permission_show` (`is_show`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '所有', '*', '所有数据操作的权限', '1');
INSERT INTO `sys_permission` VALUES ('2', '新增', 'create', '新增数据操作的权限', '1');
INSERT INTO `sys_permission` VALUES ('3', '修改', 'update', '修改数据操作的权限', '1');
INSERT INTO `sys_permission` VALUES ('4', '删除', 'delete', '删除数据操作的权限', '1');
INSERT INTO `sys_permission` VALUES ('5', '查看', 'view', '查看数据操作的权限', '1');
INSERT INTO `sys_permission` VALUES ('6', '审核', 'audit', '审核数据操作的权限', '1');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `identity` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_name` (`name`),
  KEY `idx_sys_resource_identity` (`identity`),
  KEY `idx_sys_resource_user` (`url`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '资源', '', '', '0', '0/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('2', '示例管理', 'showcase', '', '1', '0/1/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('3', '示例列表', 'sample', '/showcase/sample', '2', '0/1/2/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('4', '逻辑删除列表', 'deleted', '/showcase/deleted', '2', '0/1/2/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('5', '可移动列表', 'move', '/showcase/move', '2', '0/1/2/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('6', '文件上传列表', 'upload', '/showcase/upload', '2', '0/1/2/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('7', '树列表', 'tree', '/showcase/tree', '2', '0/1/2/', null, '5', '1');
INSERT INTO `sys_resource` VALUES ('8', '编辑器列表', 'editor', '/showcase/editor', '2', '0/1/2/', null, '6', '1');
INSERT INTO `sys_resource` VALUES ('9', '父子表（小数据量）', 'parentchild', '/showcase/parentchild/parent', '2', '0/1/2/', null, '7', '1');
INSERT INTO `sys_resource` VALUES ('10', '父子表（大数据量）管理', '', '', '2', '0/1/2/', null, '8', '1');
INSERT INTO `sys_resource` VALUES ('11', '类别列表', 'productCategory', '/showcase/product/category', '10', '0/1/2/10/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('12', '产品列表', 'product', '/showcase/product/product', '10', '0/1/2/10/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('13', '状态管理', '', '', '2', '0/1/2/', null, '9', '1');
INSERT INTO `sys_resource` VALUES ('14', '审核状态列表', 'statusAudit', '/showcase/status/audit', '13', '0/1/2/13/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('15', '显示状态列表', 'statusShow', '/showcase/status/show', '13', '0/1/2/13/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('16', '系统管理', 'sys', '', '1', '0/1/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('17', '用户管理', '', '', '16', '0/1/16/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('18', '用户列表', 'user', '/admin/sys/user/main', '17', '0/1/16/17/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('19', '在线用户列表', 'userOnline', '/admin/sys/user/online', '17', '0/1/16/17/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('20', '状态变更历史列表', 'userStatusHistory', '/admin/sys/user/statusHistory', '17', '0/1/16/17/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('21', '用户最后在线历史列表', 'userLastOnline', '/admin/sys/user/lastOnline', '17', '0/1/16/17/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('22', '组织机构管理', '', '', '16', '0/1/16/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('23', '组织机构列表', 'organization', '/admin/sys/organization/organization', '22', '0/1/16/22/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('24', '工作职务列表', 'job', '/admin/sys/organization/job', '22', '0/1/16/22/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('25', '资源列表', 'resource', '/admin/sys/resource', '16', '0/1/16/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('26', '权限管理', '', '', '16', '0/1/16/', null, '5', '1');
INSERT INTO `sys_resource` VALUES ('27', '权限列表', 'permission', '/admin/sys/permission/permission', '26', '0/1/16/26/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('28', '授权权限给角色', 'role', '/admin/sys/permission/role', '26', '0/1/16/26/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('29', '分组列表', 'group', '/admin/sys/group', '16', '0/1/16/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('30', '授权角色给实体', 'auth', '/admin/sys/auth', '26', '0/1/16/26/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('31', '个人中心', '', '', '1', '0/1/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('32', '我的消息', '', '/admin/personal/message', '31', '0/1/31/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('33', '开发维护', 'maintain', '', '1', '0/1/', null, '5', '1');
INSERT INTO `sys_resource` VALUES ('34', '图标管理', 'icon', '/admin/maintain/icon', '33', '0/1/33/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('35', '键值对', 'keyvalue', '/admin/maintain/keyvalue', '33', '0/1/33/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('37', '静态资源版本控制', 'staticResource', '/admin/maintain/staticResource', '33', '0/1/33/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('38', '在线编辑', 'onlineEditor', '/admin/maintain/editor', '33', '0/1/33/', null, '5', '1');
INSERT INTO `sys_resource` VALUES ('39', '系统监控', 'monitor', '', '1', '0/1/', null, '6', '1');
INSERT INTO `sys_resource` VALUES ('40', '在线用户列表', 'userOnline', '/admin/sys/user/online', '39', '0/1/39/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('41', '数据库监控', 'db', '/admin/monitor/druid/index.html', '39', '0/1/39/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('42', 'hibernate监控', 'hibernate', '/admin/monitor/hibernate', '39', '0/1/39/', null, '3', '1');
INSERT INTO `sys_resource` VALUES ('43', '执行SQL/JPA QL', 'ql', '/admin/monitor/db/sql', '39', '0/1/39/', null, '4', '1');
INSERT INTO `sys_resource` VALUES ('44', 'ehcache监控', 'ehcache', '/admin/monitor/ehcache', '39', '0/1/39/', null, '5', '1');
INSERT INTO `sys_resource` VALUES ('45', 'jvm监控', 'jvm', '/admin/monitor/jvm', '39', '0/1/39/', null, '6', '1');
INSERT INTO `sys_resource` VALUES ('46', 'Excel导入/导出', 'excel', '/showcase/excel', '2', '0/1/2/', null, '10', '1');
INSERT INTO `sys_resource` VALUES ('47', '我的通知', '', '/admin/personal/notification', '31', '0/1/31/', null, '2', '1');
INSERT INTO `sys_resource` VALUES ('48', '通知模板', 'notificationTemplate', '/admin/maintain/notification/template', '33', '0/1/33/', null, '1', '1');
INSERT INTO `sys_resource` VALUES ('1000', '会员管理', 'member', '/showcase/member', '2', '0/1/2/', 'ztree_file', '11', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_name` (`name`),
  KEY `idx_sys_role_role` (`role`),
  KEY `idx_sys_role_show` (`is_show`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '拥有所有权限', '1');
INSERT INTO `sys_role` VALUES ('2', '示例管理员', 'example_admin', '拥有示例管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('3', '系统管理员', 'sys_admin', '拥有系统管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('4', '维护管理员', 'conf_admin', '拥有维护管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('5', '新增管理员', 'create_admin', '拥有新增/查看管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('6', '修改管理员', 'update_admin', '拥有修改/查看管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('7', '删除管理员', 'delete_admin', '拥有删除/查看管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('8', '查看管理员', 'view_admin', '拥有查看管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('9', '审核管理员', 'audit_admin', '拥有审核管理的所有权限', '1');
INSERT INTO `sys_role` VALUES ('10', '监控管理员', 'audit_admin', '拥有审核管理的所有权限', '1');

-- ----------------------------
-- Table structure for sys_role_resource_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource_permission`;
CREATE TABLE `sys_role_resource_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `permission_ids` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_role_resource_permission` (`role_id`,`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource_permission
-- ----------------------------
INSERT INTO `sys_role_resource_permission` VALUES ('1', '1', '2', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('2', '1', '16', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('3', '1', '33', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('4', '1', '39', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('5', '2', '2', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('6', '3', '16', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('7', '4', '33', '1');
INSERT INTO `sys_role_resource_permission` VALUES ('8', '5', '2', '2,5');
INSERT INTO `sys_role_resource_permission` VALUES ('9', '5', '16', '2,5');
INSERT INTO `sys_role_resource_permission` VALUES ('10', '5', '33', '2,5');
INSERT INTO `sys_role_resource_permission` VALUES ('11', '5', '39', '2,5');
INSERT INTO `sys_role_resource_permission` VALUES ('12', '6', '2', '3,5');
INSERT INTO `sys_role_resource_permission` VALUES ('13', '6', '16', '3,5');
INSERT INTO `sys_role_resource_permission` VALUES ('14', '6', '33', '3,5');
INSERT INTO `sys_role_resource_permission` VALUES ('15', '6', '39', '3,5');
INSERT INTO `sys_role_resource_permission` VALUES ('16', '7', '2', '4,5');
INSERT INTO `sys_role_resource_permission` VALUES ('17', '7', '16', '4,5');
INSERT INTO `sys_role_resource_permission` VALUES ('18', '7', '33', '4,5');
INSERT INTO `sys_role_resource_permission` VALUES ('19', '7', '39', '4,5');
INSERT INTO `sys_role_resource_permission` VALUES ('20', '8', '2', '5');
INSERT INTO `sys_role_resource_permission` VALUES ('21', '8', '16', '5');
INSERT INTO `sys_role_resource_permission` VALUES ('22', '8', '33', '5');
INSERT INTO `sys_role_resource_permission` VALUES ('23', '8', '39', '5');
INSERT INTO `sys_role_resource_permission` VALUES ('24', '9', '7', '5,6');
INSERT INTO `sys_role_resource_permission` VALUES ('25', '9', '14', '5,6');
INSERT INTO `sys_role_resource_permission` VALUES ('26', '9', '15', '5,6');
INSERT INTO `sys_role_resource_permission` VALUES ('27', '10', '39', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile_phone_number` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_username` (`username`),
  UNIQUE KEY `unique_sys_user_email` (`email`),
  UNIQUE KEY `unique_sys_user_mobile_phone_number` (`mobile_phone_number`),
  KEY `idx_sys_user_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin@sishuok.com', '13412345671', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('2', 'showcase', 'showcase@sishuok.com', '13412345672', '5f915c55c6d43da136a42e3ebabbecfc', 'hSSixwNQwt', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('3', 'sys', 'sys@sishuok.com', '13412345673', 'a10b3c7af051a81fe2506318f982ce28', 'MANHOoCpnb', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('4', 'maintain', 'maintain@sishuok.com', '13412345674', '594813c5eb02b210dacc1a36c2482fc1', 'iY71e4dtoa', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('5', 'create', 'create@sishuok.com', '13412345675', 'a6d5988a698dec63c6eea71994dd7be0', 'iruPxupgfb', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('6', 'update', 'update@sishuok.com', '13412345676', 'fffa26ac5c47ec1bf9a37d9823816074', '2WQx5LmvlV', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('7', 'delete', 'delete@sishuok.com', '13412345677', '4c472bf1d56f440d2953803ab4eea8d4', 'E8KSvr1C7d', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('8', 'view', 'view@sishuok.com', '13412345678', 'c919215efcef4064858bf02f8776c00d', 'XFJZQOXWZW', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('9', 'audit', 'audit@sishuok.com', '13412345679', '15d8f7b8da8045d24c71a92a142ffad7', 'BI2XbXMUr7', '2013-12-28 03:36:11', 'normal', '0', '1');
INSERT INTO `sys_user` VALUES ('10', 'monitor', 'monitor@sishuok.com', '1341234580', 'e1549e68ad21fe888ae36ec4965116cd', 'iY71e4d123', '2013-12-28 03:36:11', 'normal', '0', '1');

-- ----------------------------
-- Table structure for sys_user_last_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_last_online`;
CREATE TABLE `sys_user_last_online` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `system_host` varchar(100) DEFAULT NULL,
  `last_login_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_stop_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `login_count` bigint(20) DEFAULT NULL,
  `total_online_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_last_online_sys_user_id` (`user_id`),
  KEY `idx_sys_user_last_online_username` (`username`),
  KEY `idx_sys_user_last_online_host` (`host`),
  KEY `idx_sys_user_last_online_system_host` (`system_host`),
  KEY `idx_sys_user_last_online_last_login_timestamp` (`last_login_timestamp`),
  KEY `idx_sys_user_last_online_last_stop_timestamp` (`last_stop_timestamp`),
  KEY `idx_sys_user_last_online_user_agent` (`user_agent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_last_online
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online` (
  `id` varchar(100) NOT NULL,
  `user_id` bigint(20) DEFAULT '0',
  `username` varchar(100) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  `system_host` varchar(100) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `start_timestsamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_access_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `timeout` bigint(20) DEFAULT NULL,
  `session` mediumtext,
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_online_sys_user_id` (`user_id`),
  KEY `idx_sys_user_online_username` (`username`),
  KEY `idx_sys_user_online_host` (`host`),
  KEY `idx_sys_user_online_system_host` (`system_host`),
  KEY `idx_sys_user_online_start_timestsamp` (`start_timestsamp`),
  KEY `idx_sys_user_online_last_access_time` (`last_access_time`),
  KEY `idx_sys_user_online_user_agent` (`user_agent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------
INSERT INTO `sys_user_online` VALUES ('09589572-8814-4d4d-8b0c-f0f6e7d6b5bd', '1', 'admin', '127.0.0.1', '127.0.0.1:9080', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36', 'on_line', '2014-11-01 00:05:20', '2014-11-01 01:36:55', '1800000', 'aced00057372002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e9d1ca1b8d58c626e0300054c00067374617475737400394c6f72672f6170616368652f736869726f2f73657373696f6e2f6d67742f4f6e6c696e6553657373696f6e244f6e6c696e655374617475733b4c000a73797374656d486f73747400124c6a6176612f6c616e672f537472696e673b4c0009757365724167656e7471007e00024c00067573657249647400104c6a6176612f6c616e672f4c6f6e673b4c0008757365726e616d6571007e00027872002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e53696d706c6553657373696f6e9d1ca1b8d58c626e0300007870770200db74002430393538393537322d383831342d346434642d386230632d6630663665376436623562647372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000014966f432d6787371007e000777080000014967480c9b78771300000000001b774000093132372e302e302e31737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f40000000000006770800000008000000057400496f72672e737072696e676672616d65776f726b2e7765622e736572766c65742e737570706f72742e53657373696f6e466c6173684d61704d616e616765722e464c4153485f4d415053737200296a6176612e7574696c2e636f6e63757272656e742e436f70794f6e577269746541727261794c697374785d9fd546ab90c30300007870770400000000787400506f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f41555448454e544943415445445f53455353494f4e5f4b4559737200116a6176612e6c616e672e426f6f6c65616ecd207280d59cfaee0200015a000576616c7565787001740008757365726e616d6574000561646d696e74004d6f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f5052494e434950414c535f53455353494f4e5f4b4559737200326f72672e6170616368652e736869726f2e7375626a6563742e53696d706c655072696e636970616c436f6c6c656374696f6ea87f5825c6a3084a0300014c000f7265616c6d5072696e636970616c7374000f4c6a6176612f7574696c2f4d61703b7870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e000a3f40000000000000770800000001000000017400226f72672e6170616368652e736869726f2e7265616c6d2e557365725265616c6d5f30737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000171007e001378780077010171007e0019787400476f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e6569732e4f6e6c696e6553657373696f6e44414f4c4153545f53594e435f44425f54494d455354414d5071007e000978787e7200376f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e244f6e6c696e6553746174757300000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400076f6e5f6c696e6574000e3132372e302e302e313a3930383074006d4d6f7a696c6c612f352e30202857696e646f7773204e5420362e333b20574f57363429204170706c655765624b69742f3533372e333620284b48544d4c2c206c696b65204765636b6f29204368726f6d652f33372e302e323036322e313234205361666172692f3533372e33367372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000171007e001377020f0071007e002771007e002471007e002171007e001378');
INSERT INTO `sys_user_online` VALUES ('672b7e8a-2eb3-4dc6-9344-a48a45caec5f', '1', 'admin', '127.0.0.1', '127.0.0.1:9080', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36', 'on_line', '2014-10-31 23:51:39', '2014-10-31 23:59:51', '1800000', 'aced00057372002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e9d1ca1b8d58c626e0300054c00067374617475737400394c6f72672f6170616368652f736869726f2f73657373696f6e2f6d67742f4f6e6c696e6553657373696f6e244f6e6c696e655374617475733b4c000a73797374656d486f73747400124c6a6176612f6c616e672f537472696e673b4c0009757365724167656e7471007e00024c00067573657249647400104c6a6176612f6c616e672f4c6f6e673b4c0008757365726e616d6571007e00027872002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e53696d706c6553657373696f6e9d1ca1b8d58c626e0300007870770200db74002436373262376538612d326562332d346463362d393334342d6134386134356361656335667372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000014966e7acf9787371007e000777080000014966ef2f3878771300000000001b774000093132372e302e302e31737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f40000000000006770800000008000000047400506f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f41555448454e544943415445445f53455353494f4e5f4b4559737200116a6176612e6c616e672e426f6f6c65616ecd207280d59cfaee0200015a000576616c7565787001740008757365726e616d6574000561646d696e74004d6f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f5052494e434950414c535f53455353494f4e5f4b4559737200326f72672e6170616368652e736869726f2e7375626a6563742e53696d706c655072696e636970616c436f6c6c656374696f6ea87f5825c6a3084a0300014c000f7265616c6d5072696e636970616c7374000f4c6a6176612f7574696c2f4d61703b7870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e000a3f40000000000000770800000001000000017400226f72672e6170616368652e736869726f2e7265616c6d2e557365725265616c6d5f30737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000171007e001078780077010171007e0016787400476f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e6569732e4f6e6c696e6553657373696f6e44414f4c4153545f53594e435f44425f54494d455354414d5071007e000978787e7200376f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e244f6e6c696e6553746174757300000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400076f6e5f6c696e6574000e3132372e302e302e313a3930383074006d4d6f7a696c6c612f352e30202857696e646f7773204e5420362e333b20574f57363429204170706c655765624b69742f3533372e333620284b48544d4c2c206c696b65204765636b6f29204368726f6d652f33372e302e323036322e313234205361666172692f3533372e33367372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000171007e001077020f0071007e002471007e002171007e001e71007e001078');
INSERT INTO `sys_user_online` VALUES ('bdeb5437-917a-48a9-a7e2-d4bdc807c294', '1', 'admin', '60.247.94.14', '211.152.54.141:9080', 'Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31', 'on_line', '2013-12-28 11:46:06', '2013-12-28 11:46:21', '1800000', 'aced00057372002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e9d1ca1b8d58c626e0300054c00067374617475737400394c6f72672f6170616368652f736869726f2f73657373696f6e2f6d67742f4f6e6c696e6553657373696f6e244f6e6c696e655374617475733b4c000a73797374656d486f73747400124c6a6176612f6c616e672f537472696e673b4c0009757365724167656e7471007e00024c00067573657249647400104c6a6176612f6c616e672f4c6f6e673b4c0008757365726e616d6571007e00027872002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e53696d706c6553657373696f6e9d1ca1b8d58c626e0300007870770200db74002462646562353433372d393137612d343861392d613765322d6434626463383037633239347372000e6a6176612e7574696c2e44617465686a81014b5974190300007870770800000143374f1715787371007e0007770800000143374f50d178771600000000001b7740000c36302e3234372e39342e3134737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000047400506f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f41555448454e544943415445445f53455353494f4e5f4b4559737200116a6176612e6c616e672e426f6f6c65616ecd207280d59cfaee0200015a000576616c7565787001740008757365726e616d6574000561646d696e74004d6f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f5052494e434950414c535f53455353494f4e5f4b4559737200326f72672e6170616368652e736869726f2e7375626a6563742e53696d706c655072696e636970616c436f6c6c656374696f6ea87f5825c6a3084a0300014c000f7265616c6d5072696e636970616c7374000f4c6a6176612f7574696c2f4d61703b7870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e000a3f4000000000000c770800000010000000017400226f72672e6170616368652e736869726f2e7265616c6d2e557365725265616c6d5f30737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000171007e001078780077010171007e0016787400476f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e6569732e4f6e6c696e6553657373696f6e44414f4c4153545f53594e435f44425f54494d455354414d5071007e000978787e7200376f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e244f6e6c696e6553746174757300000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400076f6e5f6c696e657400133231312e3135322e35342e3134313a3930383074006c4d6f7a696c6c612f352e30202857696e646f7773204e5420362e323b20574f57363429204170706c655765624b69742f3533372e333120284b48544d4c2c206c696b65204765636b6f29204368726f6d652f32362e302e313431302e3433205361666172692f3533372e33317372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000171007e001077020f0071007e002471007e002171007e001e71007e001078');
INSERT INTO `sys_user_online` VALUES ('f77bb5c7-cd7b-49ff-ab0f-84440994c287', '1', 'admin', '127.0.0.1', '127.0.0.1:9080', 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36', 'on_line', '2014-10-31 14:50:01', '2014-10-31 14:50:18', '1800000', 'aced00057372002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e9d1ca1b8d58c626e0300054c00067374617475737400394c6f72672f6170616368652f736869726f2f73657373696f6e2f6d67742f4f6e6c696e6553657373696f6e244f6e6c696e655374617475733b4c000a73797374656d486f73747400124c6a6176612f6c616e672f537472696e673b4c0009757365724167656e7471007e00024c00067573657249647400104c6a6176612f6c616e672f4c6f6e673b4c0008757365726e616d6571007e00027872002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e53696d706c6553657373696f6e9d1ca1b8d58c626e0300007870770200db74002466373762623563372d636437622d343966662d616230662d3834343430393934633238377372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000014964f7cab1787371007e000777080000014964f80cdf78771300000000001b774000093132372e302e302e31737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000047400506f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f41555448454e544943415445445f53455353494f4e5f4b4559737200116a6176612e6c616e672e426f6f6c65616ecd207280d59cfaee0200015a000576616c7565787001740008757365726e616d6574000561646d696e74004d6f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f5052494e434950414c535f53455353494f4e5f4b4559737200326f72672e6170616368652e736869726f2e7375626a6563742e53696d706c655072696e636970616c436f6c6c656374696f6ea87f5825c6a3084a0300014c000f7265616c6d5072696e636970616c7374000f4c6a6176612f7574696c2f4d61703b7870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e000a3f4000000000000c770800000010000000017400226f72672e6170616368652e736869726f2e7265616c6d2e557365725265616c6d5f30737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000171007e001078780077010171007e0016787400476f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e6569732e4f6e6c696e6553657373696f6e44414f4c4153545f53594e435f44425f54494d455354414d5071007e000978787e7200376f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e244f6e6c696e6553746174757300000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400076f6e5f6c696e6574000e3132372e302e302e313a3930383074006d4d6f7a696c6c612f352e30202857696e646f7773204e5420362e333b20574f57363429204170706c655765624b69742f3533372e333620284b48544d4c2c206c696b65204765636b6f29204368726f6d652f33372e302e323036322e313234205361666172692f3533372e33367372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000171007e001077020f0071007e002471007e002171007e001e71007e001078');
INSERT INTO `sys_user_online` VALUES ('f97d9d36-617e-41d6-bc6d-2fa3ea00506f', '1', 'admin', '123.233.212.143', '203.166.177.140:9080', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36', 'on_line', '2013-12-28 11:47:11', '2013-12-28 11:47:22', '1800000', 'aced00057372002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e9d1ca1b8d58c626e0300054c00067374617475737400394c6f72672f6170616368652f736869726f2f73657373696f6e2f6d67742f4f6e6c696e6553657373696f6e244f6e6c696e655374617475733b4c000a73797374656d486f73747400124c6a6176612f6c616e672f537472696e673b4c0009757365724167656e7471007e00024c00067573657249647400104c6a6176612f6c616e672f4c6f6e673b4c0008757365726e616d6571007e00027872002a6f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e53696d706c6553657373696f6e9d1ca1b8d58c626e0300007870770200db74002466393764396433362d363137652d343164362d626336642d3266613365613030353036667372000e6a6176612e7574696c2e44617465686a81014b5974190300007870770800000143375015cc787371007e000777080000014337503de578771900000000001b7740000f3132332e3233332e3231322e313433737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000047400506f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f41555448454e544943415445445f53455353494f4e5f4b4559737200116a6176612e6c616e672e426f6f6c65616ecd207280d59cfaee0200015a000576616c7565787001740008757365726e616d6574000561646d696e74004d6f72672e6170616368652e736869726f2e7375626a6563742e737570706f72742e44656661756c745375626a656374436f6e746578745f5052494e434950414c535f53455353494f4e5f4b4559737200326f72672e6170616368652e736869726f2e7375626a6563742e53696d706c655072696e636970616c436f6c6c656374696f6ea87f5825c6a3084a0300014c000f7265616c6d5072696e636970616c7374000f4c6a6176612f7574696c2f4d61703b7870737200176a6176612e7574696c2e4c696e6b6564486173684d617034c04e5c106cc0fb0200015a000b6163636573734f726465727871007e000a3f4000000000000c770800000010000000017400226f72672e6170616368652e736869726f2e7265616c6d2e557365725265616c6d5f30737200176a6176612e7574696c2e4c696e6b656448617368536574d86cd75a95dd2a1e020000787200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000103f4000000000000171007e001078780077010171007e0016787400476f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e6569732e4f6e6c696e6553657373696f6e44414f4c4153545f53594e435f44425f54494d455354414d5071007e000978787e7200376f72672e6170616368652e736869726f2e73657373696f6e2e6d67742e4f6e6c696e6553657373696f6e244f6e6c696e6553746174757300000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400076f6e5f6c696e657400143230332e3136362e3137372e3134303a3930383074006d4d6f7a696c6c612f352e30202857696e646f7773204e5420362e313b20574f57363429204170706c655765624b69742f3533372e333620284b48544d4c2c206c696b65204765636b6f29204368726f6d652f33302e302e313539392e313031205361666172692f3533372e33367372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000171007e001077020f0071007e002471007e002171007e001e71007e001078');

-- ----------------------------
-- Table structure for sys_user_organization_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_organization_job`;
CREATE TABLE `sys_user_organization_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_organization_job` (`user_id`,`organization_id`,`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_organization_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_status_history
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_status_history`;
CREATE TABLE `sys_user_status_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  `op_user_id` bigint(20) DEFAULT NULL,
  `op_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_block_history_user_id_block_date` (`user_id`,`op_date`),
  KEY `idx_sys_user_block_history_op_user_id_op_date` (`op_user_id`,`op_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_status_history
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_sys_user_off_online`;
DELIMITER ;;
CREATE TRIGGER `trigger_sys_user_off_online` AFTER DELETE ON `sys_user_online` FOR EACH ROW begin
   if OLD.`user_id` is not null then
      if not exists(select `user_id` from `sys_user_last_online` where `user_id` = OLD.`user_id`) then
        insert into `sys_user_last_online`
                  (`user_id`, `username`, `uid`, `host`, `user_agent`, `system_host`,
                   `last_login_timestamp`, `last_stop_timestamp`, `login_count`, `total_online_time`)
                values
                   (OLD.`user_id`,OLD.`username`, OLD.`id`, OLD.`host`, OLD.`user_agent`, OLD.`system_host`,
                    OLD.`start_timestsamp`, OLD.`last_access_time`,
                    1, (OLD.`last_access_time` - OLD.`start_timestsamp`));
      else
        update `sys_user_last_online`
          set `username` = OLD.`username`, `uid` = OLD.`id`, `host` = OLD.`host`, `user_agent` = OLD.`user_agent`,
            `system_host` = OLD.`system_host`, `last_login_timestamp` = OLD.`start_timestsamp`,
             `last_stop_timestamp` = OLD.`last_access_time`, `login_count` = `login_count` + 1,
             `total_online_time` = `total_online_time` + (OLD.`last_access_time` - OLD.`start_timestsamp`)
        where `user_id` = OLD.`user_id`;
      end if ;
   end if;
end
;;
DELIMITER ;
