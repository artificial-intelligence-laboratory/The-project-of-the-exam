<template>
	<view>
		<view >
			<u-avatar class="logo" :src="logoURL" size="50"></u-avatar>
		</view>
		
		
		<u-cell-group :customStyle='celltop'>
			<u-cell  title="软件名" :value="software.name" ></u-cell>
			<u-cell title="版本号" :value="software.version"></u-cell>
			<u-cell title="开发团队" :value="software.team" ></u-cell>
			<u-cell title="版本更新" :isLink="true" @click="UpdateVersion"></u-cell>
		
		</u-cell-group>
		
		<view class="About_Footer">
			<span> &laquo;用户协议&raquo;</span>
			<span>|</span> 
			<span> &laquo;隐私协议&raquo;</span>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				software:{
					name:'小灵通',
					version:'0.01',
					team:'菜鸡煮'
				},
				logoURL: '../../../static/logo.png',
				celltop:{
					marginTop: '50rpx'
				}
			}
		},
		methods:{
			UpdateVersion(){
				uni.showToast({
					title:'更新完成',
					icon:'success',
					duration:3000
				})
			}
		}
	}
</script>

<style>
	page{
		background: #eeeeee;
	}
	.logo{
		margin: 0 auto ;
	}
	.About_Footer{
		text-align: center;
		width: 100%;
		position: absolute;
		bottom: 60rpx;
		color: blue;
		font-size: 30rpx;
	}
	.About_Footer :nth-child(2){
		margin: 0 40rpx;
	}
	
</style>
