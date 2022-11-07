<template>
	<view>
		<u-cell-group>

			<u-cell class="whitebox" title="设置密码" isLink  @click="ChangePwd"></u-cell>
		</u-cell-group>
		<u-transition :show="show" mode="slide-right">
		<u-popup :show="show" @close="close" mode="center" round="10" :customStyle="cusStyle">
			<view class="pop-input">
				<u-input type="password" placeholder="请输入密码"  border="surround" v-model="userInsert" :focus="true"></u-input>
			</view>
			
			<view class="pop-input">
				<u-input type="password" placeholder="请确认密码"  border="surround" v-model="Confirmpwd"></u-input>
			</view>
			
			<view class="u-popup-close">
				<u-button type="primary" size="large" text="修改" @click="commitChangePwd"></u-button>
			</view>
			
			
			
			<u-alert v-if="IsConfirm" title="两次密码不一致" type = "warning" showIcon></u-alert>
			   
			
		</u-popup>
		 </u-transition>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				IsConfirm:false,
				show: false,
				userInsert: '', 
				Confirmpwd:'',
				cusStyle:{
					"background":"#eee"
				}
			}
			
		},
		methods:{
			ChangePwd(){
					this.show = true
			
				},
				
				commitChangePwd(){
					let that = this
					if(this.Confirmpwd!=this.userInsert){
						this.IsConfirm=true
						setTimeout(()=>{
							this.IsConfirm=false
						},1500)
					}else{
						uni.request({
							url: that.$baseUrl + '/user/updatePwd',
							method: 'POST',
							data:{
								'password':that.Confirmpwd
							},
							header: {
								'Authorization': uni.getStorageSync('token')
							},
							success(res) {
								console.log(res.data)
								if (res.data.status == '200') {
									// TODO:成功
									that.show = false
									uni.showToast({
										title:res.data.msg,
										icon:'success',
										duration:3000,
										success() {
											
										}
									})
								}else if(res.data.status == '502' || res.data.status == '501'){		//502状态，需要重新登录
									that.show = false
									uni.showToast({
										title:res.data.msg,
										icon:'error',
										duration:3000,
										
									})
									uni.navigateTo({
										url:'/pages/Login/login'
									})
								}else{
									uni.showToast({
										title:res.data.msg,
										icon:'error',
										duration:3000,
									})
								}
								
							}
						
						})
					}
					this.userInsert=''
					this.Confirmpwd=''

				},
			
			close() {
				this.show = false
				// console.log('close');
			},
		},
		onLoad(data) {
			
		}
		
		
		}
	
</script>
	
<style>
	page{
		background: #eeeeee; 
	},
	.whitebox{
		background: white;
	}
	
	.pop-input{
		background: white;
		width: 360rpx;
		height: 100rpx;
		margin: 40rpx;
	}
	.u-popup-close{
		
	}
</style>