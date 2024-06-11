# 23SPM

#### 启动方式

先clone本项目

1. 前端启动方式

   启动前确保安装了nodejs（和npm），在命令行输入node --version和npm -v，有版本号说明装好了

   在ibooking-vue目录下打开shell，执行npm install安装依赖。如果很慢可以使用淘宝镜像：npm config set registry https://registry.npm.taobao.org。依赖安装完成后再执行npm run dev即可

2. 后端启动方式

   启动前确保安装了java环境和maven，在命令行输入java -version和mvn -v，有版本号说明装好了。没装java的建议装jdk8。

   ...待补充

安装nodejs、java、maven等，可参照菜鸟教程https://www.runoob.com/


## 后端docker启动命令

1. 构建镜像

将构建的jar文件拷贝至ibooking/docker/后执行

```sh
docker build -f Dockerfile -t ibooking_backend:v1.0.0 .
```

2. 启动mysql

```sh
docker run -p 3314:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

#mac
docker run -p 3314:3306 -e MYSQL_ROOT_PASSWORD=123456 -d --platform linux/x86_64 mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

3. 启动后端docker

请替换10.177.44.50为服务器ip

```sh
docker run -p 8090:8090 -d ibooking_backend:v1.0.0 "--spring.datasource.url=jdbc:mysql://10.177.44.50:3314/spm?createDatabaseIfNotExist=true&characterEncoding=utf-8&useSSL=false&useUnicode=true&autoReconnect=true&serverTimezone=Asia/Shanghai"
```

后端服务将在服务器的8090端口上开启