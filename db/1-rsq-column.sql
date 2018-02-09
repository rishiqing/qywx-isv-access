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