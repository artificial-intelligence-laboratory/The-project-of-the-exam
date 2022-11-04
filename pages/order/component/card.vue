<template>
	<view>
		<view>
			<scroll-view scroll-x class=" nav text-center">
				<view class="cu-item" v-for="(item,index) in tabList" :key="index" @tap="tabSelect" :data-id="index">
					<view :class="item.icon" id="Order_icon"></view>
					<text class="grid-text">{{ item.name }}</text>
				</view>
				<view>
					<view v-if="tabContentId == 0">
						<view class="KP">
							<view class="cu-item">
								<view class="content">
									<view class=" flex  margin-top-sm  ">
										<text class="text-black text-bold text-xl ">快递代拿</text>
										<view class="text" style="margin-left: 140px;"><text
												class="text-bold text-xxl text-price text-red  ">5.00/件</text>
										</view>
									</view>
									<view class="text-gray text-content text-df">
										广州市▪黄埔区
									</view>
									<view class=" flex justify-between">
										<view class="text-gray  text-content text-df margin-top-sm">
											2022-05-21至2023-05-21
										</view>
										<view class="cu-tag bg-grey light  round " @click="goTotask">领取任务
										</view>
									</view>
									<view class="solid-top"></view>
									<view class=" flex justify-between">
										<text class="text-black">广州大学</text>
										<view class="text-black text-df">2022年10月22日</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 1">
						222
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 2">
						333
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 3">
						444
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 4">
						555
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 5">
						111
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				keyword: '',
				TabCur: 0,
				scrollLeft: 0,
				tabList: [{
					name: '快递',
					icon: 'Order_icon icon-daishenhe'
				}, {
					name: '帮忙',
					icon: 'Order_icon icon-renwu'
				}, {
					name: '租借',
					icon: 'Order_icon icon-jinxingzhong'
				}, {
					name: '二手',
					icon: 'Order_icon icon-complete-fill'
				}, {
					name: '跑腿',
					icon: 'Order_icon icon-renwu'
				}, ],
				tabContentId: 0,
				isCard: false
			}
		},
		onShow() {
			uni.request({
				url: 'https://www.gcc-fns.com',
				success: (res) => {
					console.log(res.data);
				}
			});
		},
		methods: {
			change(list) {
				// console.log(list1)
			},
			click(list) {
				// console.log(list1)
			},
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				//改变内容
				console.log(e)
				this.tabContentId = e.currentTarget.dataset.id
			},
			IsCard(e) {
				this.isCard = e.detail.value
			},
			goTotask() {
				uni.navigateTo({
					url: '../order/task'
				})
			},
		}

	}
</script>

<style scoped>
	.grid-text {
		font-size: 20px;
		line-height: 5px;
	}

	.KP {
		background-color: #D8D8D8;
		text-align: left;
		font-size: 29rpx;
		line-height: 100rpx;
		margin: 15px 20px 0px 20px;
		white-space: pre-wrap;
		border-radius: 10px;
	}

	.cu-tag {
		height: 40px;
		width: 100px;
	}

	.bg-grey.light {
		bottom: 15px;
	}
</style>
