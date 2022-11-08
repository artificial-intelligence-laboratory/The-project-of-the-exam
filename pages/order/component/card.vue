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
						<view v-for="(item,index) in carddata" :key="item.id">
						<view class="KP">
							<view class="cu-item">
								<view class="content">
									<view class=" flex  margin-top-sm  ">
										<text class="text-black text-bold text-xl ">{{item.title}}</text>
										<view class="text" style="margin-left: 140px;"><text
												class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
										</view>
									</view>
									<view class="text-black text-content text-df">
										{{item.workPlace}}
									</view>
									<view class=" flex justify-between">
										<view class="text-black  text-content text-df margin-top-sm">
											{{item.startTime}}至{{item.endTime}}
										</view>
									<view>
										<button class="cu-btn round lg bg-red" @click="goTodetails(item.id)">领取任务</button>
									</view>
									</view>
								</view>
							</view>
						</view>
					</view>	
					<u-loadmore :status="status" v-show="status != 'nomore'" :load-text="loadText"/>  
				</view>
				
				</view>
				<view>
					
					<view v-if="tabContentId == 1">
						<view v-for="(item,index) in carddata" :key="item.id">
							<view class="KP">
								<view class="cu-item">
									<view class="content">
										<view class=" flex  margin-top-sm  ">
											<text class="text-black text-bold text-xl ">{{item.title}}</text>
											<view class="text" style="margin-left: 140px;"><text
													class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
											</view>
										</view>
										<view class="text-black text-content text-df">
											{{item.workPlace}}
										</view>
										<view class=" flex justify-between">
											<view class="text-black  text-content text-df margin-top-sm">
												{{item.startTime}}至{{item.endTime}}
											</view>
										<view>
											<button class="cu-btn round lg bg-red" @click="goTodetails(item.id)">领取任务</button>
										</view>
										</view>
									</view>
								</view>
							</view>
						</view>	
						<u-loadmore :status="status" v-show="status != 'nomore'" :load-text="loadText"/>
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 2">
					<view v-for="(item,index) in carddata" :key="item.id">
						<view class="KP">
							<view class="cu-item">
								<view class="content">
									<view class=" flex  margin-top-sm  ">
										<text class="text-black text-bold text-xl ">{{item.title}}</text>
										<view class="text" style="margin-left: 140px;"><text
												class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
										</view>
									</view>
									<view class="text-black text-content text-df">
										{{item.workPlace}}
									</view>
									<view class=" flex justify-between">
										<view class="text-black  text-content text-df margin-top-sm">
											{{item.startTime}}至{{item.endTime}}
										</view>
									<view>
										<button class="cu-btn round lg bg-red" @click="goTodetails(item.id)">领取任务</button>
									</view>
									</view>
								</view>
							</view>
						</view>
					</view>	
					<u-loadmore :status="status" v-show="status != 'nomore'" :load-text="loadText"/>
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 3">
						<view v-for="(item,index) in carddata" :key="item.id">
							<view class="KP">
								<view class="cu-item">
									<view class="content">
										<view class=" flex  margin-top-sm  ">
											<text class="text-black text-bold text-xl ">{{item.title}}</text>
											<view class="text" style="margin-left: 140px;"><text
													class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
											</view>
										</view>
										<view class="text-black text-content text-df">
											{{item.workPlace}}
										</view>
										<view class=" flex justify-between">
											<view class="text-black  text-content text-df margin-top-sm">
												{{item.startTime}}至{{item.endTime}}
											</view>
										<view>
											<button class="cu-btn round lg bg-red" @click="goTodetails(item.id)">领取任务</button>
										</view>
										</view>
									</view>
								</view>
							</view>
						</view>	
						<u-loadmore :status="status" v-show="status != 'nomore'" :load-text="loadText"/>
					</view>
				</view>
				<view>
					<view v-if="tabContentId == 4">
					<view v-for="(item,index) in carddata" :key="item.id">
						<view class="KP">
							<view class="cu-item">
								<view class="content">
									<view class=" flex  margin-top-sm  ">
										<text class="text-black text-bold text-xl ">{{item.title}}</text>
										<view class="text" style="margin-left: 140px;"><text
												class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
										</view>
									</view>
									<view class="text-black text-content text-df">
										{{item.workPlace}}
									</view>
									<view class=" flex justify-between">
										<view class="text-black  text-content text-df margin-top-sm">
											{{item.startTime}}至{{item.endTime}}
										</view>
									<view>
										<button class="cu-btn round lg bg-red" @click="goTodetails(item.id)">领取任务</button>
									</view>
									</view>
								</view>
							</view>
						</view>
					</view>	
					<u-loadmore :status="status" v-show="status != 'nomore'" :load-text="loadText"/>
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
				carddata:[],
				status: 'more',
				loadText: {  
					loadmore: '',
					loading: '',
					nomore: ''
				},
				cur:1,
				count:10,
				id:'',
				type:0,
				
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
		methods: {
			getOrderList() {
				let that = this
				console.log(that);
				uni.request({
					url: that.$baseUrl + "/order/getOrderList?cur="+that.cur+"&count="+that.count+"&type="+that.type,
					method: 'GET',
					header: {
						'Authorization': that.$store.state.token
					},
					success: (res) => {
						console.log(res.data);
						that.carddata=res.data.data
					},
					fail: (err) => {
						console.error(err);
					},
				});
			},
			getMessage(){
				uni.showLoading({
					title: "加载中……"
				});
				const mess = {
					name:this.keyword,
					cur:this.cur,
					count:this.count
				}
				getLegalWorker(mess).then(response => {
					uni.hideLoading();
					if(response.data.code == 200){
						if(response.data.data.length < this.count){  
							this.status = "nomore";
						}
						this.cellData.push(...response.data.data) 
					} 
				})
			},
			//上拉刷新事件  //重要代码
			onReachBottom() {
				console.log('onReachBottom');
				if(this.status == 'nomore'){
					return
				}
				this.status = 'loading'
				this.count++;
				this.getMessage();
				this.status = 'more'
			},
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
				console.log("type"+this.tabContentId);
				this.type = this.tabContentId
				this.getOrderList()
			},
			IsCard(e) {
				this.isCard = e.detail.value
			},
			
			goTodetails(id) {
				console.log('ok');
				console.log(id);
				uni.navigateTo({
					url: '../order/details?id='  + id
				})
			},
		},
		//生命周期
		created() {
			// this.banner()
			this.getOrderList()
			// console.log("abc",this.$store)
		},

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
	.cu-btn.lg {
		
		margin-left: 10px;
		margin-bottom: 20px;
		
	}
</style>
