<template>
	<view >
			
				<u-cell-group>
					<u-cell class="whitebox" title="账户与安全" isLink :url="this.profileSetting"></u-cell>
					<u-cell class="whitebox" title="消息与提醒" isLink url="/pages/Profile/ThirdPage/MessageSetting"></u-cell>
				</u-cell-group>
			

	</view>	
</template>

<script>
	export default{
		data(){
			
			return{
				profileSetting:""
			}
		},
		
		// 加载时接收用户信息
		onLoad() {
			const user = uni.getStorageSync('userInfo')
			this.profileSetting =  "/pages/Profile/ThirdPage/ProfileSetting?phone="+user.phone
			console.log(this.profileSetting)
		},
	}
</script>

<style>
	page{
		background: #eeeeee; 
	},
	.whitebox{
		background: white;
	}
	
</style>