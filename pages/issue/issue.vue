<template>
	<view>
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
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					是否确认发布订单
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-blue text-blue" @tap="hideModal">取消</button>
						<button class="cu-btn bg-blue margin-left" @tap="hideModal">确定</button>
					</view>
				</view>
			</view>
		</view>
		<form>
			<view class="cu-form-group margin-top">
				<view class="typeld" :rules="rules"  >订单类型</view>
				<picker @change="PickerChange" :value="index" :range="picker">
					<view class="picker">
						{{index>-1?picker[index]:'请选择订单类型'}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">任务标题</view>
				<view class="BD">
					<input placeholder="请输入任务标题" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group" :rules="rules">
				<view class="phone">手机号码</view>
				<view class="BD">
					<input placeholder="请输入手机号码" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="workPlace">收货地址</view>
				<view class="BD">
					<input placeholder="请输入收货地址" name="input"></input>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="startTime">开始时间</view>
				<picker mode="date" :value="date" start="2015-09-01" end="2020-09-01" @change="DateChange">
					<view class="picker">
						{{date}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="endTime">结束时间</view>
				<picker mode="date" :value="date1" start="2015-09-01" end="2020-09-01" @change="DateChange1">
					<view class="picker">
						{{date1}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">任务描述</view>
				<textarea maxlength="-1" :disabled="modalName!=null" @input="textareaBInput"
					placeholder="多行文本输入框"></textarea>
			</view>
			<view class="cu-form-group">
				<view class="fee">订单价格(元)</view>
				<view class="BD">
					<input placeholder="请输入订单价格" name="input"></input>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				modalName: null,
				index: -1,
				picker: ['快递', '帮忙', '租借', '二手', '跑腿'],
				date: '2018-12-25',
				date1: '2018-12-25',
				modalName: null,
					requied:true,
				rules: {
			
				},
				multiIndex: [0, 0, 0],
			};
		},
		methods: {
			createOrder(){
				let that = this
			uni.request({
			  url:  that.$baseUrl + "/order/createOrder", 
			  method:'POST',
			  success: (res) => {
			    console.log(res.data);
			  },
			  fail: (err) => {
			    console.error(err);
			  },
			});
			},
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
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
			this.createOrder()
		},
	}
</script>

<style lang="scss">
	@import url("../../colorui/main.css");
	@import url("../../colorui/icon.css");
	@import url("issue.css");
</style>
