# Install

    > npm install



# Run
    > npm run dev

Navigation to http://localhost:8080 to view the site
    

# 前端docker
安装好docker、nginx之后
在ibooking-vue目录下
 > docker build -t ibooking-frontend . 

 > docker run -d -p 8080:80 --name ibooking-frontend-container ibooking-frontend
