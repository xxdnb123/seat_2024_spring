# Docker image for springboot file run
# VERSION 0.0.1
# Author:  CHENSIYUAN
# 基础镜像使用java
FROM openjdk:8
# 将jar包添加到容器中并更名为xx.jar
ADD ibooking-0.01.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-Dspring.profiles.active=prop","-jar","/app.jar"]
