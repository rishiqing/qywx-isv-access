CREATE DATABASE IF NOT EXISTS qywx_isv_access_beta DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `isv_suite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_name` varchar(255) NOT NULL COMMENT '套件名字',
  `suite_key` varchar(128) NOT NULL COMMENT 'suite 的唯一key',
  `suite_secret` varchar(256) NOT NULL COMMENT 'suite的唯一secrect，与key对应',
  `encoding_aes_key` varchar(256) NOT NULL COMMENT '回调信息加解密参数',
  `token` varchar(128) NOT NULL COMMENT '已填写用于生成签名和校验毁掉请求的合法性',
  `corp_id` VARCHAR(128) NOT NULL COMMENT 'isv所在的公司的corp id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套件信息表';

CREATE TABLE `isv_suite_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '套件suitekey',
  `ticket` varchar(128) NOT NULL COMMENT '套件ticket',
  `ticket_update_time` datetime NOT NULL COMMENT 'ticket更新的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用于接收推送的套件ticket';

CREATE TABLE `isv_suite_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '套件key',
  `suite_token` varchar(256) NOT NULL COMMENT '套件token',
  `expires_in` bigint(10) NOT NULL COMMENT '过期时间(s)',
  `token_update_time` datetime NOT NULL COMMENT 'token更新的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套件的accesstoken表';

CREATE TABLE `isv_app` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'pk',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(128) NOT NULL COMMENT 'app的key值',
  `app_id` VARCHAR(128) NOT NULL COMMENT '企业微信分配的app id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_isv_app_app_id` (`suite_key`, `app_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT 'isv创建的app';

