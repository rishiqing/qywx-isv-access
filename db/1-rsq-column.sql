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
  MODIFY app_id bigint(20) NULL COMMENT '服务商套件中的对应应用id'