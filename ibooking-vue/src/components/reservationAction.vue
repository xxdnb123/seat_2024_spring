<template>
  <div>
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="reservationTable">
      <p class="info">您所在的位置：首页 - 签到/取消预约</p>
      <el-table :data="reservationTable">
        <!-- <el-table-column prop="row" label="行号"></el-table-column>
    <el-table-column prop="column" label="列号"></el-table-column>
    <el-table-column prop="socket" label="是否有插座"></el-table-column>
    <el-table-column prop="status" label="状态"></el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button size="mini" @click="reserve(scope.$index)"
          v-bind:disabled="reserveDisabled(scope.$index)">预约</el-button>
      </template>
    </el-table-column> -->
        <el-table-column prop="campus" label="校区"></el-table-column>
        <el-table-column prop="region" label="区域"></el-table-column>
        <el-table-column prop="building" label="教学楼"></el-table-column>
        <el-table-column prop="floor" label="教室"></el-table-column>
        <el-table-column prop="row" label="座位行号"></el-table-column>
        <el-table-column prop="column" label="座位列号"></el-table-column>
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <el-table-column label="签到">
          <template slot-scope="scope">
            <el-button size="mini" @click="dialogCancelVisiable=true"
              v-bind:disabled="signInDisabled(scope.$index)">签到</el-button>
            <el-dialog title="签到" :visible.sync="dialogCancelVisiable">
              <el-form :model="cancelForm" :rules="rules" :ref="cancelForm">
                <el-form-item prop="campus" label="校区">
                  <el-select v-model="cancelForm.campus" placeholder="请选择校区" @change="getRegion" class="input">
                    <el-option v-for="item in studyRoom" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="region" label="区域">
                  <el-select v-model="cancelForm.region" placeholder="请选择区域" @change="getBuilding" class="input">
                    <el-option v-for="item in regionList" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="building" label="教学楼">
                  <el-select v-model="cancelForm.building" placeholder="请选择教学楼">
                    <el-option v-for="item in buildingList" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="floor" label="教室">
                  <el-input v-model="cancelForm.floor" placeholder="请输入教室号"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogCancelVisiable=false">取消</el-button>
                <el-button @click="signIn(scope.$index)" type="primary">签到</el-button>
              </div>
            </el-dialog>
          </template>
        </el-table-column>
        <el-table-column label="取消预约">
          <template slot-scope="scope">
            <el-button size="mini" @click="cancel(scope.$index)"
              v-bind:disabled="cancelDisabled(scope.$index)">取消预约</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import axios from 'axios';
  import { Message } from 'element-ui'
  export default {
    data () {
      return {
        reservationTable: [],
        dialogCancelVisiable: false,
        cancelForm: {
          campus: '',
          region: '',
          building: '',
          floor: ''
        },
        studyRoom: this.$store.state.studyRoom,
        regionList: null,
        buildingList: null,
      }
    }, methods: {
      getStatus (status) {
        if (status === 0) {
          return "预约成功待签到"
        } else if (status === 1) {
          return "已签到使用中"
        } else if (status === 2) {
          return "完成使用"
        } else if (status === 3) {
          return "过期取消"
        } else if (status === 4) {
          return "学生取消"
        } else if (status === 5) {
          return "管理员取消"
        } else {
          return "未知状态"
        }
      }, getType (type) {
        if (type === 0) {
          return "正常预约"
        } else if (type === 1) {
          return "抢占预约"
        } else {
          return "未知类型"
        }
      }, signIn (row) {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        month = (month > 9) ? month : ("0" + month);
        day = (day < 10) ? ("0" + day) : day;
        var hours = date.getHours();
        var minute = date.getMinutes();
        minute = (minute < 10) ? ("0" + minute) : minute;
        var seconds = date.getSeconds();
        seconds = (seconds < 10) ? ("0" + seconds) : seconds;
        console.log(hours)
        console.log(minute)
        console.log(seconds)
        var signTime = year + "-" + month + "-" + day + "T" + hours + ":" + minute + ":" + seconds
        axios.post('api/stu/signIn', {
          id: this.reservationTable[row].id,
          time: signTime,
          campus: this.cancelForm.campus,
          region: this.cancelForm.region,
          building: this.cancelForm.building,
          floor: this.cancelForm.floor
        }).then(resp => {
          Message({
            message: '签到成功！',
            type: 'success'
          })
          this.$router.go(0)
        }).catch(error => {
          Message({
            message: '签到失败！',
            type: 'error'
          })
          // this.$router.go(0)
        })
      }, signInDisabled (row) {

      }, cancel (row) {
        console.log(this.reservationTable[row].id)
        axios.post('/api/stu/reserveCancel', {
          id: this.reservationTable[row].id
        }).then(resp => {
          if (resp.status === 200) {
            Message({
              message: '取消成功！',
              type: 'success'
            })
            this.$router.go(0)
          }
        }).catch(error => {
          Message({
            message: '取消失败！',
            type: 'error'
          })
        })
      }, cancelDisabled (row) {

      }, getRegion (code) {
        this.regionList = null;
        this.cancelForm.region = null;
        this.buildingList = null;
        this.cancelForm.building = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.cancelForm.campus) {
            this.regionList = this.$store.state.studyRoom[i].region;
          }
        }
      },
      getBuilding (code) {
        this.buildingList = null;
        this.cancelForm.building = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.cancelForm.campus) {
            for (var j = 0; j < this.$store.state.studyRoom[i].region.length; j++) {
              if (this.$store.state.studyRoom[i].region[j].value === this.cancelForm.region) {
                this.buildingList = this.$store.state.studyRoom[i].region[j].building;
              }
            }
          }
        }
      }, handleCommand (command) {
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
    }, beforeMount () {
      console.log(this.$store.state.stuNum)
      axios.post('api/stu/reservationList', {
        number: this.$store.state.stuNum
      }).then(resp => {
        if (resp.status === 200) {
          console.log(resp.data)
          for (var k = 0; k < resp.data.length; k++) {
            if (resp.data[k].status != 0) {
              continue
            }
            for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
              if (this.$store.state.studyRoom[i].value == resp.data[k].campusId) {
                var campusData = this.$store.state.studyRoom[i].label;
                for (var j = 0; j < this.$store.state.studyRoom[i].region.length; j++) {
                  if (this.$store.state.studyRoom[i].region[j].value == resp.data[k].regionId) {
                    var regionData = this.$store.state.studyRoom[i].region[j].label;
                    for (var l = 0; l < this.$store.state.studyRoom[i].region[j].building.length; l++) {
                      if (this.$store.state.studyRoom[i].region[j].building[l].value == resp.data[k].buildingId) {
                        var buildingData = this.$store.state.studyRoom[i].region[j].building[l].label;
                      }
                    }
                  }
                }
              }
            }
            this.reservationTable.push({
              campus: campusData,
              region: regionData,
              building: buildingData,
              floor: resp.data[k].number,
              row: resp.data[k].row,
              column: resp.data[k].column,
              startTime: resp.data[k].startTime.substring(0, 10) + " " + resp.data[k].startTime.substring(11, 16),
              endTime: resp.data[k].endTime.substring(0, 10) + " " + resp.data[k].endTime.substring(11, 16),
              status: this.getStatus(resp.data[k].status),
              type: this.getType(resp.data[k].type),
              id: resp.data[k].id
            })
          }
        }
      })
    }
  } 
</script>
<style>
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

  .reservationTable {
    width: 1200px;
    flex-direction: column;
    margin: 0 auto;
    margin-top: 200px;
  }
</style>