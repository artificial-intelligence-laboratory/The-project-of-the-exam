<template>
	<view>
			<view class="content  margin-top">
				<view class="KP">
					<view class="cu-card dynamic" :class="isCard?'no-card':''" style="border-radius: 28px;">
						<view class="cu-item shadow">
							<view class="cu-list menu-avatar">
								<view class="cu-item">
									<view class="cu-avatar round lg" style="width: 50px;">
										<image class="tu" :src="bymedata.orderUser.avatar"></image>
									</view>
									<view class="content flex-sub">
										<view>{{bymedata.orderUser.username}}</view>
									</view>
								</view>
							</view>
							<view class="padding bg-white ">
								<view class="solid line-black  padding" style="border-radius: 28px;">
									<text class="details">{{bymedata.details}}</text>
								</view>
							</view>
							<view class="text-content" style="font-size: 16px;text-align: left;">
								<view>
									<text class="text-gray">订单编号</text>
									<text class="orderId text-gray">234235623523|</text>
									<text class="text-gray">复制</text>
								</view>
								<view>
									<text class="text-gray">创建时间</text>
									<text class="orderId text-gray">{{bymedata.createTime}}</text>
								</view>
								<view  >
									<text class="text-gray">接单时间</text>
									<text class="orderId text-gray">{{bymedata.startTime}}</text>
								</view>
								<view><text class="text-gray">结束时间</text>
									<text class="orderId text-gray">{{bymedata.endTime}}</text>
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
						<button class="cuIcon-markfill cu-btn text-green line-green shadow" @click="goTochat">联系发单人</button>
						<button class="cuIcon-dianhua cu-btn text-green line-green shadow">拨打电话</button>
					</view>
				</view>
			</view>
		
		<view>
			<view class="footer" type="primary" block>
				<view class="cu-bar btn-group padding" >
					<button class="cu-btn bg-green shadow-blur round lg " @tap="showModal" data-target="DialogModal1">取消任务</button>
				</view>
			</view>
			<view class="cu-modal" :class="modalName=='DialogModal1'?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						
					</view>
					<view class="padding-xl">
						是否确定取消任务
					</view>
					<view class="cu-bar bg-white justify-end">
						<view class="action">
							<button class="cu-btn line-green text-green" @tap="hideModal('ca')">取消</button>
							<button class="cu-btn bg-green margin-left" @tap="hideModal('yes')">确定</button>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>


</template>

<script>
	export default {

		data() {
			return {
				isCard: true,
				modalName: null,
				bymedata:[],
				orderId: '',
				status: 0
			}
		},

		methods: {
			getOrderByMe() {
				let that = this
				console.log(that);
				uni.request({
					url: that.$baseUrl + "/order/getOrderDetails?orderId=" + that.orderId + "&isIssue=true",
					method: 'GET',
					header: {
						'Authorization': that.$store.state.token
					},
					success: (res) => {
						console.log(res.data);
						that.bymedata=res.data.data;			
					},
					fail: (err) => {
						console.error(err);
					},
				});
			},
			updateStatus() {
				let that = this
				uni.request({
					url: that.$baseUrl + "/order/updateStatus",
					method: 'PUT',
					header: {
						'Authorization': that.$store.state.token
					},
					data: {
						'orderId': that.orderId,
						'status': that.status
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
			deleteOrder(){
				
			}
			goTochat() {
				uni.navigateTo({
					url: '../Contact/SecondPage/Chat'
				})
			},
			goBackToorder() {
				uni.switchTab({
					url: '../order/details'
				});
				console.log('返回原来的页面');
			},
			IsCard(e) {
				this.isCard = e.detail.value
			},
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(str) {
				this.modalName = null
				if (str === 'yes') this.deleteOrder();
			},
		},
		
		onLoad(option) {
			console.log(option.id);
			this.orderId = option.id
			this.status = 2
			this.updateStatus()
			this.getOrderByMe()
		},
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
	.tu{
		height: 50px;
		border-radius: 50%;
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

	.footer {
		position: fixed;
		bottom: 0;
		width: 100%;
		line-height: var(--footer-height);
		background: #ffffff;
		/* color: #fff; */
		height: 70px;
	}
</style>
