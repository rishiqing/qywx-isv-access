# 企业微信后台

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
 values(now(), now(), 'suite_key', 'suite_token_xxxxxx', 7200, '1970-01-01 00:00:00')`
其中:
- suite_key为企业微信中的SuiteID
- suite_token为任意值,该值将在首次获取token时更新

3. 插入isv的初始信息:
`insert into isv (date_created, last_updated, corp_id, provider_secret, encoding_aes_key, token, provider_access_token, expires_in, provider_token_update_time)
 values(now(), now(), 'corp_id', 'provider_secret', 'encoding_aes_key', 'token', 'provider_access_token_xxxx', 7200, '1970-01-01 00:00:00')`
 其中:
 - corp_id为服务商的企业的corpId
 - provider_secret、encoding_aes_key和token均为“服务商后台”中“通用开发参数”中设置的
 - provider_access_token设置为任意值，会在必要时由后台进行更新

### 4  校验
在企业微信中校验回调url是否成功

### 5  等待ticket推送
企业微信的ticket每隔10分钟推送一次。启动tomcat后，需要等待企业微信的ticket的推送，才能调用相关接口

## 配置文件

### 系统配置文件
统一使用`${user.home}/qywx/qywxauth-config.properties`
日志文件存放在`${sys:user.home}/qywx/logs/`

## maven
采用主-子模块结构：
- pom.xml  
父项目，管理所有子模块的依赖的版本，所有子模块所使用的三方库统一在这里添加
- web/pom.xml  
web层依赖的二方库和三方库，不需要写版本号
- service/pom.xml  
service层依赖的二方库和三方库，不需要写版本号
- dao/pom.xml  
dao层所以来的二方库和三方库，不需要写版本号

## spring MVC
- web层中web.xml中配置spring-context.xml、spring-servlet.xml
- spring-context.xml引入service层和dao层的bean，主要包括：
  - spring-beans.xml：service层的各种service bean
  - spring-quartz.xml：service层与计时任务相关的各种bean
  - spring-jdbc.xml：dao层的数据源配置
  - spring-dao.xml：dao层myBatis相关的mapper等配置

## myBatis

### mybatis-config.xml
dao中mybatis相关的配置，与spring集成后，大部分配置已经移入到spring-dao.xml中

### dao mapper interface
`com.rishiqing.qywx.dao.mapper`包中的各种`*Dao`文件，
spring-mybatis会扫描`*Dao`文件，并生成相关的bean

### mapper xml
`classpath:mybatis/**/*DaoMapper.xml`是与dao mapper interface相关联的数据库操作语句文件。

### DO
`com.rishiqing.qywx.dao.model`包中是根据数据库查询结构映射创建的bean，
称为DO，均以DO结尾

## slf4j和log4j2
- 使用slf4j作为log的facade，使用org.slf4j:slf4j-api:1.7.25
- 项目使用log4j2作为日志框架，包括log4j-api、log4j-core两个依赖
- log4j2在web环境下使用，需要添加log4j-web依赖
- slf4j与log4j2的绑定，使用log4j-slf4j-impl
- 由于spring框架中使用jcl作为日志框架，为保证同一使用slf4j，需要一下两个设置：
  1. 在spring框架中exclude commons-logging依赖
  2. 添加jcl-over-slf4j的依赖
- log的存放路径为`${sys:user.home}/qywx/logs/`
- log名称按照[layerName]_[typeName]_[functionName].log的格式
  1. layerName取值包括：web/service/dao等，一般是项目名
  2. typeName取值包括：corp/scheduler等,一般跟包名相关
  3. functionName，可以是callback/request等，一般跟具体功能相关
  
## 打包说明  

目前的应用需要生成同时三个子应用：
- main主应用，用来提供基础服务
- job应用，用来做重试等定期任务
- alert应用，提醒服务器，如果用户设置了提醒，那么使用此服务器来保存并发送提醒

因此，使用maven的maven-assembly-plugin同时打包成三个应用。执行：
`clean package -Dmaven.test.skip=true -Denv=beta`

- maven.test.skip: 是否跳过测试
- env: 用来处理日志文件。例如指定了`env=beta`，那么在打包的时候将会使用`webapp/WEB-INF/log4j2.beta.xml`作为配置文件

## 测试

### 引入xml配置文件
在`test/resources`中新建相关的spring context文件

### 测试用例代码
- 添加注解`@RunWith(SpringJUnit4ClassRunner.class)`
- 添加注解`@ContextConfiguration("classpath:dao-test-spring-context.xml")`
- 使用`@Autowired`引入需要测试的service
- 使用`@Test`标记test case方法
- 使用`org.junit.Assert`断言库进行测试

## job
job项目会做独立部署，因此在一般项目开发时不会启动job

## 其他

### 部门leader问题
CorpStaff的isLeaderInDepts这个字段目前有问题!
在初始化获取部门成员和通讯录变更的时候出现问题
企业微信目前返回值中只有一个1或0,而不能标识出这个staff在多个部门中是否为leader
- 在开通后获取部门成员时,这个值会被反复覆盖
- 在通讯录变更时,无法得知用户在哪个部门是leader

### 日事清后台department字段orderNum int字段overflow问题
日事清后台的department的orderNum为int类型，而企业微信有可能超出int的最大长度

### 企业微信的newUserId问题
企业微信的corp staff的userId可以修改……官方说法是由系统自动生成的userId可以由用户修改一次

### 测试授权

1. 新增授权
- 成员
- 部门
- 标签
- 成员、部门（无重复）
- 成员、部门（有重复）
- 成员、标签（无重复）
- 成员、标签（有重复）
- 部门、标签（无重复）
- 部门、标签（有重复）　

2. 变更授权