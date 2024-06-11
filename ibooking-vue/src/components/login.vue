<template>
  <div id="base_login">
    <el-form :model="loginForm" :rules="rules" class="login_container" label-position="left" label-width="0px"
      v-loading="loading" :ref="loginForm" style="height: 300px;margin-bottom: 50px">
      <h3 class="login_title">登录</h3>
      <el-form-item prop="username" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">账号：</label></el-col>
          <el-col :span="8"><el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入账号"
              width="350px" class="input" prefix-icon="el-icon-user">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="password" style="margin:20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">密码：</label></el-col>
          <el-col :span="8"><el-input type="password" v-model="loginForm.password" auto-complete="off"
              placeholder="请输入密码" width="350px" class="input" show-password prefix-icon="el-icon-lock">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item style="width: 100%;">
        <el-button type="primary" style="width: 150px;background: dodgerblue;border-radius: 4px;height: 40px;"
          v-on:click="login(loginForm)">登录
        </el-button>
      </el-form-item>
      <el-form-item>
        <p>
          还没有账号？去
          <router-link to="register" style="text-decoration: none;color: red;">
            注册
          </router-link>
        </p>
      </el-form-item>
    </el-form>
  </div>

</template>
<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'Login',
    data () {
      const dataValid = (rule, value, callback) => {
        if (!value || value === '') {
          return callback(new Error('不能为空！'))
        }
        return callback()
      }
      return {
        loginForm: {
          username: '',
          password: '',
        },
        rules: {
          username: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }],
          password: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }]
        },
        loading: false
      }
    },
    methods: {
      login (formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            axios.post('api/user/login', {
              number: this.loginForm.username,
              password: this.loginForm.password
            }
            ).then(resp => {
              if (resp.status === 200) {
                Message({
                  message: '登录成功！',
                  type: 'success'
                })
                console.log(resp.data)
                if ('id' in resp.data) {
                  this.$router.replace('/admin/index')
                  this.$store.commit('setAdminId', resp.data)
                } else {
                  this.$store.commit('setStuNum', resp.data)
                  console.log(this.$store.state.stuNum)
                  this.$router.replace('/stuIndex')
                }
              }
            }).catch(error => {
              Message({
                message: '登录失败，请确认您的账号和密码都正确输入了！',
                type: 'error'
              })
            })
          } else {
            Message({
              message: '请确认账号和密码都已输入',
              type: "warning",
              showClose: true,
              duration: 0
            })
          }
        })
      }
    }
  }
</script>
<style scoped>
  p {
    margin: 0;
  }

  body {
    margin: 0px;
    padding: 0px;
  }

  .date {
    float: left;
    width: 100;
    padding-right: 12px;
    text-align: left;
  }

  .input {
    width: 300px;
    padding: 0;
  }

  .login_container {
    border-radius: 15px;
    background: #fff;
    background-clip: padding-box;
    margin: 0 auto;
    width: 600px;
    padding: 10px 35px 0 35px;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .login_title {
    margin: 0px auto 10px auto;
    text-align: center;
    color: #505458;
  }

  input {
    width: 300px;
    height: 24px;
    border: 2px solid #ccc;
    border-radius: 4px;
  }
</style>