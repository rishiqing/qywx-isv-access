# 企业微信后台

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
  1. layerName取值包括：web/service/dao等
  2. typeName取值包括：corp/isv/scheduler等
  3. functionName，可以是callback/request等
  
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