CREATE TABLE `isv_corp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '授权方企业微信id',
  `corp_name` varchar(256) DEFAULT NULL COMMENT '授权方企业微信名称',
  `corp_type` varchar(256) DEFAULT NULL COMMENT '授权方企业微信类型，认证号：verified, 注册号：unverified',
  `corp_square_logo_url` varchar(767) DEFAULT NULL COMMENT '授权方企业微信方形头像',
  `corp_user_max` bigint(10) DEFAULT NULL COMMENT '授权方企业微信用户规模',
  `corp_agent_max` bigint(10) DEFAULT NULL COMMENT 'agent max',
  `corp_full_name` varchar(256) DEFAULT NULL COMMENT '所绑定的企业微信主体名称',
  `verified_end_time` bigint(20) DEFAULT NULL COMMENT '认证到期时间',
  `subject_type` bigint(4) DEFAULT NULL COMMENT '企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号',
  `corp_wxqrcode` varchar(767) DEFAULT NULL COMMENT '授权方企业微信二维码',
  `auth_email` varchar(128) DEFAULT NULL COMMENT '授权管理员的邮箱，可能为空（外部管理员一定有，不可更改）',
  `auth_mobile` varchar(128) DEFAULT NULL COMMENT '授权管理员的手机号，可能为空（内部管理员一定有，可更改）',
  `auth_user_id` varchar(128) DEFAULT NULL COMMENT '授权管理员的userid，可能为空（内部管理员一定有，不可更改）',
  `auth_name` varchar(128) DEFAULT NULL COMMENT '授权管理员的name，可能为空（内部管理员一定有，不可更改）',
  `auth_avatar` varchar(128) DEFAULT NULL COMMENT '授权管理员的头像url',
  `is_auth_canceled` bit(1) DEFAULT 0 COMMENT '表示该企业是否是取消授权了，默认是未取消',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_id` (`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业信息表';

CREATE TABLE `isv_corp_suite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '企业corpid',
  `suite_key` varchar(128) NOT NULL COMMENT '套件key',
  `permanent_code` varchar(255) NOT NULL COMMENT '临时授权码或永久授权码value',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_suite` (`corp_id`,`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业对套件的授权记录';

CREATE TABLE `isv_corp_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `agent_id` bigint(20) NOT NULL COMMENT '授权方应用id',
  `name` varchar(128) NOT NULL COMMENT '授权方应用名字',
  `round_logo_url` varchar(767) DEFAULT NULL COMMENT '授权方应用圆形头像',
  `square_logo_url` varchar(767) DEFAULT NULL COMMENT '授权方应用方形头像',
  `suite_key` varchar(128) NOT NULL COMMENT '服务商套件key',
  `app_id` bigint(20) NOT NULL COMMENT '服务商套件中的对应应用id',
  `corp_id` varchar(128) NOT NULL COMMENT '使用微应用的企业ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_app` (`corp_id`,`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业微应用信息表';

CREATE TABLE `isv_corp_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '套件key',
  `corp_id` varchar(128) NOT NULL COMMENT '企业id',
  `corp_token` varchar(512) NOT NULL COMMENT '授权方（企业）access_token,最长为512字节',
  `expires_in` bigint(10) NOT NULL COMMENT '授权方（企业）access_token超时时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`corp_id`, `suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套件能够访问企业数据的accesstoken';

CREATE TABLE `isv_corp_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '公司id',
  `dept_id` bigint(10) NOT NULL COMMENT '部门id',
  `name` varchar(128) COMMENT '部门名称',
  `order` bigint(10) COMMENT '在父部门中的次序值',
  `parent_id` bigint(10) COMMENT '父部门的dept_id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_dept` (`corp_id`,`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='isv存储的公司部门信息';

CREATE TABLE `isv_corp_staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `corp_id` varchar(128) NOT NULL COMMENT '公司id',
  `user_id` varchar(128) NOT NULL COMMENT '成员UserID。对应管理端的帐号',
  `name` varchar(128) COMMENT '成员名称',
  `department` varchar(1024) COMMENT '成员所属部门id列表',
  `order_in_depts` varchar(1024) COMMENT '部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)',
  `is_leader_in_depts` varchar(1024) COMMENT '标示是否为上级',
  `position` varchar(128) COMMENT '职位信息',
  `mobile` varchar(128) COMMENT '手机号码，第三方仅通讯录套件可获取',
  `gender` varchar(1) COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `email` varchar(128) COMMENT '邮箱，第三方仅通讯录套件可获取',
  `avatar` varchar(256) COMMENT '头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可',
  `tel` varchar(128) COMMENT '座机。第三方仅通讯录套件可获取',
  `english_name` varchar(64) COMMENT '英文名。',
  `status` bigint(5) COMMENT '激活状态: 1=已激活，2=已禁用，4=未激活 已激活代表已激活企业微信或已关注微信插件。未激活代表既未激活企业微信又未关注微信插件。',
  `extattr` varchar(1024) COMMENT '扩展属性，第三方仅通讯录套件可获取',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_corp_user` (`corp_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='isv存储的公司成员信息';

CREATE TABLE `isv_corp_jsapi_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `corp_id` varchar(100) NOT NULL COMMENT '企业id',
  `corp_jsapi_ticket` varchar(256) NOT NULL COMMENT '企业js_ticket',
  `expires_in` bigint(10) NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_corp` (`suite_key`,`corp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业使用jsapi的js ticket表';



# quartz tables
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(64) NOT NULL,
  `calendar_name` varchar(64) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(64) NOT NULL,
  `job_name` varchar(64) NOT NULL,
  `job_group` varchar(64) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`),
  KEY `idx_qrtz_j_req_recovery` (`sched_name`,`requests_recovery`) USING BTREE,
  KEY `idx_qrtz_j_grp` (`sched_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `job_name` varchar(64) NOT NULL,
  `job_group` varchar(64) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(64) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `idx_qrtz_t_j` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  KEY `idx_qrtz_t_jg` (`sched_name`,`job_group`) USING BTREE,
  KEY `idx_qrtz_t_c` (`sched_name`,`calendar_name`) USING BTREE,
  KEY `idx_qrtz_t_g` (`sched_name`,`trigger_group`) USING BTREE,
  KEY `idx_qrtz_t_state` (`sched_name`,`trigger_state`) USING BTREE,
  KEY `idx_qrtz_t_n_state` (`sched_name`,`trigger_name`,`trigger_group`,`trigger_state`) USING BTREE,
  KEY `idx_qrtz_t_n_g_state` (`sched_name`,`trigger_group`,`trigger_state`) USING BTREE,
  KEY `idx_qrtz_t_next_fire_time` (`sched_name`,`next_fire_time`) USING BTREE,
  KEY `idx_qrtz_t_nft_st` (`sched_name`,`trigger_state`,`next_fire_time`) USING BTREE,
  KEY `idx_qrtz_t_nft_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`) USING BTREE,
  KEY `idx_qrtz_t_nft_st_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_state`) USING BTREE,
  KEY `idx_qrtz_t_nft_st_misfire_grp` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_group`,`trigger_state`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `instance_name` varchar(64) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(64) DEFAULT NULL,
  `job_group` varchar(64) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`),
  KEY `idx_qrtz_ft_trig_inst_name` (`sched_name`,`instance_name`) USING BTREE,
  KEY `idx_qrtz_ft_inst_job_req_rcvry` (`sched_name`,`instance_name`,`requests_recovery`) USING BTREE,
  KEY `idx_qrtz_ft_j_g` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  KEY `idx_qrtz_ft_jg` (`sched_name`,`job_group`) USING BTREE,
  KEY `idx_qrtz_ft_t_g` (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `idx_qrtz_ft_tg` (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(64) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(64) NOT NULL,
  `instance_name` varchar(64) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `cron_expression` varchar(120) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(64) NOT NULL,
  `trigger_name` varchar(64) NOT NULL,
  `trigger_group` varchar(64) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 1-rsq-column.sql
ALTER TABLE `isv_corp`
  ADD COLUMN `rsq_id`  varchar(255) NULL COMMENT '日事清应用公司id';

ALTER TABLE `isv_corp_dept`
  ADD COLUMN `rsq_id`  varchar(255) NULL COMMENT '日事清应用部门的id';

ALTER TABLE `isv_corp_staff`
  ADD COLUMN `rsq_user_id`  varchar(255) NULL COMMENT '日事清应用用户id',
  ADD COLUMN `rsq_username`  varchar(255) NULL COMMENT '日事清应用登录用户名id',
  ADD COLUMN `rsq_password`  varchar(255) NULL COMMENT '日事清应用登录用户密码',
  ADD COLUMN `rsq_login_token`  varchar(255) NULL COMMENT '日事清应用登录权限凭证';


# isv_corp_app  新版企业微信一个套件只能有一个应用,所以去掉了这里的appid,相应的将该表的app_id字段设置为可空
# 2018-02-07 by Wallace Mao
ALTER TABLE isv_corp_app
  MODIFY app_id bigint(20) NULL COMMENT '服务商套件中的对应应用id';

# 在suite中添加日事清集成的相关字段
# 2018-02-08 by Wallace Mao
ALTER TABLE `isv_suite`
  ADD COLUMN `rsq_app_name`  varchar(255) NULL COMMENT '日事清接口调用对应的name',
  ADD COLUMN `rsq_app_token`  varchar(255) NULL COMMENT '日事清接口调用对应的token';

# 添加是否为管理员字段
# 2018-02-08 by Wallace Mao
ALTER TABLE `isv_corp_staff`
  ADD COLUMN `admin_type` bigint(20) DEFAULT -1 COMMENT '表示该用户是否为企业管理员, -1为默认值，0是有发消息权限，1是有管理权限';

# 添加unionId字段
# 2018-02-09 by Wallace Mao
ALTER TABLE `isv_corp_staff`
  ADD COLUMN `union_id` varchar(255) NULL COMMENT '与网站扫码登录共享的union_id';

# 由于一个suiteKey仅对应一个app_id，那么去掉isv_corp_app
ALTER TABLE isv_corp_app DROP INDEX u_corp_app;
ALTER TABLE isv_corp_app ADD UNIQUE u_corp_app(`suite_key`,`corp_id`);

# 用户开通应用时失败
CREATE TABLE `isv_fail_auth_callback` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(100) NOT NULL COMMENT '套件key',
  `corp_id` VARCHAR(100) NOT NULL COMMENT '企业id',
  `fail_type` VARCHAR(100) NOT NULL COMMENT '失败类型',
  `info_type` VARCHAR(100) NOT NULL COMMENT '回调类型，对应callback里面的infoType',
  `fail_note` VARCHAR(256) DEFAULT NULL COMMENT '失败信息描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_fail_corp_suite` (`suite_key`,`corp_id`,`fail_type`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业对套件的授权失败记录';

# 通讯录变更回调失败
CREATE TABLE `isv_fail_contact_callback` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(100) NOT NULL COMMENT '套件key',
  `corp_id` VARCHAR(100) NOT NULL COMMENT '企业id',
  `fail_type` VARCHAR(100) NOT NULL COMMENT '失败类型',
  `change_type` VARCHAR(100) NOT NULL COMMENT '通讯录回调的change_type',
  `fail_note` VARCHAR(256) DEFAULT NULL COMMENT '错误信息描述',
  `event_msg` VARCHAR(512) NOT NULL COMMENT '企业回调事件的消息体',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业通讯录事件回调失败的记录';

# jsapi ticket 添加更新时间字段
ALTER TABLE `isv_corp_jsapi_ticket`
  ADD COLUMN `update_time` datetime NOT NULL COMMENT '上次更新ticket的时间';

# suite预授权码
CREATE TABLE `isv_suite_pre_auth_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(128) NOT NULL COMMENT '套件key',
  `suite_pre_auth_code` varchar(256) NOT NULL COMMENT '套件的预授权码',
  `expires_in` bigint(10) NOT NULL COMMENT '过期时间(s)',
  `code_update_time` datetime NOT NULL COMMENT 'code更新的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='套件的预授权码表';

# 3-db-tag.sql
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