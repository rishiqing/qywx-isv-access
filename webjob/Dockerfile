FROM registry-vpc.cn-beijing.aliyuncs.com/rsq-public/tomcat:8.0.50-jre8

LABEL name="qywx-isv-access-job" \
       description="backend for integration of rishiqing and qywx(enterprise version of WeChat)" \
       maintainer="Wallace Mao" \
       org="rishiqing"

# set default time zone to +0800 (Shanghai)
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

ENV CATALINA_HOME /usr/local/tomcat
WORKDIR $CATALINA_HOME

ADD target/qywxbackauth-job-*.war webapps/qywxbackauth-job.war

CMD ["catalina.sh", "run"]
