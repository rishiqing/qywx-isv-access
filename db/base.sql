CREATE DATABASE IF NOT EXISTS qywx_isv_access_beta DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `isv_suite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_name` varchar(255) NOT NULL COMMENT '套件名字',
  `suite_key` varchar(100) NOT NULL COMMENT 'suite 的唯一key',
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
  `suite_key` varchar(100) NOT NULL COMMENT '套件suitekey',
  `ticket` varchar(100) NOT NULL COMMENT '套件ticket',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用于接收推送的套件ticket';

CREATE TABLE `isv_suite_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `last_updated` datetime NOT NULL COMMENT '修改时间',
  `suite_key` varchar(100) NOT NULL COMMENT '套件key',
  `suite_token` varchar(256) NOT NULL COMMENT '套件token',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_suite_key` (`suite_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='套件的accesstoken表';

CREATE TABLE `isv_app` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'pk',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(128) NOT NULL COMMENT 'app的key值',
  `app_id` VARCHAR(128) NOT NULL COMMENT '企业微信分配的app id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_isv_app_app_id` (`app_id`),
  UNIQUE KEY `uk_isv_app_suite_key` (`suite_key`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT 'isv创建的app';



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
