LABEL name="qywx-isv-access"
LABEL description="backend for integration of rishiqing and qywx(enterprise version of WeChat)"
LABEL maintainer="Wallace Mao"
LABEL org="rishiqing"

FROM registry.cn-beijing.aliyuncs.com/rsq-main/tomcat:8.5.29

ENV CATALINA_HOME /usr/local/tomcat
WORKDIR $CATALINA_HOME

ADD web/target/qywxbackauth.war webapps/
