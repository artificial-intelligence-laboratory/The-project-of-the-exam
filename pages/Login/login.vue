<template>
	<view class="content">
		<!-- 头部登录文字 -->
		<view class="header">
			<span style="font-weight: bolder;font-size: 50rpx;">登录</span>
			<view style="color: #A9A9A9;margin-top: 20rpx;">若该邮箱未注册，我们将自动为您注册</view>
		</view>

		<!-- 切换登录类型-->
		<view class="login-type">
			<view v-for="(item,index) in loginTypeList" :key="index" @click="loginType = index"
				:class="{act: loginType === index}" class="login-type-btn">{{item}}</view>
		</view>

		<!-- 邮箱验证码登录  -->
		<view class="input-group" v-if="loginType === 0">
			<view class="input-row border">
				<!-- <text class="title">邮箱：</text> -->
				<!-- <input class="input" type="text" clearable focus v-model="email" placeholder="请输入邮箱"></input> -->
				<u--input placeholder="请输入邮箱" border="surround" focus="" v-model="email"></u--input>
				
			</view>


			<view class="u-demo-block__content" style="margin-top: 15px;">
				<!-- 注意：由于兼容性差异，如果需要使用前后插槽，nvue下需使用u--input，非nvue下需使用u-input -->
				<!-- #ifndef APP-NVUE -->
				<u-input placeholder="请输入验证码" v-model="code">
					<!-- #endif -->
					<!-- #ifdef APP-NVUE -->
					<u--input placeholder="请输入验证码" v-model="code">
						<!-- #endif -->
						<template slot="suffix">
							<u-code uniqueKey="Page-a" :keepRunning="true" :seconds="seconds" ref="uCode"
								@change="codeChange"></u-code>
							<u-button  @tap="getCode" :text="tips" type="success" size="mini">
							</u-button>
						</template>
						<!-- #ifndef APP-NVUE -->
				</u-input>
				<!-- #endif -->
				<!-- #ifdef APP-NVUE -->
				</u--input>
				<!-- #endif -->
			</view>
		</view>




		<!-- 邮箱密码登录 -->
		<view class="input-group" v-else>
			<view class="input-row border">
				<!-- <text class="title">邮箱：</text> -->
				<u--input placeholder="请输入邮箱" border="surround" focus v-model="email"></u--input>
			</view>


			<view class="input-row border">
				<u--input placeholder="请输入密码" border="surround" v-model="password"></u--input>
			</view>

		</view>
		<view class="btn-row">
			<button type="primary" class="primary" :loading="loginBtnLoading" @click="bindLogin">登录</button>
		</view>
<u-notify ref="uNotify" message="Hi uView"></u-notify>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tips: '',
				// refCode: null,
				seconds: 30,
				loginType: 0,
				loginTypeList: ['验证码登录', '密码登录'], // 0 1
				email: '',
				code: '',
				password: '',
				positionTop: 0,
				loginBtnLoading: false,
			}
		},
		computed: {
			CheckEmail(e) {
				
				let email_reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
				if (email_reg.test(e.email)) {
					this.email = e.email
					return true

				} else {
					return false
				}
				return false
			}
		},

		methods: {
			codeChange(text) {		// uView内部传递参数 text:重新获取验证码||点击获取验证码
				this.tips = text;
			},
			// 获取验证码
			getCode() {
				let that = this
				if (!this.CheckEmail) {
					this.$refs.uNotify.show({
						top: 100,
						type: 'warning ',
						message: '邮箱格式有误',
						duration: 2000,
						fontSize: 20,
						safeAreaInsetTop: true
					})
				}else{
					// 检查无误后开始请求验证码
					if (this.$refs.uCode.canGetCode) { // 注意：用户可能在倒计时的过程中点击获取验证码的按钮，组件内部提供了通过ref获取的canGetCode变量
						// 请求验证码
						uni.request({
							url: that.$baseUrl+"/passport/sendCode",
							method:'POST',
							data: {
								'email':that.email
							},
							header: {
								'Content-Type':'application/json'
							},
							success(res) {
								console.log(res)
								
							}
						})
					
						uni.showLoading({
							title: '正在获取验证码'
						})
						setTimeout(() => {
							uni.hideLoading();
							// 这里此提示会被this.start()方法中的提示覆盖
							uni.$u.toast('验证码已发送');
							// 通知验证码组件内部开始倒计时
							this.$refs.uCode.start();
						}, 1500);
					} else {
						uni.$u.toast('请等待倒计时结束后再点击');
					}
				}



			},
			bindLogin() {
				switch (this.loginType) {
					case 0:
						this.loginByEmail()
						break;
					case 1:
						this.loginByPwd()
						break;
					default:
						break;
				}
			},
			// 
			loginByEmail() {
				let that = this
				console.log(this.$baseUrl + "/passport/codeLogin")
				uni.request({
					url: that.$baseUrl + "/passport/codeLogin",
					method:'POST',
					data: {
						email: that.email	,
						code:that.code
						// email: "826697618@qq.com",
						// code:"655810"
					},
					header:{
						'Content-Type':'application/json'
					},
					success(res) {
						console.log(res)
						// 登录验证是否成功
						if (res.data.status != '200') {
							uni.showToast({
								title: res.data.msg,
								icon: 'fail'
							});
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: 'success'
							});
							
							// 保存用户token
							if(res.data.data.token!=null){
								uni.setStorageSync('token', res.data.data.token); 
							}
							
							// 保存用户信息
							uni.setStorageSync('userInfo', res.data.data.user); 

							
							uni.navigateTo({
								url:'/pages/Profile/SecondPages/UserDetail',
							})
						}


					},
					fail(err) {
						console.log(err)
					}
				})
			},
			loginByPwd() {
				let that = this
				let url =that.$baseUrl + "/passport/pwdLogin"

				uni.request({
					url: url,
					method:'POST',
					data: {
						// email: that.email,
						email: "826697618@qq.com",
						password:that.password
					},
					header:{
						'Content-Type':'application/json'
					},
					success(res) {
						console.log(res)
						
						// 登录验证是否成功
						if (res.data.status != '200') {
							uni.showToast({
								title: res.data.msg,
								icon: 'fail'
							});
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: 'success'
							});
							
							// 保存用户token
							if(res.data.data.token!=null){
								uni.setStorageSync('token', res.data.data.token); 
							}
							
							// 保存用户信息
							uni.setStorageSync('userInfo', res.data.data.user); 
				
							
							uni.navigateTo({
								url:'/pages/Profile/SecondPages/UserDetail',
							})
						}
				
				
					},
					fail(err) {
						console.log(err)
					}
				})
			}
		},
		onLoad() {
				
		}
	}
</script>

<style>
	@import url('login.css');
</style>
