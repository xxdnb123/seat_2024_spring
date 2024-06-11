# 第一阶段：构建前端应用
FROM node:14-alpine as builder

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json 文件到容器中
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 复制整个项目到容器中
COPY . .

# 构建前端应用
RUN npm run build


# 第二阶段：使用 Nginx 作为服务器
FROM nginx:latest

RUN rm -rf /usr/share/nginx/html
RUN mkdir -p /usr/share/nginx/html
# 将构建后的前端html文件复制到 Nginx 的默认静态文件目录
COPY --from=builder /app/dist/index.html /usr/share/nginx/html/


#将dist文件夹复制到 Nginx 的默认静态文件目录
COPY --from=builder /app/dist /usr/share/nginx/html/dist
RUN rm -r /usr/share/nginx/html/dist/index.html
#COPY /usr/share/nginx/html/dist/index.html /usr/share/nginx/html

# 将自定义的 nginx.conf 文件复制到容器中的默认路径
COPY nginx.conf /etc/nginx/nginx.conf



# 暴露容器的端口
EXPOSE 80

# 启动 Nginx 服务
CMD ["nginx", "-g", "daemon off;"]
