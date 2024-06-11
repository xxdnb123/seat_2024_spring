<template>
  <div class="studyRoom-List">
    <div class="info-dropdown">
      <el-dropdown @command="handleCommand" placement="top">
        <i class="el-icon-user stu-info-icon"></i>
        <el-dropdown-menu slot="dropdown" class="info-dropdown-menu">
          <el-dropdown-item command="backToIndex">回首页</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="container">
      <el-row class="position">
        <p>您所在的位置：首页 - 自习室查询</p>
      </el-row>
      <el-row>
        <div class="search">
          <el-form :model="searchForm" :rules="rules" :ref="searchForm">
            <el-form-item prop="campus" label="" style="margin: 20px;">
              <el-row>
                <el-col :span="4" :offset="4"><label class="date">校区：</label></el-col>
                <el-col :span="8">
                  <el-select v-model="searchForm.campus" placeholder="请选择校区" @change="getRegion" class="input">
                    <el-option v-for="item in studyRoom" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item prop="region" label="" style="margin: 20px;">
              <el-row>
                <el-col :span="4" :offset="4"><label class="date">区域：</label></el-col>
                <el-col :span="8">
                  <el-select v-model="searchForm.region" placeholder="请选择区域" @change="getBuilding" class="input">
                    <el-option v-for="item in regionList" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item prop="building" label="" style="margin: 20px;">
              <el-row>
                <el-col :span="4" :offset="4"><label class="date">教学楼：</label></el-col>
                <el-col :span="8">
                  <el-select v-model="searchForm.building" placeholder="请选择教学楼" @change="getFloor" class="input">
                    <el-option v-for="item in buildingList" :key="item.value" :value="item.value"
                      :label="item.label"></el-option>
                  </el-select>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item prop="floor" label="" style="margin: 20px;">
              <el-row>
                <el-col :span="4" :offset="4"><label class="date">教室：</label></el-col>
                <el-col :span="8">
                  <el-select v-model="searchForm.floor" placeholder="请选择教室" class="input">
                    <el-option v-for="item in floorList" :key="item" :value="item" :label="item"></el-option>
                  </el-select>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item prop="time" label="" style="margin: 20px;">
              <el-row>
                <el-col :span="4" :offset="4"><label class="date">时间：</label></el-col>
                <el-col :span="4">
                  <el-time-select placeholder="请选择起始时间" v-model="searchForm.time[0]"
                    :picker-options="{start:'08:00',step:'00:60',end:'22:00',minTime:startTime}"
                    class="input"></el-time-select></el-col>
                <el-col :span="4">
                  <el-time-select placeholder="请选择结束时间" v-model="searchForm.time[1]"
                    :picker-options="{start:'08:00',step:'00:60',end:'22:00',minTime:searchForm.time[0]}"
                    class="input"></el-time-select></el-col>
              </el-row>
            </el-form-item>

            <el-form-item style="margin: 20px;">
              <el-row>
                <el-button type="primary" v-on:click="search(searchForm)">查询</el-button>
              </el-row>
            </el-form-item>
          </el-form>
        </div>
      </el-row>
      <el-row>
        <div class="Table">
          <el-table :data="studyRoomTable">
            <el-table-column prop="campus" label="校区"></el-table-column>
            <el-table-column prop="region" label="区域"></el-table-column>
            <el-table-column prop="building" label="教学楼"></el-table-column>
            <el-table-column prop="floor" label="教室"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="seat" label="空余座位数"></el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button size="mini" @click="studyRoomDetail(scope.$index)"
                  v-bind:disabled="detailDisabled(scope.$index)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-row>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'StudyRoomList',
    data () {
      const timeValid = (rule, value, callback) => {
        if (value.length == 1 || (value.length == 2 && ((value[0] == null && value[1] != null) || (value[1] == null && value[0] != null)))) {
          return callback(new Error('起始时间和结束时间需要同时选择！'))
        } else {
          return callback()
        }
      }
      return {
        rules: {
          time: [{ validator: timeValid, trigger: 'blur' }]
        },
        searchForm: {
          campus: '',
          region: '',
          building: '',
          floor: '',
          time: [this.getTime()[0], this.getTime()[1]]
        },
        studyRoom: this.$store.state.studyRoom,
        regionList: null,
        buildingList: null,
        floorList: [],
        studyRoomTable: [],
        startTime: this.getTime()[2]
      }
    }, methods: {
      getTime () {
        var date = new Date();
        var hours = date.getHours();
        return [hours + ":00", (hours + 1) + ":00", (hours - 1) + ":00"]
      },
      search (formName) {
        this.studyRoomTable.splice(0, this.studyRoomTable.length);
        console.log(this.searchForm)
        this.$refs[formName].validate(valid => {
          if (valid) {
            axios.post('/api/stu/studyRoomList', {
              campus: this.searchForm.campus,
              region: this.searchForm.region,
              building: this.searchForm.building,
              floor: this.searchForm.floor,
              openTime: this.searchForm.time[0],
              closeTime: this.searchForm.time[1]
            }).then(resp => {
              if (resp.status === 200) {
                Message({
                  message: '查询成功！',
                  type: 'success'
                })
                console.log(resp.data)
                for (var k = 0; k < resp.data.length; k++) {
                  for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
                    if (this.$store.state.studyRoom[i].value == resp.data[k].campus) {
                      var campusData = this.$store.state.studyRoom[i].label;
                      for (var j = 0; j < this.$store.state.studyRoom[i].region.length; j++) {
                        if (this.$store.state.studyRoom[i].region[j].value == resp.data[k].region) {
                          var regionData = this.$store.state.studyRoom[i].region[j].label;
                          for (var l = 0; l < this.$store.state.studyRoom[i].region[j].building.length; l++) {
                            if (this.$store.state.studyRoom[i].region[j].building[l].value == resp.data[k].building) {
                              var buildingData = this.$store.state.studyRoom[i].region[j].building[l].label;
                            }
                          }
                        }
                      }
                    }
                  }
                  this.studyRoomTable.push({
                    campus: campusData,
                    region: regionData,
                    building: buildingData,
                    floor: resp.data[k].number,
                    status: resp.data[k].status === "0" ? "不可用" : "可用",
                    seat: resp.data[k].availableSeatNumber,
                    id: resp.data[k].id,
                    openTime: resp.data[k].openTime,
                    closeTime: resp.data[k].closeTime
                  })
                }
              }
            })
          } else {
            Message({
              message: '请按照要求填写查询信息',
              type: "warning"
            })
          }

        })
      },
      getRegion (code) {
        this.regionList = null;
        this.searchForm.region = null;
        this.buildingList = null;
        this.searchForm.building = null;
        this.floorList = []
        this.searchForm.floor = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.searchForm.campus) {
            this.regionList = this.$store.state.studyRoom[i].region;
          }
        }
      },
      getBuilding (code) {
        this.buildingList = null;
        this.searchForm.building = null;
        this.floorList = []
        this.searchForm.floor = null;
        for (var i = 0; i < this.$store.state.studyRoom.length; i++) {
          if (this.$store.state.studyRoom[i].value === this.searchForm.campus) {
            for (var j = 0; j < this.$store.state.studyRoom[i].region.length; j++) {
              if (this.$store.state.studyRoom[i].region[j].value === this.searchForm.region) {
                this.buildingList = this.$store.state.studyRoom[i].region[j].building;
              }
            }
          }
        }
      },
      getFloor (code) {
        axios.post('/api/stu/studyRoomList', {
          campus: this.searchForm.campus,
          region: this.searchForm.region,
          building: this.searchForm.building,
          number: null,
          openTime: null,
          closeTime: null
        }).then(resp => {
          if (resp.status === 200) {
            // console.log(resp.data[0].number);
            // this.floorList = [];
            this.floorList.splice(0, this.floorList.length);
            for (var i = 0; i < resp.data.length; i++) {
              this.floorList.push(resp.data[i].number);
            }
          }
        })
      }, studyRoomDetail (index) {
        console.log(this.studyRoomTable[index])
        this.$store.commit('setStudyRoomInfo', this.studyRoomTable[index]);
        if (this.searchForm.time[0] != null && this.searchForm.time[1] != null) {
          this.$store.commit('setStudyRoom', { id: this.studyRoomTable[index].id, startTime: this.searchForm.time[0], endTime: this.searchForm.time[1] })
        } else {
          this.$store.commit('setStudyRoom', { id: this.studyRoomTable[index].id, startTime: this.studyRoomTable[index].openTime, endTime: this.studyRoomTable[index].closeTime })
        }
        this.$router.replace('/studyRoomDetail')
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
        this.$router.replace('/stuIndex')
      },
      detailDisabled (row) {
        return this.studyRoomTable[row].status === "不可用"
      }
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

  .container {
    flex-direction: column;
    /*align-items: center;*/
  }

  .search {
    margin-top: 35vh;
    transform: translateY(-50%);
  }

  .Table {
    width: 1200px;
    /* 自定义宽度 */
    margin: 0 auto;
  }

  .position {
    margin-top: 80px;
    font-size: 24px;
    font-family: "PingFang SC";
  }
</style>