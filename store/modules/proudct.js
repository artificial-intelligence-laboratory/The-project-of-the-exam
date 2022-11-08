import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const state = new Vuex.Store({
	state:{
		token: uni.getStorageSync('token')||'',
		user: uni.getStorageSync('userInfo')||{}
	},
	mutations:{
		//保存数据
		setToken(state,token){
			state.token=token
		},
		setUser(state,user){
			state.user=user
		}
		
	}
})
export default state;