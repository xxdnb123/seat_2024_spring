<template>
  <div class="stu-info">
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="stu-info-table">
      <el-row class="position">
        <p>您所在的位置：首页 - 个人信息</p>
      </el-row>
      <el-row class="table-row" justify="center">
        <el-col :span="16">
          <el-row v-for="(item, index) in studentInfoTable" :key="index" class="table-row" :style="getRowStyle">
            <el-col :span="8" class="stu-info-label">{{ item.label }}</el-col>
            <el-col :span="16" class="stu-info-data">{{ item.data }}</el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>




<script>
  import axios from 'axios';
  import { Message } from 'element-ui'
  export default {
    name: 'StuInfo',
    data () {
      return {
        studentInfoTable: [
          { label: '学号', data: null },
          { label: '姓名', data: null },
          { label: '专业', data: null },
          { label: '学院', data: null },
          { label: '在黑名单中', data: null },
          { label: '违约次数', data: null }
        ]
      };
    },
    methods: {
      handleCommand (command) {
        if (command === 'backToIndex') {
          this.backToIndex();
        } else if (command === 'logout') {
          this.logout();
        }
      },
      logout () {
        Message({
          message: '登出成功!',
          type: 'success'
        });
        this.$store.commit('logout');
        this.$router.replace('/login');
      },
      backToIndex () {
        this.$router.replace('/stuIndex');
      }
    },
    beforeMount () {
      axios.post('api/stu/info', { number: this.$store.state.stuNum }).then(resp => {
        this.studentInfoTable[0].data = resp.data.number;
        this.studentInfoTable[1].data = resp.data.name;
        this.studentInfoTable[2].data = resp.data.major;
        this.studentInfoTable[3].data = resp.data.college;
        this.studentInfoTable[4].data = resp.data.isBlacklisted === "true" ? '是' : '否';
        this.studentInfoTable[5].data = resp.data.defaultCount;
        console.log(resp.data);
      });
    },
    computed: {
      getRowStyle () {
        return {
          height: '50px' // 调整行高
        };
      }
    }
  };
</script>


<style scoped>
  .stu-info {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }

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

  .stu-info-table {
    width: 80%;
    margin-top: -200px;
    z-index: 0;
    background-color: #f5f5f5;
    /* 表格背景颜色 */
    /*border: 1px solid #ccc; /* 表格边框 */
    padding: 20px;
    border-radius: 5px;
    /* 表格圆角 */
  }

  .table-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 50px;
    margin-bottom: 10px;
    /* 调整行间距 */
  }

  .stu-info-label {
    text-align: right;
    padding-right: 10px;
    font-weight: bold;
    color: #555;
    /* 标签文字颜色 */
  }

  .stu-info-data {
    padding-left: 10px;
    font-size: 16px;
    color: #333;
    /* 数据文字颜色 */
  }

  .el-row {
    margin-bottom: 20px;
  }

  .el-col {
    width: 45%;
    box-sizing: border-box;
    padding: 10px;
  }

  .position {
    margin-bottom: 240px;
    font-size: 24px;
    font-family: "PingFang SC";
  }
</style>