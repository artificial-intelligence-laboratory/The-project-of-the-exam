<template>
	<view class="">
		

		<u-subsection :list="list" :current="index"  @change="sectionChange"></u-subsection>
		<u-empty
				:show="IsEmpty"
		        mode="order"
		        icon="http://cdn.uviewui.com/uview/empty/order.png"
		>
		</u-empty>
		
		<view class="" v-if="!IsEmpty">
			<view class="cu-item" v-for="(item,index) in needtoShow">
					<view class="content">
						<view class=" flex  margin-top-sm ">
							<text class="text-black text-bold text-xl ">快递代拿</text>
							<view class="text" style="margin-left: 140px;"><text
									class="text-bold text-xxl text-price text-red  ">{{item.fee}}</text>
							</view>
						</view>
						<view class="text-gray text-content text-df">
							{{item.workPlace}}
						</view>
						<view class=" flex justify-between">
							<view class="text-gray  text-content text-df margin-top-sm">
								{{item.startTime}}至{{item.endTime}}
							</view>
							<view class="cu-tag bg-grey light  round " @click="ToDetail">任务详情
							</view>
						</view>
						<view class="solid-top"></view>
						<view class=" flex justify-between">
							<text class="text-black">广州商学院</text>
							<view class="text-black text-df">2022年10月22日</view>
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
				index:0,
				list: ['全部', '待审核', '待接取', '待完成', '已结束'],
				itemList0:[],	// 待审核的订单
				itemList1:[],	// 待接取的订单
				itemList2:[],	// 待完成的订单
				itemList3:[],	// 已结束的订单
				needtoShow:[],
				
				Listcount:20,	// 一页显示多少个
				currentPage:0   // 当前页
			}
		},
		methods:{
			sectionChange(index) {
				this.index = index;
				
				// 获取 4个状态的订单
				this.getMyOrder()
				
				
				// 给到相对应的tab页面的list
				this.InitList()
			},
			// 获取所有订单
			InitList(){
				if(this.index==0)	// 全部
				{
					let arr = this.itemList0.concat(this.itemList1).concat(this.itemList2).concat(this.itemList3)
					console.log("全部列表",arr)
					this.needtoShow = arr
					
				}else if(this.index == 1)
					this.needtoShow = this.itemList0
				else if(this.index == 2)
					this.needtoShow = this.itemList1
				else if(this.index == 3)
					this.needtoShow = this.itemList2
				else if(this.index == 4)
						this.needtoShow = this.itemList3
			},
			
			// 加载我的接单
			getMyOrder(){
				let that = this
				// 获取状态0的
				uni.request({
					url: that.$baseUrl + '/order/getOrderByMe?isIssue=true&count='+this.Listcount+'&cur='+this.currentPage+'&status=0',
					method:"get",
					header:{
						'Content-Type': 'application/json',
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						console.log(res)
						that.itemList0 = res.data.data
						
					}
				})
				
				// 获取状态1的
				uni.request({
					url: that.$baseUrl + '/order/getOrderByMe?isIssue=true&count='+this.Listcount+'&cur='+this.currentPage+'&status=1',
					method:"get",
					header:{
						'Content-Type': 'application/json',
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						that.itemList1 = res.data.data
					}
				})
				
				// 获取状态2的
				uni.request({
					url: that.$baseUrl + '/order/getOrderByMe?isIssue=true&count='+this.Listcount+'&cur='+this.currentPage+'&status=2',
					method:"get",
					header:{
						'Content-Type': 'application/json',
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						
						that.itemList2 = res.data.data
					}
				})
				
				// 获取状态3的
				uni.request({
					url: that.$baseUrl + '/order/getOrderByMe?isIssue=true&count='+this.Listcount+'&cur='+this.currentPage+'&status=3',
					method:"get",
					header:{
						'Content-Type': 'application/json',
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						
						that.itemList3 = res.data.data
					}
				})
				
				
			}
		},
		onLoad(data) {
			this.index = Number.parseInt(data.index)+1
			this.getMyOrder()
			setTimeout(()=>{this.InitList()},100)	// 延时给到相对应的tab页面的list
		},
		computed:{
			IsEmpty(){
				if (this.needtoShow.length==0){
					return true
				}else{
					return false
				}
				
			}
		}
	}
</script>

<style>
	@import url("@/colorui/main.css");
	@import url("@/colorui/icon.css");
</style>
