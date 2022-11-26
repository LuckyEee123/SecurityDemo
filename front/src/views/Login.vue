<template>
  <div style="height: 100vh; overflow: hidden">
    <div style="height: 50px; line-height: 50px; border-bottom: 2px solid; padding-left: 50px;">
      <b style="font-size: 24px;">SecurityDemo</b>
    </div>

    <div
      style="width: 50%; margin: 100px auto; border-radius: 10px; box-shadow: 0 0 10px -2px cornflowerblue; display: flex;">
      <div style="flex: 1; padding: 100px 50px">
        <img src="../assets/imgs/loginBackground.png" alt="" style="width: 100%;">
      </div>

      <div style="flex: 1; padding: 50px">
        <div class="form-toggle" style="margin-bottom:20px">
          <b>账号登录</b>
        </div>

        <el-form ref="form" :model="loginForm">
          <el-form-item>
            <el-input style="color:black" size="medium" v-model="loginForm.userName" prefix-icon="Search"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input size="medium" show-password v-model="loginForm.password" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <div style="margin: 10px 0; text-align: right">
            忘记密码
          </div>
          <div>
            <el-button type="primary" size="medium" style="width: 100%" @click="login">登录</el-button>
          </div>
          <div style="margin: 10px 0; text-align: right;">
            <a href="/#/register">立即注册</a>
          </div>
        </el-form>

      </div>
    </div>
  </div>
</template>
  
<script setup>
import { ElMessage } from 'element-plus'
import request from '../utils/request'
import { ref } from 'vue'
import { useRouter } from 'vue-router'


name: "Login"
let loginForm = ref({})

const router = useRouter()

async function login() {
  let res = await request.post('/user/login', loginForm.value)
  if (res.data) {
    localStorage.setItem("user", JSON.stringify(res.data))
    ElMessage({
      showClose: true,
      message: '登录成功',
    })
    router.push('/')
  }
}

</script>
  
<style scoped>

</style>