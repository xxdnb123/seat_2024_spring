import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store(
  {
    state: {
      stuNum: localStorage.getItem('stuNum') || null,
      stuName: localStorage.getItem('stuName') || null,
      studyRoom: [{
        label: '邯郸校区',
        value: 1,
        region: [{
          label: '北区',
          value: 101,
          building: [{
            label: '第四教学楼',
            value: 101001
          }]
        }, {
          label: '西区',
          value: 102,
          building: [{
            label: '第二教学楼',
            value: 102001,
          }, {
            label: '第三教学楼',
            value: 102002
          }]
        }, {
          label: '南区',
          value: 103,
          building: [{
            label: '第五教学楼',
            value: 103001,
          }, {
            label: '第六教学楼',
            value: 103002
          }]
        }]
      }, {
        label: '江湾校区',
        value: 2,
        region: [{
          label: '东区',
          value: 201,
          building: [{
            label: 'JA教学楼',
            value: 201001
          }, {
            label: 'JB教学楼',
            value: 201002
          }]
        }]
      }, {
        label: '张江校区',
        value: 3,
        region: [{
          label: '北区',
          value: 301,
          building: [{
            label: '第一教学楼',
            value: 301001
          }, {
            label: '第二教学楼',
            value: 301002
          }]
        }]
      }],
      studyRoomID: localStorage.getItem('studyRoomID') || null,
      startTime: localStorage.getItem('startTime') || null,
      endTime: localStorage.getItem('endTime') || null,
      adminId: localStorage.getItem('adminId') || null,
      campus: localStorage.getItem('campus') || null,
      region: localStorage.getItem('region') || null,
      building: localStorage.getItem('building') || null,
      floor: localStorage.getItem('floor') || null
    },
    mutations: {
      setStuNum (state, data) {
        localStorage.setItem('stuNum', data.number);
        state.stuNum = data.number
        localStorage.setItem('stuName', data.name);
        state.stuName = data.name
      },
      setStudyRoom (state, data) {
        localStorage.setItem('studyRoomID', data.id);
        state.studyRoomID = data.id;
        localStorage.setItem('startTime', data.startTime);
        state.startTime = data.startTime;
        localStorage.setItem('endTime', data.endTime);
        state.endTime = data.endTime
      },
      logout (state) {
        localStorage.removeItem('stuNum');
        localStorage.removeItem('studyRoomID');
        localStorage.removeItem('startTime');
        localStorage.removeItem('endTime');
        state.stuNum = null;
        state.studyRoomID = null;
        state.startTime = null;
        state.endTime = null;
        localStorage.removeItem('adminId');
        state.adminId = null;
        localStorage.removeItem('stuName');
        state.stuName = null;
        localStorage.removeItem('campus');
        state.campus = null;
        localStorage.removeItem('region');
        state.region = null;
        localStorage.removeItem('building');
        state.building = null;
        localStorage.removeItem('floor');
        state.floor = null;
      },
      setAdminId (state, data) {
        localStorage.setItem('adminId', data.id);
        state.adminId = data.id;
      }, setStudyRoomInfo (state, data) {
        localStorage.setItem('campus', data.campus);
        state.campus = data.campus;
        localStorage.setItem('region', data.region);
        state.region = data.region;
        localStorage.setItem('building', data.building);
        state.building = data.building;
        localStorage.setItem('floor', data.floor);
        state.floor = data.floor
      }
    }
  }
)