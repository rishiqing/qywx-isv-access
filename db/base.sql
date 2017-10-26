CREATE DATABASE IF NOT EXISTS qywx_isv_access_beta DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `isv_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `date_created` DATETIME NOT NULL COMMENT '创建时间',
  `last_updated` DATETIME NOT NULL COMMENT '修改时间',
  `app_id` VARCHAR(128) NOT NULL COMMENT '企业微信分配的app id',
  `suite_key` VARCHAR(128) NOT NULL COMMENT 'app的key值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_isv_app_app_id` (`app_id`),
  UNIQUE KEY `uk_isv_app_suite_key` (`suite_key`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT 'isv创建的app';