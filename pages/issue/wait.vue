<template>
	<view>
		<view class="box">
			<view class="cu-bar bg-white">
				<view class="action">
					<text class="cuIcon-back text-gray" @click="goBackToissue"></text> 返回
				</view>
				<view class="content text-bold">
					待审核
				</view>
			</view>
		</view>
		</view>
</template>

<script>
	export default {
		
		data() {
			return {
				 
				}		
		},
			
		methods:{
		 goBackToissue() {
				uni.switchTab({
					url: '../issue/issue'
				});
				console.log('返回原来的页面');
			}
			},
		onLoad() {
			
		}
			}
</script>

<style lang="scss">
	@import url("../../colorui/main.css");
	@import url("../../colorui/icon.css");
</style>
