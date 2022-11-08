<template>
	<view>
		<view class="status_bar" style="height: var(--status-bar-height); width: 100%;">
		</view>
		<view class="cu-bar bg-white">
			<view class="action">
				<text class=" text-black">发单</text>
			</view>
			<view class="action">
				<text class="text-blue text-bold text-xl" @tap="showModal" data-target="DialogModal1">确认发布</text>
			</view>
		</view>
		<view class="cu-modal" :class="modalName=='DialogModal1'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="action" @tap="hideModal('1')">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					是否确认发布订单
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-blue text-blue" @tap="hideModal('ca')">取消</button>
						<button class="cu-btn bg-blue margin-left" @tap="hideModal('yes')">确定</button>
					</view>
				</view>
			</view>
		</view>
		<form>
			<view class="cu-form-group margin-top">
				<view class="typeId" required>订单类型</view>
				<picker @change="PickerChange" :value="index" v-model="FormData.typeId= index>-1?index+1:1"
					:range="picker">
					<view class="picker">
						{{index>-1?picker[index]:'请选择订单类型'}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title" required>标题</view>
				<view class="BD">
					<input placeholder="请输入任务标题" v-model="FormData.title" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="phone" required>手机号码</view>
				<view class="BD">
					<input placeholder="请输入手机号码" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="workPlace">工作地点</view>
				<view class="BD">
					<input v-model="FormData.workPlace" placeholder="请输入收货地址" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="startTime">开始时间</view>
				<picker  v-model="FormData.startTime=date" mode="date" :value="date" :start="startTime" :end="endTime" @change="DateChange">
					<view class="picker">
						{{date}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="endTime">结束时间</view>
				<picker v-model="FormData.endTime=date1" mode="date" :value="date1" :start="startTime" :end="endTime" @change="DateChange1">
					<view class="picker" >
						{{date1}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">详情</view>
				<textarea v-model="FormData.details" maxlength="-1" :disabled="modalName!=null" @input="textareaBInput"
					placeholder="多行文本输入框"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">注意事项</view>
				<textarea v-model="FormData.beCareful" maxlength="-1" :disabled="modalName!=null" @input="textareaBInput"
					placeholder="多行文本输入框"></textarea>
			</view>
			<view class="cu-form-group">
				<view class="fee">订单价格(元)</view>
				<view class="BD">
					<input v-model="FormData.fee" placeholder="请输入订单价格" name="input"></input>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				FormData: {
					title: '',
					typeId: 1,
					workPlace: '',
					details: '',
					fee: '',
					startTime:'',
					endTime:'',
					beCareful:'',
				},
				modalName: null,
				index: -1,
				picker: ['快递', '帮忙', '租借', '二手', '跑腿'],
				date: '2018-12-25',
				date1: '2018-12-25',
				startTime:'',
				endTime:'2029-01-01',
				modalName: null,
				requied: true,
				multiIndex: [0, 0, 0],
				rules:{
			
			},
			}
		},
		onLoad() {
			var tha =this;
			this.date=new Date().toISOString().slice(0,10)
			this.date1=new Date().toISOString().slice(0,10)
		},
		methods: {
			//判断指定字段是否为空，若存在为空返回true
			checkNull() {
				if (this.FormData.title == '') {
					uni.showToast({
						title: '标题不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.typeId == '') {
					uni.showToast({
						title: '订单类型不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.fee == '') {
					uni.showToast({
						title: '金额不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.workPlace == '') {
					uni.showToast({
						title: '工作地点不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.startTime == '') {
					uni.showToast({
						title: '开始时间不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.beCareful == '') {
					uni.showToast({
						title: '注意事项不能为空',
						icon: 'fail'
					});
					return true;
				}
				if (this.FormData.endTime == '') {
					uni.showToast({
						title: '结束时间不能为空',
						icon: 'fail'
					});
					return true;
				}
				//判断到最后说明都不为空，返回false
				return false;
			},
			createOrder: function(e) {
				// console.log('11111'); 
				let that = this;
				if (that.checkNull()) {
					return
				}
				uni.request({
					url: that.$baseUrl + "/order/createOrder",
					method: 'POST',
					data: {
						title: that.FormData.title,
						details: that.FormData.details,
						beCareful: that.FormData.beCareful,
						workPlace: that.FormData.workPlace,
						fee: that.FormData.fee,
						typeId: that.FormData.typeId,
						startTime:that.FormData.startTime,
						endTime:that.FormData.endTime,
					},
					header: {
						'Authorization': that.$store.state.token
					},
					success(res) {
						console.log(res)
						
						// 验证是否成功
						if (res.data.status != '200') {
							uni.showToast({
								title: res.data.msg,
								icon: 'fail'
							});
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: 'success'
							});	
							uni.navigateTo({
								url:'/pages//order/order',
							})
						}					
					},
					fail: (err) => {
						console.error(err);
						
					},
				});
			},

			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(str) {
				// console.log(str);
				this.modalName = null
				if (str === 'yes') this.createOrder();
				
			},
			PickerChange(e) {
				this.index = e.detail.value
			},
			textareaBInput(e) {
				this.textareaBValue = e.detail.value
			},
			DateChange(e) {
				this.date = e.detail.value
			},
			DateChange1(e) {
				this.date1 = e.detail.value
			},
			
		},
		//生命周期
		created() {
			// this.createOrder()
			// console.log("abc",this.$store)
		},
	}
</script>

<style lang="scss">
	@import url("../../colorui/main.css");
	@import url("../../colorui/icon.css");
	@import url("issue.css");
</style>
