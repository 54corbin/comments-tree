<template>
  <div class="login clearfix">
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="user" status-icon label-width="80px">
          <h3>注册</h3>
          <hr>
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="设置密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <router-link to="/login" >已有帐号，立即登录</router-link>
          <el-form-item>
            <el-button type="primary" icon @click="doRegister()">注册账号</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        email: "",
        password: ""
      },
    };
  },
  created() {
  },
  methods: {
    doRegister() {
      if (!this.user.username) {
        this.$message.error("请输入用户名！");
        return;
      }
      let usernameReg = /^[a-zA-Z0-9]{5,20}/;
      if (!usernameReg.test(this.user.username)) {
        this.$message.error("用户名：只能使用长度在5~20之间的字母和数字！");
        return;
      }
      if (!this.user.email) {
        this.$message.error("请输入邮箱！");
        return;
      }
      let emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!emailReg.test(this.user.email)) {
        this.$message.error("请输入有效的邮箱！");
        return;
      }
      if (!this.user.password) {
        this.$message.error("请输入密码！");
        return;
      }
      let passwordReg = /(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[`~$@!%*#?&])[A-Za-z\d`~$@!%*#?&]{8,20}$/;
      if (!passwordReg.test(this.user.password)) {
        this.$message.error("密码:应符合长度在8~20之间，至少包含一个大写、一个小写、一个数字、一个特殊符号！");
        return;
      }

      // this.$router.push({ path: "/" }); //无需向后台提交数据，方便前台调试
      axios
        .post("/user", {
          username: this.user.username,
          email: this.user.email,
          password: this.user.password
        })
        .then(res => {
          // console.log("输出response.data", res.data);
          // console.log("输出response.data.status", res.data.status);
          if (res.data.success) {
            this.$message.success('注册成功，请登录！')
            this.$router.push({path: "/login"});
          } else {
            alert("您输入的用户名或邮箱地址已存在！");
          }
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login {
  width: 100%;
  height: 740px;
  background: url("../assets/images/bg-01.jpg") no-repeat;
  background-size: cover;
  overflow: hidden;
}

.login-wrap {
  width: 400px;
  border-radius: 10px;
  overflow: hidden;
  background: #ffffff;
  background-size: cover;
  height: 370px;
  margin: 215px auto;
  padding-top: 10px;
  line-height: 40px;
}

h3 {
  color: #0babeab8;
  font-size: 24px;
}

hr {
  background-color: #444;
  margin: 20px auto;
}

.el-button {
  width: 80%;
  margin-left: -50px;
}
</style>
