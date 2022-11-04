<template>
	<view>
		<view class="box">
			<view class="cu-bar bg-white">
				<view class="action">
					<text class="cuIcon-back text-gray " @click="goBackToorder">任务详情</text>
				</view>
			</view>

			<view class="content  margin-top">
				<view class="KP">
					<view class="cu-card dynamic" :class="isCard?'no-card':''" style="border-radius: 28px;">
						<view class="cu-item shadow">
							<view class="cu-list menu-avatar">
								<view class="cu-item">
									<view class="cu-avatar round lg"
										style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg);">
									</view>
									<view class="content flex-sub">
										<view>凯尔</view>
									</view>
								</view>
							</view>
							<view class="padding bg-white ">
								<view class="solid line-black  padding" style="border-radius: 28px;">
									<text class="details">缩略信息</text>
								</view>
							</view>
							<view class="text-content" style="font-size: 16px;">
								<view>
									<text class="text-gray">订单编号</text>
									<text class="orderId text-gray">234235623523|</text>
									<text class="text-gray">复制</text>
								</view>
								<view>
									<text class="text-gray">创建时间</text>
									<text class="orderId text-gray">2022-10-23 23:23:05</text>
								</view>
								<view>
									<text class="text-gray">接单时间</text>
									<text class="orderId text-gray">2022-10-23 23:23:05</text>
								</view>
								<view><text class="text-gray">结束时间</text>
									<text class="orderId text-gray">2022-10-23 23:23:05</text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="contact">
				<view class=" padding bg-white " style="border-radius: 18px;">
					<text class="text-bold text-lg text-black ">联系</text>
					<view class="cu-bar btn-group">
						<button class="cuIcon-markfill cu-btn text-green line-green shadow">联系发单人</button>
						<button class="cuIcon-dianhua cu-btn text-green line-green shadow">拨打电话</button>
					</view>
				</view>
			</view>
			<view>
				<view class="cu-bar btn-group padding">
					<button class="cu-btn bg-green shadow-blur round lg">保存</button>
				</view>
			</view>
		</view>
		<view class="box">
			<view class="cu-bar tabbar margin-bottom bg-white">
			</view>
		</view>
	</view>


</template>

<script>
	export default {

		data() {
			return {
				isCard: true
			}
		},

		methods: {
			goBackToorder() {
				uni.switchTab({
					url: '../order/order'
				});
				console.log('返回原来的页面');
			},
			IsCard(e) {
				this.isCard = e.detail.value
			},
		},
		onLoad() {

		}
	}
</script>

<style scoped>
	.KP {
		text-align: center;
		font-size: 32rpx;
		line-height: 50rpx;
		margin: 15px 20px 0px 20px;
		white-space: pre-wrap;

	}

	.details {
		white-space: pre-wrap;

	}

	.orderId {
		padding-left: 80px;
	}

	.contact {
		text-align: left;
		font-size: 32rpx;
		line-height: 50rpx;
		margin: 15px 20px 0px 20px;
		white-space: pre-wrap;
	}
</style>
