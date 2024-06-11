<template>
  <div>
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="button-container">
      <p class="info">欢迎您: 管理员{{this.$store.state.adminId}}</p>
      <el-row><el-col>
          <el-button @click="infoManagement" type="primary" class="button-item">学生信息管理</el-button></el-col></el-row>
      <el-row><el-col><el-button @click="studyRoomManagement" type="primary"
            class="button-item">自习室管理</el-button></el-col></el-row>
      <el-row><el-col><el-button @click="studyRoomAdd" type="primary"
            class="button-item">新增自习室</el-button></el-col></el-row>
    </div>
  </div>
</template>
<script>
  import { Message } from 'element-ui'
  export default {
    methods: {
      handleCommand (command) {
        if (command === 'logout') {
          this.logout();
        }
      },
      logout () {
        Message({
          message: '登出成功!',
          type: 'success'
        });
        this.$store.commit('logout');
        this.$router.replace('/login')
      },
      infoManagement () {
        this.$router.replace('/admin/stuInfo')
      },
      studyRoomManagement () {
        this.$router.replace('/admin/studyRoomList')
      },
      studyRoomAdd () {
        this.$router.replace('/admin/studyRoomAdd')
      }
    }
  }
</script>


<style scoped>
  .info-dropdown {
    position: absolute;
    top: 20px;
    right: 20px;
    z-index: 1;
  }

  .stu-info-icon {
    font-size: 30px;
    color: #333;
    /* 图标颜色 */
  }

  .info-dropdown-menu {
    width: 150px;
    font-size: 16px;
  }

  .button-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }

  .button-item {
    width: 200px;
    margin-bottom: 10px;
  }

  .button-container::before,
  .button-container::after {
    content: '';
    flex-grow: 1;
  }
</style>