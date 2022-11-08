<template>
	<view>
		<view class="cu-list menu-avatar">
			<view class="cu-item" >
				<view class="cu-avatar round lg">
				<image class="tu" :src="detailsdata.orderUser.avatar"  @click="goTopersonal"></image>
				</view>
				<view class="content flex-sub">
					<view>{{detailsdata.orderUser.username}}</view>
				</view>
			</view>
		</view>
		<view class="contact">
			<view class=" padding bg-white " style="border-radius: 10px;">
				<view class=" flex  margin-top-sm  ">
					<text class="text-black text-bold text-xl ">{{detailsdata.title}}</text>
					<view class="text" style="margin-left: 140px;">
						<text class="text-bold text-xxl text-price text-red  ">{{detailsdata.fee}}</text>
					</view>
				</view>
				<view class="solid-top margin-top-sm"></view>
				<view class=" flex justify-between">
					<text class="text-gray margin-top-sm">{{detailsdata.workPlace}}</text>
					<view class="text-gray text-df margin-top-sm">{{detailsdata.createTime}}发布</view>
				</view>
			</view>
		</view>
		<view class="padding flex flex-wrap justify-between align-center ">
			<button class="cu-btn round lg bg-red" @click="goTotask">接单</button>
			<button class="cu-btn round lg bg-red" @click="goTochat">沟通</button>
		</view>
		<view class="contact">
			<view class=" padding bg-white " style="border-radius: 10px;">
				<view class=" flex  margin-top-sm  ">
					<text class="text-black text-bold text-xl ">兼职描述:</text>
				</view>
				<view class="solid-top margin-top-sm"></view>
				<view class=" flex justify-between">
					<text class="text-gray margin-top-sm">工作时间：</text>
				</view>
				<view><text>{{detailsdata.startTime}}至{{detailsdata.endTime}}</text></view>
				<view class=" flex justify-between">
					<text class="text-gray margin-top-sm">岗位职责：</text>
				</view>
				<view><text>{{detailsdata.type}}</text></view>
				
				<view class=" flex justify-between">
					<text class="text-gray margin-top-sm">任职要求：</text>
				</view>
				<view><text>{{detailsdata.details}}</text></view>
			</view>
		</view>
		<view class="contact">
			<view class=" padding bg-white " style="border-radius: 10px;">
				<view class=" flex  margin-top-sm  ">
					<text class="text-black text-bold text-xl ">注意事项：</text>
				</view>
				<view><text>{{detailsdata.beCareful}}</text></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {

		data() {
			return {
				detailsdata: {
					orderUser:{}
				},
				orderId: 0,
			}
		},

		methods: {
			getOrderDetails(id) {
				let that = this
				that.orderId = id
				uni.request({
					url: that.$baseUrl + "/order/getOrderDetails?orderId=" + that.orderId + "&isIssue=true",
					method: 'GET',
					header: {
						'Authorization': that.$store.state.token
					},
					success: (res) => {
						console.log(res.data);
						that.detailsdata=res.data.data
					},
					fail: (err) => {
						console.error(err);
					},
				});
			},
			goTotask() {
				console.log('ok');
				uni.navigateTo({
					url: '../order/task?id='+this.orderId
				})
			},
			goTochat() {
				uni.navigateTo({
					url: '../Contact/SecondPage/Chat'
				})
			},
			goTopersonal(){
				uni.navigateTo({
					url: '../order/personal?id='+this.orderId
				})
			}
		},
		//生命周期
		created() {
			// this.banner()
			// this.getOrderDetails()
			// console.log('mmmmmm');
			// console.log("abc",this.$store)
		},
		onLoad: function(option) {
			console.log(option.id);
			this.getOrderDetails(option.id)
		},
	}
</script>

<style scoped>
	.contact {
		text-align: left;
		font-size: 32rpx;
		line-height: 50rpx;
		margin: 15px 20px 0px 20px;
		white-space: pre-wrap;
	}

	.cu-btn.lg {
		padding: 0 50px;
		margin-left: 10px;
		margin-right: 10px;
	}
	.tu{
		height: 50px;
		border-radius: 50%;
	}
</style>
