import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import uView from '@/uni_modules/uview-ui'

Vue.use(uView)
//uni.$u.config.unit = 'rpx'   //Ipad等大屏幕不能使用rpx
Vue.config.productionTip = false
App.mpType = 'app'


 
if(process.env.NODE_ENV === 'development'){
//开发环境
// #ifdef H5
        // 如需跨域参照以下h5跨域配置
    Vue.prototype.$baseUrl = "/api"
// #endif
// #ifdef APP-PLUS ||MP
    Vue.prototype.$baseUrl = "http://120.24.226.87:8888/"
// #endif
}else{
    Vue.prototype.$baseUrl = "http://120.24.226.87:8888/"
} 



const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif







