import Vue from 'vue'
import Router from 'vue-router'
import Register from '../components/register.vue'
import Login from '../components/login.vue'
import StuInfo from '../components/stuInfo.vue'
import AdminStuInfo from '../components/adminStuInfo.vue'
import StudyRoomList from '../components/studyRoomList.vue'
import StudyRoomDetail from '../components/studyRoomDetail.vue'
import AdminStudyRoomList from '../components/adminStudyRoomList.vue'
import AdminStudyRoomAdd from '../components/adminStudyRoomAdd.vue'
import StuIndex from '../components/stuIndex.vue'
import AdminIndex from '../components/adminIndex.vue'
import Reservation from '../components/reservation.vue'
import ReservationAction from '../components/reservationAction.vue'
import AdminReservation from '../components/adminReservation.vue'
Vue.use(Router)

export const router = new Router({
  mode: "history",
  routes: [
    {
      path: '/register',
      name: 'Register',
      component: Register
    }, {
      path: '/login',
      name: 'Login',
      component: Login
    }, {
      path: '/',
      component: Login
    }, {
      path: '/stuInfo',
      name: 'stuInfo',
      component: StuInfo
    }, {
      path: '/admin/stuInfo',
      name: 'adminStuInfo',
      component: AdminStuInfo
    }, {
      path: '/studyRoomList',
      name: 'studyRoomList',
      component: StudyRoomList
    }, {
      path: '/studyRoomDetail',
      name: 'studyRoomDetail',
      component: StudyRoomDetail
    }, {
      path: '/admin/studyRoomList',
      name: 'adminStudyRoomList',
      component: AdminStudyRoomList
    }, {
      path: '/admin/studyRoomAdd',
      name: 'adminStudyRoomAdd',
      component: AdminStudyRoomAdd
    }, {
      path: '/stuIndex',
      name: 'stuIndex',
      component: StuIndex
    }, {
      path: '/admin/index',
      name: 'adminIndex',
      component: AdminIndex
    }, {
      path: '/reservation',
      name: 'reservation',
      component: Reservation
    }, {
      path: '/reservationAction',
      name: 'reservationAction',
      component: ReservationAction
    }, {
      path: '/admin/reservation',
      name: 'adminReservation',
      component: AdminReservation
    }
  ]
})