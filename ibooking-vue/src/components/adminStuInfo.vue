<template>
  <div class="admin-stu-info">
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div>
      <p class="info1">您所在的位置：首页 - 学生信息管理</p>
    </div>
    <div class="container">
      <div class="stu-search">
        <el-form :model="searchForm" :rules="rules" :ref="searchForm" class="form-center">
          <el-form-item prop="stuNum" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">学号：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.stuNum" auto-complete="off"
                  placeholder="请输入学号" width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item prop="name" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">姓名：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.name" auto-complete="off" placeholder="请输入姓名"
                  width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item prop="major" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">专业：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.major" auto-complete="off" placeholder="请输入专业"
                  width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item prop="school" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">学院：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.school" auto-complete="off"
                  placeholder="请输入学院" width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item prop="blacklisted" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">在黑名单中：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.blacklisted" auto-complete="off"
                  placeholder="请输入是否在黑名单中" width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item prop="badCount" label="" style="margin: 20px;">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">违约次数：</label></el-col>
              <el-col :span="8"><el-input type="text" v-model="searchForm.badCount" auto-complete="off"
                  placeholder="请输入违约次数" width="350px" class="input"></el-input></el-col>
            </el-row>
          </el-form-item>

          <el-form-item style="margin: 20px;">
            <el-row><el-button type="primary" v-on:click="search(searchForm)">查询</el-button></el-row>
          </el-form-item>
        </el-form>
      </div>
      <div class="stu-info">
        <el-table :data="studentInfoTable" class="info-table">
          <!-- stuNum, name, major, school, blacklisted, badCount -->
          <el-table-column prop="stuNum" label="学号"></el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="school" label="学院"></el-table-column>
          <el-table-column prop="blacklisted" label="在黑名单中" :formatter="getBlacklisted">
          </el-table-column>
          <el-table-column prop="badCount" label="违约次数"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-row><el-button size="mini" v-bind:disabled="isAddDisabled(scope.$index)"
                  @click="addToBlacklist(scope.$index)">加入黑名单</el-button></el-row>
              <el-row><el-button size="mini" type="danger" v-bind:disabled="isDelDisable(scope.$index)"
                  @click="removeFromBlacklist(scope.$index)">移除黑名单</el-button></el-row>
              <el-row><el-button size="mini" type="primary"
                  @click="reservationList(scope.$index)">查看预约记录</el-button></el-row>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'AdminStuInfo',
    data () {
      return {
        searchForm: {
          stuNum: '',
          name: '',
          major: '',
          school: '',
          blacklisted: '',
          badCount: ''
        },
        studentInfoTable: []
      }
    }, methods: {
      search (formName) {
        this.studentInfoTable.splice(0, this.studentInfoTable.length);
        var tempBlacklisted = null;
        if (this.searchForm.blacklisted === "是") {
          tempBlacklisted = true;
        } else if (this.searchForm.blacklisted === "否") {
          tempBlacklisted = false;
        }
        axios.post('/api/admin/studentInfo', {
          number: this.searchForm.stuNum === "" ? null : this.searchForm.stuNum,
          name: this.searchForm.name === "" ? null : this.searchForm.name,
          major: this.searchForm.major === "" ? null : this.searchForm.major,
          college: this.searchForm.school === "" ? null : this.searchForm.school,
          isBlacklisted: tempBlacklisted,
          defaultCount: this.searchForm.badCount === "" ? null : this.searchForm.badCount
        }).then(resp => {
          if (resp.status === 200) {
            Message({
              message: '查询成功！',
              type: 'success'
            })
            console.log(resp.data)
            for (var i = 0; i < resp.data.length; i++) {
              this.studentInfoTable.push(
                {
                  stuNum: resp.data[i][0],
                  name: resp.data[i][1],
                  major: resp.data[i][2],
                  school: resp.data[i][3],
                  blacklisted: resp.data[i][4],
                  badCount: resp.data[i][5]
                }
              )
            }
          }
        })
      },
      getBlacklisted (row) {
        if (row.blacklisted === "true") {
          return "是";
        } else {
          return "否";
        }
      },
      isAddDisabled (row) {
        if (this.studentInfoTable[row].blacklisted === "true") {
          return true;
        } else {
          return false;
        }
      },
      isDelDisable (row) {
        if (this.studentInfoTable[row].blacklisted === "false") {
          return true;
        } else {
          return false;
        }
      },
      addToBlacklist (row) {
        axios.post('/api/admin/blacklistAdd', {
          number: this.studentInfoTable[row].stuNum
        }).then(resp => {
          if (resp.status === 200) {
            Message({
              message: '操作成功！',
              type: 'success'
            })
            this.$router.go(0);
          }
        })
      },
      removeFromBlacklist (row) {
        axios.post('/api/admin/blacklistDel', {
          number: this.studentInfoTable[row].stuNum
        }).then(resp => {
          if (resp.status === 200) {
            Message({
              message: '操作成功！',
              type: 'success'
            })
            this.$router.go(0);
          }
        })
      },
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
        this.$router.replace('/login')
      },
      backToIndex () {
        this.$router.replace('/admin/index')
      }, reservationList (row) {
        console.log(this.studentInfoTable[row])
        this.$store.commit('setStuNum', { number: this.studentInfoTable[row].stuNum })
        this.$router.replace('/admin/reservation')
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

  .container {
    flex-direction: column;
    align-items: center;
    margin-top: -50px;
  }


  .stu-search {
    margin-top: 50vh;
    transform: translateY(-50%);
  }

  .form-center {
    margin: 0 auto;
  }

  .info-table {
    width: 1200px;
    /* 自定义宽度 */
    margin: 0 auto;
  }

  .info1 {
    font-size: 24px;
    font-family: "PingFang SC";
    margin-top: 80px;
  }
</style>