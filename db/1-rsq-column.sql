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

# 用户开通应用时失败
CREATE TABLE `isv_corp_suite_fail` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(100) NOT NULL COMMENT '套件key',
  `corp_id` VARCHAR(100) NOT NULL COMMENT '企业id',
  `info_type` VARCHAR(100) NOT NULL COMMENT '失败类型，对应callback里面的infoType',
  `fail_info` VARCHAR(256) DEFAULT NULL COMMENT '失败信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_c_s_f_p` (`suite_key`,`corp_id`,`info_type`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业对套件的授权失败记录';

# 通讯录变更回调失败
CREATE TABLE `isv_corp_callback_fail` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(100) NOT NULL COMMENT '套件key',
  `corp_id` VARCHAR(100) NOT NULL COMMENT '企业id',
  `change_type` VARCHAR(100) NOT NULL COMMENT '通讯录回调的change_type',
  `fail_info` VARCHAR(256) DEFAULT NULL COMMENT '错误信息',
  `event_msg` VARCHAR(512) NOT NULL COMMENT '企业回调事件的消息体',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业通讯录事件回调失败的记录';

# 同步到日事清失败
CREATE TABLE `isv_corp_rsq_fail` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `suite_key` VARCHAR(100) NOT NULL COMMENT '套件key',
  `corp_id` VARCHAR(100) NOT NULL COMMENT '企业id',
  `fail_info` VARCHAR(256) DEFAULT NULL COMMENT '失败信息',
  `fail_type` VARCHAR(128) NOT NULL COMMENT '企业失败类型，用于标记是corp/dept/staff',
  `fail_id` VARCHAR(128) NOT NULL COMMENT '企业失败的id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_c_s_f_p` (`suite_key`,`corp_id`,`fail_type`, `fail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='企业同步组织结构到日事清信息失败记录';