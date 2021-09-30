// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import router from './router'
import axios from 'axios'
import 'vue-tree-halower/dist/halower-tree.min.css' // you can customize the style of the tree
import VTree from 'vue-tree-halower'
import $ from 'jquery'
import storage from "./assets/js/storage";
axios.defaults.baseURL = '/api';
Vue.prototype.HOST='/user'

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(VTree)
Vue.use(ElementUI);

$(document).ready(()=>{
  console.log("remember=",storage.getItem("remember"))
  if(!storage.getItem("remember") || false === storage.getItem("remember")) {

    let lastExit = storage.getItem("lastExit")
    console.log("now -lastExit = ", new Date().getTime() - lastExit)

    //两次间隔大于5秒说明不是刷新操作
    if (lastExit && new Date().getTime() - lastExit > 5000) {
      console.warn("clear token ")
      storage.clear()
    }
  }




});
window.onunload = function (e) {
  e = e || window.event;

  // 兼容IE8和Firefox 4之前的版本
  if (e) {
    e.returnValue = '关闭提示';
  }

    //保存退出的时间
    storage.setItem({
      name: "lastExit",   // 存入的名字
      value: new Date().getTime(),   // 存入的值
      startTime: new Date().getTime()  //存入的时间
    })

  // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
  return '关闭提示';
};
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
