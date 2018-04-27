CREATE TABLE `isv_corp_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '企业id',
  `tag_id` bigint(10) NOT NULL COMMENT '企业的标签id',
  `tag_name` varchar(256) NOT NULL COMMENT '企业的标签名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`corp_id`, `tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业的标签';

CREATE TABLE `isv_corp_tag_staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '企业id',
  `tag_id` bigint(10) NOT NULL COMMENT '企业的标签id',
  `user_id` varchar(128) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`corp_id`, `tag_id`, `user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业中用户与标签的关联表';

CREATE TABLE `isv_corp_tag_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '企业id',
  `tag_id` bigint(10) NOT NULL COMMENT '企业的标签id',
  `dept_id` bigint(10) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`corp_id`, `tag_id`, `dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业中部门与标签的关联表';