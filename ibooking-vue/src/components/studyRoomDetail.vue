<template>
  <div class="Room-Detail">
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="Table">
      <el-row class="info">
        <p>您所在的位置：首页 - 自习室查询 - 自习室详情</p>
        <p>当前教室：{{this.$store.state.campus}} - {{this.$store.state.region}} - {{this.$store.state.building}} -
          {{this.$store.state.floor}}</p>
        <p>当前时间段：{{this.$store.state.startTime}} ~ {{this.$store.state.endTime}}</p>
      </el-row>
      <el-form>
        <el-table :data="seatTable">
          <el-table-column prop="row" label="行号"></el-table-column>
          <el-table-column prop="column" label="列号"></el-table-column>
          <el-table-column prop="socket" label="是否有插座"></el-table-column>
          <el-table-column prop="status" label="状态"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="reserve(scope.$index)"
                v-bind:disabled="reserveDisabled(scope.$index)">预约</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'StudyRoomDetail',
    data () {
      return {
        seatTable: []
      }
    }, methods: {
      reserve (row) {
        console.log(this.$store.state.stuNum)
        console.log(this.seatTable[row].id)
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        month = (month > 9) ? month : ("0" + month);
        day = (day < 10) ? ("0" + day) : day;
        // console.log(row)
        console.log(year + "-" + month + "-" + day + "T" + this.$store.state.startTime + ":00")
        console.log(year + "-" + month + "-" + day + "T" + this.$store.state.endTime + ":00")
        var hours = date.getHours();
        if (hours == this.$store.state.startTime.substring(0, 2)) {
          axios.post('api/stu/reserveSeize', {
            number: this.$store.state.stuNum,
            id: this.seatTable[row].id,
            openTime: year + "-" + month + "-" + day + "T" + this.$store.state.startTime + ":00",
            closeTime: year + "-" + month + "-" + day + "T" + this.$store.state.endTime + ":00"
          }).then(resp => {
            if (resp.status === 200) {
              Message({
                message: '抢占预约成功！',
                type: 'success'
              })
              this.$router.go(0);
            }
          }).catch(error => {
            Message({
              message: '抢占预约失败！请稍后再试',
              type: 'error'
            })
          })
        } else {
          axios.post('api/stu/reserve', {
            number: this.$store.state.stuNum,
            id: this.seatTable[row].id,
            openTime: year + "-" + month + "-" + day + "T" + this.$store.state.startTime + ":00",
            closeTime: year + "-" + month + "-" + day + "T" + this.$store.state.endTime + ":00"
          }).then(resp => {
            if (resp.status === 200) {
              Message({
                message: '预约成功！',
                type: 'success'
              })
              this.$router.go(0);
            }
          }).catch(error => {
            Message({
              message: '预约失败！请稍后再试',
              type: 'error'
            })
          })
        }

      },
      reserveDisabled (row) {
        if (this.$store.state.adminId !== null) {
          return true;
        }
        if (this.seatTable[row].status === "不可用") {
          return true;
        } else {
          return false;
        }
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
        if (this.$store.state.stuNum === null) {
          this.$router.replace('/admin/index')
        } else {
          this.$router.replace('/stuIndex')
        }

      }
    }, beforeMount () {
      console.log(this.$store.state.startTime)
      console.log(this.$store.state.endTime)
      axios.post('api/stu/studyRoomDetail', {
        id: this.$store.state.studyRoomID,
        startTime: this.$store.state.startTime,
        endTime: this.$store.state.endTime
      }).then(resp => {
        console.log(resp.data)
        if (resp.status === 200) {
          this.seatTable.splice(0, this.seatTable.length);
          for (var i = 0; i < resp.data.length; i++) {
            this.seatTable.push({
              id: resp.data[i].id,
              row: resp.data[i].row,
              column: resp.data[i].column,
              socket: resp.data[i].hasSocket == 'true' ? "是" : "否",
              status: resp.data[i].status === "0" ? "不可用" : "可用",
            })
          }
        }
      })
    }
  }
</script>
<style scope>
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

  .Table {
    width: 80%;
    margin: auto;
    z-index: 0;
    background-color: #f5f5f5;
    /* 表格背景颜色 */
    /*border: 1px solid #ccc; /* 表格边框 */
    padding: 20px;
    border-radius: 5px;
    /* 表格圆角 */
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .info {
    font-size: 24px;
    font-family: "PingFang SC";
    margin-top: -150px;
    margin-bottom: 50px;
  }
</style>