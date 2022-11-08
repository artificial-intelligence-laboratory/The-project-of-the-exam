<template>
	<view class="swiper-top">
		<view>
			<swiper class="swiper" :indicator-dots='true' indicator-color="rgba(0, 0, 0, .3)"
				indicator-active-color="#99FFFF" :autoplay="true" interval="1500">
				<block v-for="(item,index) in bannerdata" :key="index">
					<swiper-item>
						<view class="swiper-item">
							<image :src="item.image" mode="aspectFill"></image>
						</view>
					</swiper-item>
				</block>
			</swiper>
		</view>
	</view>
</template>
<script>
import loginVue from '../../Login/login.vue';
	export default {
		data() {
			return {
				bannerdata: []
			}
		},
		methods: {
			banner() {
				uni.request({
					url: 'https://meituan.thexxdd.cn/api/getbanner',
					method: 'GET',
					success: (res) => {
						console.log(res);
						this.bannerdata = res.data.data
					},
					fail: (err) => {
						console.log(err)
					}
				});
			}
		},
		//生命周期
		created() {
			this.banner()
			console.log("abc",this.$store)
		},
		// onShow() {
		// 	console.log("abd",this)
		// }
	}
</script>

<style scoped>
	.swiper-top {
		height: 250upx;
		margin: 20upx 35upx;
		border-radius: 18upx;
		position: relative;
	}

	.swiper {
		height: 250upx !important;
		border-radius: 18upx;
		overflow: hidden;
	}

	.swiper-item image {
		width: 100%;
		height: 250upx !important;
		border-radius: 18upx;
	}

	.active {
		background-color: #ffffff !important;
	}
</style>
