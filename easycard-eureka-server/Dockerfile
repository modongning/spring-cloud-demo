FROM java:8
# 作者信息
MAINTAINER modongning<modongning@163.com>

#定义参数名称
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]