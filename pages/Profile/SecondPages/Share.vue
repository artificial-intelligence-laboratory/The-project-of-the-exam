<template>
	<view>
		
		<view class="Card">
			
			<view class="avatar">
				<u-avatar :src="avatarURL" size="50"></u-avatar>
				<view style="margin-left: 40rpx;">
					{{username}}
				</view>
			</view>
			
			
			<view class="QRcode">
				<u-image src="https://cdn.uviewui.com/uview/album/1.jpg" :lazy-load="true" mode="widthFix"></u-image>
			</view>
			
			<view class="content">
				扫一扫上面的二维码图案，下载小灵通
			</view>
			
		</view>
		
		
	</view>
</template>

<script>
	export default{
		data(){
			return{
				avatarURL:'',
				username:''
			}
		},
			onLoad() {
				const user = uni.getStorageSync('userInfo')
				this.avatarURL = user.avatar
				this.username = user.username
			}
	}
</script>

<style>
	page{
		background: #eeeeee; 
	}
	.Card{
		background: white;
		width: 85%;
		/* height: 600rpx; */
		margin: 0 auto;
		/* line-height: 600rpx; */
		border-radius: 5%;
		outline: 1px solid;
	}
	.avatar{
			display: flex;
			align-items: center;
			margin: 20rpx;
	}
	.QRcode{
		align-items: center;
	}
</style>