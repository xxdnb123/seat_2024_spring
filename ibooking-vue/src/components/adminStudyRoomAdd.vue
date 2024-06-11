<template>
  <div class="admin-studyRoom-add">
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="roomForm">
      <p class="info2">您所在的位置：首页 - 新增自习室</p>
      <div>
        <el-form :model="studyRoomForm" :rules="rules" :ref="studyRoomForm">
          <el-form-item prop="campus" label="">
            <el-row>
              <el-col :span="4" :offset="4"><label class="date">校区：</label></el-col>
              <el-col :span="8">
                <el-select v-model="studyRoomForm.campus" placeholder="请选择校区" @change="getRegion">
                  <el-option v-for="item in studyRoom" :key="item.value" :value="item.value"
                    :label="item.label"></el-option>
                </el-select>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item prop="region" label="">
            <el-col :span="4" :offset="4"><label class="date">区域：</label></el-col>
            <el-col :span="8">
              <el-select v-model="studyRoomForm.region" placeholder="请选择区域" @change="getBuilding">
                <el-option v-for="item in regionList" :key="item.value" :value="item.value"
                  :label="item.label"></el-option>
              </el-select>
            </el-col>
          </el-form-item>
          <el-form-item prop="building" label="">
            <el-col :span="4" :offset="4"><label class="date">教学楼：</label></el-col>
            <el-col :span="8">
              <el-select v-model="studyRoomForm.building" placeholder="请选择教学楼">
                <el-option v-for="item in buildingList" :key="item.value" :value="item.value"
                  :label="item.label"></el-option>
              </el-select>
            </el-col>
          </el-form-item>
          <el-form-item prop="floor" label="">
            <el-col :span="4" :offset="4"><label class="date">教室：</label></el-col>
            <el-col :span="8">
              <el-input v-model="studyRoomForm.floor" placeholder="请输入教室号" class="input"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item prop="time" label="">
            <el-col :span="4" :offset="4"><label class="date">时间：</label></el-col>
            <el-col :span="4">
              <el-time-select placeholder="请选择起始时间" v-model="studyRoomForm.time[0]"
                :picker-options="{start:'08:00',step:'00:60',end:'22:00'}"></el-time-select>
            </el-col>
            <el-col :span="4">
              <el-time-select placeholder="请选择结束时间" v-model="studyRoomForm.time[1]"
                :picker-options="{start:'08:00',step:'00:60',end:'22:00',minTime:studyRoomForm.time[0]}"></el-time-select>
            </el-col>
          </el-form-item>
          <el-form-item v-for="(item,index) in studyRoomForm.seats">
            <el-form-item label="">
              <el-col :span="4"><label class="date">座位{{index+1}}</label></el-col>
              <el-col :span="4">
                <el-form-item :prop="'seats.'+index+'.row'" label="">
                  <el-input v-model="item.row" placeholder="请输入座位行号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item :prop="'seats.'+index+'.column'" label="">
                  <el-input v-model="item.column" placeholder="请输入座位列号"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item :prop="'seats.'+index+'.hasSocket'" label="">
                  <el-select v-model="item.hasSocket" placeholder="请选择座位是否有插座">
                    <el-option v-for="option in socketOption" :key="option.value" :label="option.label"
                      :value="option.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item :prop="'seats.'+index+'.status'" label="">
                  <el-select v-model="item.status" placeholder="请选择座位状态">
                    <el-option v-for="option in statusOption" :key="option.value" :label="option.label"
                      :value="option.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-form-item>
          </el-form-item>
          <el-form-item>
            <el-button @click="addSeat">增加座位</el-button>
            <el-button @click="removeSeat" type="danger">删除座位</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="addStudyRoom(studyRoomForm)" type="primary">增加自习室</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'StudyRoomAdd',
    data () {
      const dataValid = (rule, value, callback) => {
        if (!value || value == '' || value == "") {
          return callback(new Error('不能为空！'))
        }
        return callback()
      }
      const timeValid = (rule, value, callback) => {
        if (value[0] == null || value[1] == null) {
          return callback(new Error('需要填写自习室开始时间和结束时间！'))
        }
      }
      const seatValid = (rule, value, callback) => {
        if (value.length === 1) {
          return callback(new Error('需要两个座位！'))
        }
        for (var i = 0; i < value.length; i++) {
          if (!value[i].row || value[i].row == '' || value[i].row == "") {
            return callback(new Error('需要填写座位信息！'))
          }
          if (!value[i].column || value[i].column == '' || value[i].column == "") {
            return callback(new Error('需要填写座位信息！'))
          }
          if (!value[i].socket || value[i].socket == '' || value[i].socket == "") {
            return callback(new Error('需要填写座位信息！'))
          }
          if (!value[i].status || value[i].status == '' || value[i].status == "") {
            return callback(new Error('需要填写座位信息！'))
          }
        }
        return callback()
      }
      return {
        studyRoomForm: {
          campus: '',
          region: '',
          building: '',
          floor: '',
          time: [null, null],
          seats: [{
            row: '',
            column: '',
            hasSocket: '',
            status: ''
          }]
        },
        socketOption: [{
          label: '是',
          value: true
        }, {
          label: '否',
          value: false
        }],
        statusOption: [{
          label: '不可用',
          value: 0
        }, {
          label: '可用',
          value: 1
        }],
        studyRoom: this.$store.state.studyRoom,
        regionList: null,
        buildingList: null,
      }
    }, methods: {
      addSeat () {
        this.studyRoomForm.seats.push({
          row: '',
          column: '',
          hasSocket: '',
          status: ''
        })
      },
      removeSeat () {
        if (this.studyRoomForm.seats.length === 1) {
          Message({
            message: '自习室至少需要含有一个座位信息！',
            type: 'error'
          })
        } else {
          this.studyRoomForm.seats.splice(this.studyRoomForm.seats.length - 1, this.studyRoomForm.seats.length);
        }
      }, getRegion (code) {
        this.regionList = null;
        this.studyRoomForm.region = null;
        this.buildingList = null;
        this.studyRoomForm.building = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.studyRoomForm.campus) {
            this.regionList = this.$store.state.studyRoom[i].region;
          }
        }
      },
      getBuilding (code) {
        this.buildingList = null;
        this.studyRoomForm.building = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.studyRoomForm.campus) {
            for (var j = 0; j < this.$store.state.studyRoom[i].region.length; j++) {
              if (this.$store.state.studyRoom[i].region[j].value === this.studyRoomForm.region) {
                this.buildingList = this.$store.state.studyRoom[i].region[j].building;
              }
            }
          }
        }
      }, addStudyRoom (formName) {
        console.log(this.studyRoomForm)
        // this.$refs[formName].validate(valid => {
        var valid = this.validate()
        console.log(valid)
        if (valid) {
          console.log(2)
          axios.post('/api/admin/studyRoomAdd', {
            campus: this.studyRoomForm.campus,
            region: this.studyRoomForm.region,
            building: this.studyRoomForm.building,
            number: this.studyRoomForm.floor,
            openTime: this.studyRoomForm.time[0],
            closeTime: this.studyRoomForm.time[1],
            seatsInfo: this.studyRoomForm.seats
          }).then(resp => {
            if (resp.status === 200) {
              Message({
                message: '添加成功！',
                type: "success",
              });
            }
          }).catch(error => {
            console.log(error)
            if (error.response.status === 400) {
              Message({
                message: '添加失败，该自习室信息已存在！',
                type: "error",
              });
            }
          })
        } else {
          console.log(1)
          // this.$message({
          //   message: '请填写每项自习室信息，不得留空！',
          //   type: "warning",
          // });
        }
        // })
      }, validate () {
        if (this.empty(this.studyRoomForm.campus)) {
          Message({
            message: '请选择校区！',
            type: "warning",
          });
          return false;
        }
        if (this.empty(this.studyRoomForm.region)) {
          Message({
            message: '请选择区域！',
            type: "warning",
          });
          return false;
        }
        if (this.empty(this.studyRoomForm.building)) {
          Message({
            message: '请选择教学楼！',
            type: "warning",
          });
          return false;
        } if (this.empty(this.studyRoomForm.floor)) {
          Message({
            message: '请填写教室信息！',
            type: "warning",
          });
          return false;
        }
        if (this.studyRoomForm.time[0] == null || this.studyRoomForm.time[1] == null) {
          Message({
            message: '需要填写自习室开始时间和结束时间！',
            type: "warning",
          });
          return false;
        }
        for (var i = 0; i < this.studyRoomForm.seats.length; i++) {
          if (this.studyRoomForm.seats[i].row === null || this.studyRoomForm.seats[i].row === '' || this.studyRoomForm.seats[i].row === "") {
            Message({
              message: '需要填写座位' + (i + 1) + '的行号！',
              type: "warning",
            });
            return false;
          }
          if (this.studyRoomForm.seats[i].column === null || this.studyRoomForm.seats[i].column === '' || this.studyRoomForm.seats[i].column === "") {
            Message({
              message: '需要填写座位' + (i + 1) + '的列号！',
              type: "warning",
            });
            return false;
          }
          if (this.studyRoomForm.seats[i].hasSocket === null || this.studyRoomForm.seats[i].hasSocket === '' || this.studyRoomForm.seats[i].hasSocket === "") {
            Message({
              message: '需要填写座位' + (i + 1) + '的插座信息！',
              type: "warning",
            });
            return false;
          }
          if (this.studyRoomForm.seats[i].status === null || this.studyRoomForm.seats[i].status === '' || this.studyRoomForm.seats[i].status === "") {
            Message({
              message: '需要填写座位' + (i + 1) + '的状态！',
              type: "warning",
            });
            return false;
          }
        }
        return true;
      }, empty (data) {
        if (data === null || data === '' || data === "") {
          return true;
        }
        return false;
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

  .roomForm {
    width: 1200px;
    margin: 0 auto;
    margin-top: 100px;
  }

  .info2 {
    font-size: 24px;
    font-family: "PingFang SC";
    margin-bottom: 60px;
  }

  .input {
    width: 220px;
  }
</style>>