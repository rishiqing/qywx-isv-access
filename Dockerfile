FROM registry-internal.cn-beijing.aliyuncs.com/rsq-public/tomcat:8.0.50-jre8

LABEL name="qywx-isv-access" \
       description="backend for integration of rishiqing and qywx(enterprise version of WeChat)" \
       maintainer="Wallace Mao" \
       org="rishiqing"

ENV CATALINA_HOME /usr/local/tomcat
WORKDIR $CATALINA_HOME

ADD web/target/qywxbackauth.war webapps/

CMD ["catalina.sh", "run"]
