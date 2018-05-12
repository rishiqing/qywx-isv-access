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


CREATE TABLE `isv` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` VARCHAR(128) NOT NULL COMMENT 'isv所在的公司的corp id',
  `provider_secret` varchar(256) NOT NULL COMMENT '服务商的唯一secret',
  `encoding_aes_key` varchar(256) NOT NULL COMMENT '回调信息加解密参数',
  `token` varchar(128) NOT NULL COMMENT '已填写用于生成签名和校验毁掉请求的合法性',
  `provider_access_token` varchar(128) NOT NULL COMMENT '用来获取服务商相关信息的access token',
  `expires_in` bigint(10) NOT NULL COMMENT '过期时间(s)',
  `provider_token_update_time` datetime NOT NULL COMMENT 'token更新的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_isv_key` (`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='服务商信息表';