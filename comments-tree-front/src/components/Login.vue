<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="user" status-icon label-width="80px">
          <h3>登录</h3>
          <hr>
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名或邮箱" prefix-icon></el-input>
          </el-form-item>
          <el-form-item id="password" prop="password" label="密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item id="remember" prop="remember">
            <router-link to="/register" >没有帐号，立即注册</router-link>
            <el-checkbox v-model="user.remember">记住我</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-upload" @click="doLogin()">登 录</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import storage from "../assets/js/storage";
export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: "",
        remember: false
      }
    };
  },
  created() {},
  methods: {
    doLogin() {
      if (!this.user.username) {
        this.$message.error("请输入用户名！");
        return;
      } else if (!this.user.password) {
        this.$message.error("请输入密码！");
        return;
      } else {
        //校验用户名和密码是否正确;
        axios
          .post("/user/login", {
            username: this.user.username,
            password: this.user.password,
            remember: this.user.remember
          })
          .then(res => {
            // console.log("输出response.data.status", res.data.status);

            let now = new Date();
            if (res.data.success) {



              //默认只存15分钟
              let exp = now.setMinutes(now.getMinutes()+15)
              if (this.remember){
                //记住我存30天
                exp = exp.setDate(now.getDate()+30)
              }

              console.log("remember me ",this.user.remember)
              //保存是否记住的标志
              storage.setItem({
                name: "remember",   // 存入的名字
                value: this.user.remember,   // 存入的值
                expires: exp,   // 过期时间30天
                startTime: now.getTime()  //存入的时间
              })

              //保存token
              storage.setItem({
                name: "token",   // 存入的名字
                value: res.data.data,   // 存入的值
                expires: exp,   // 过期时间30天
                startTime: now.getTime()  //存入的时间
              })
              this.$message.success("欢迎回来！");
              this.$router.push({ path: "/" });

            } else {
              alert("您输入的用户名或密码错误！");
            }
          });
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login {
  width: 100%;
  height: 740px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: -ms-flexbox;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  background: url("../assets/images/bg-01.jpg") no-repeat center;
  background-size: cover;
  overflow: hidden;
}
.login-wrap {
  width: 400px;
  border-radius: 10px;
  overflow: hidden;
  background: #ffffff;
  background-size: cover;
  height: 320px;
  margin: 215px auto;
  padding-top: 10px;
  line-height: 40px;
}
#password {
  margin-bottom: 5px;
}
h3 {
  color: #0babeab8;
  font-size: 24px;
}
hr {
  background-color: #444;
  margin: 20px auto;
}
a {
  text-decoration: none;
  color: #aaa;
  font-size: 15px;
}
a:hover {
  color: coral;
}
.el-button {
  width: 80%;
  margin-left: -50px;
}
</style>
