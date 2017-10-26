# 企业微信后台

## 配置文件

### 系统配置文件
统一使用`${user.home}/qywx/qywxauth-config.properties`
日志文件存放在`${sys:user.home}/qywx/logs/`
### 环境变量

## maven

## spring MVC

## myBatis

## slf4j和log4j2
- 使用slf4j作为log的facade，使用org.slf4j:slf4j-api:1.7.25
- 项目使用log4j2作为日志框架，包括log4j-api、log4j-core两个依赖
- log4j2在web环境下使用，需要添加log4j-web依赖
- slf4j与log4j2的绑定，使用log4j-slf4j-impl
- 由于spring框架中使用jcl作为日志框架，为保证同一使用slf4j，需要一下两个设置：
  1. 在spring框架中exclude commons-logging依赖
  2. 添加jcl-over-slf4j的依赖

## 测试

## job

## 其他