# 企业微信后台对接接口

## 基础配置文件
`file:${user.home}/qywx/qywxauth-config.properties`

## 部署新应用说明
### 1  企业微信创建新应用
登录并打开[企业微信页面](https://open.work.weixin.qq.com/wwopen/developer#/sass/apps/list),重要的信息有:
- 应用主页
- 可信域名
- 安装完成回调域名
- 业务设置URL
- 数据回调URL
- 指令回调URL
- SuiteID(SuiteKey)
- Secret
- Token
- EncodingAESKey

### 2  企业微信isv后台程序配置
配置文件路径: `file:${user.home}/qywx/qywxauth-config.properties`  
基本的配置包括:  
- jdbc.username: 例如`root`
- jdbc.password: 例如`root`
- jdbc.url: 例如`jdbc:mysql://localhost:3306/qywx_isv_access_beta?useUnicode=true&characterEncoding=utf8`
- isv.suite.key: 企业微信中的SuiteID
- isv.app.id: 企业微信中为app分配的id
- isv.app.page.mobile.index: app移动端首页地址,可加替换变量
例如: `http://qywx.rsq.etoutiao.cn/rsqbackwebapp?corpid=$CORPID$&agentid=$AGENTID$`
- isv.app.page.cookie.name: 在客户端cookie中缓存的id名称
建议: `$AGENTID$-$CORPID$-userId`
- isv.rsq.url.root: 与日事清交互的地址
例如: `https://beta.rishiqing.com/`
- amq.broker.url1: activemq的地址,例如: `localhost:616161`
- amq.broker.url2: activemq的地址,例如: `localhost:616161`
- amq.username: activemq的用户名,例如: `admin`
- amq.password: activemq的密码,例如:`admin`

配置完成后,重启tomcat

### 3  数据库新增套件和应用
执行SQL语句:  
1. 插入suite基本信息:  
`insert into isv_suite (date_created, last_updated, suite_name, suite_key, suite_secret, encoding_aes_key, token, corp_id) 
values (now(), now(), 'suite_name', 'suite_key', 'suite_secret', 'encoding_aes_key', 'token', 'corp_id')`  
其中: 
- suite_name/suite_key/suite_secret/encoding_aes_key分别为企业微信中对应的配置信息  
- corp_id为isv的企业id, 日事清为: `wxec002534a59ea2e7`

2. 插入suite_token初始信息:  
`insert into isv_suite_token (date_created, last_updated, suite_key, suite_token, expires_in, token_update_time)
 values(now(), now(), 'suite_key', 'suite_token_xxxxxx', 7200, now())`
其中:
- suite_key为企业微信中的SuiteID
- suite_token为任意值,该值将在首次获取token时更新

### 4  校验
在企业微信中校验回调url是否成功