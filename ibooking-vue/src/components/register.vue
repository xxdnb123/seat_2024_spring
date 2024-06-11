<template>
  <div id="base_register">
    <el-form :model="registerForm" :rules="rules" class="register_container" label-position="left" label-width="0px"
      v-loading="loading" :ref="registerForm" style="height: 500px;">
      <h3 class="register_title">注册</h3>
      <el-form-item prop="stuNum" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">学号：</label></el-col>
          <el-col :span="8"><el-input type="text" v-model="registerForm.stuNum" auto-complete="off" placeholder="请输入学号"
              class="input">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="fullName" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">姓名：</label></el-col>
          <el-col :span="8"><el-input type="text" v-model="registerForm.fullName" auto-complete="off"
              placeholder="请输入姓名" class="input">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="password" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">密码：</label></el-col>
          <el-col :span="8"><el-input type="password" v-model="registerForm.password" auto-complete="off"
              placeholder="请输入密码" class="input">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="major" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">专业：</label></el-col>
          <el-col :span="8"><el-input type="text" v-model="registerForm.major" auto-complete="off" placeholder="请输入专业"
              class="input">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="school" style="margin: 20px;" label=" ">
        <el-row>
          <el-col :span="4"><label class="date">学院：</label></el-col>
          <el-col :span="8"><el-input type="text" v-model="registerForm.school" auto-complete="off" placeholder="请输入学院"
              class="input">
            </el-input></el-col>
        </el-row>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 150px;background: dodgerblue;border-radius: 4px;height: 40px;"
          v-on:click="register(registerForm)">注册
        </el-button>
      </el-form-item>
      <p>
        已有账号？去
        <router-link to="login" style="text-decoration: none;color: red;">
          登录
        </router-link>
      </p>
    </el-form>
  </div>
</template>

<script>
  import axios from 'axios'
  import { Message } from 'element-ui'
  export default {
    name: 'Register',
    data () {
      const dataValid = (rule, value, callback) => {
        if (!value || value === '') {
          return callback(new Error('不能为空！'))
        }
        return callback()
      }
      const passwordCheck = (rule, value, callback) => {
        const reg = /^(?![\d]+$)(?![a-zA-Z]+$)(?![_-]+$)[\da-zA-Z_-]{6,32}$/;
        if (!value || value === '') {
          return callback(new Error('不能为空！'));
        } else if (!reg.test(value)) {
          return callback(new Error('密码需要字母，数字或者特殊字符（-_）至少包含两种，长度为6-32个字符'));
        } else {
          return callback();
        }
      };
      return {
        registerForm: {
          stuNum: '',
          fullName: '',
          password: '',
          major: '',
          school: '',
        },
        rules: {
          stuNum: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }],
          fullName: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }],
          password: [{ required: true, message: '', trigger: 'blur' }, { validator: passwordCheck, trigger: 'blur' }],
          major: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }],
          school: [{ required: true, message: '', trigger: 'blur' }, { validator: dataValid, trigger: 'blur' }]
        },
        loading: false
      }
    }, methods: {
      register (formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            axios.post('/api/user/register', {
              number: this.registerForm.stuNum,
              name: this.registerForm.fullName,
              password: this.registerForm.password,
              major: this.registerForm.major,
              college: this.registerForm.school
            }).then(resp => {
              if (resp.status === 200) {
                Message({
                  message: '注册成功！',
                  type: 'success'
                })
                this.$router.replace('/login')
              } else {
                Message({
                  message: '注册失败，请稍后重试',
                  type: "error",
                  showClose: true,
                  duration: 0
                });
              }
            }).catch(error => {
              this.$message({
                message: ''
              })
            })
          } else {
            Message({
              message: '请按照要求填写每项注册信息！',
              type: "warning",
              showClose: true,
              duration: 0
            });
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

  .date {
    float: left;
    width: 100px;
    padding-right: 12px;
    text-align: left;
  }

  .input {
    width: 350px;
    padding: 0;
  }

  .register_container {
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

  .register_title {
    margin: 0 auto 10px auto;
    text-align: center;
    color: #505458;
  }
</style>