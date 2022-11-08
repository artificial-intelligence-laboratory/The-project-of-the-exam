<template>
	<view>
		<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" bgColor="#eee" @leftClick="gobackTodetails">
				<view class="u-nav-slot" slot="center">
					个人信息
				</view>
			</u-navbar>
		</view>
		<!-- 加载动画 -->
		<u-loading-icon text="个人信息加载中..." size="30" :show="IsLoading"></u-loading-icon>
		<u-cell-group>
			<u-cell class="avatar-item" title="头像" :border="false" :isLink="true">
				<view slot="value">
					<u-avatar :src="detailsdata.orderUser.avatar" size="50"></u-avatar>
				</view>
			</u-cell>
			<u-cell name="username" title="昵称"  :value="detailsdata.orderUser.username" :isLink="true">
			
			</u-cell>
			<u-cell name="num" title="学号" :value="detailsdata.orderUser.num" :isLink="true"></u-cell>
			<u-cell name="academy" title="学院" :value="detailsdata.orderUser.academy" :isLink="true">
			</u-cell>
			<u-cell name="specialities" title="专业" :value="detailsdata.orderUser.specialities" :isLink="true">
			</u-cell>
			<u-cell name="classes" title="班级" :value="detailsdata.orderUser.classes" :isLink="true">
			</u-cell>
			<u-cell name="phone" title="手机号" :value="detailsdata.orderUser.phone" :isLink="true">
			</u-cell>
		</u-cell-group>
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
				IsLoading: false,
				show: false,
				chooseAvaters: false, // 操作菜单的显示隐藏
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
						that.detailsdata = res.data.data
					},
					fail: (err) => {
						console.error(err);
					},
				});
			},
			gobackTodetails(){
				uni.navigateTo({
					url: '../order/details'
				})
			}
		},
		onLoad: function(option) {
			console.log(option.id);
			this.getOrderDetails(option.id)
		},


	}
</script>

<style>
	.avatar-item {
		border-bottom: 1rpx solid #eeeeee;
	}

	.pop-input {
		width: 400rpx;
		height: 120rpx;
	}
</style>
