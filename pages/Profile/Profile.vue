<template>
	<view>
		<view class="personal" @click="NavToDetail">
			<view class="personal-main">
				<u-avatar :src="user.avatar" mode="square" size="100" class="u-avatar"></u-avatar>
				<view class="personal-info">
					<view class="">{{user.username}}</view>
					<view class="">{{user.phone}}</view>
				</view>
			</view>
			<!-- <u-icon name="arrow-right" size="30" class="p-right-icon"></u-icon> -->
		</view>

		<!-- 我的发布 -->
		<view id="WhiteBox" class="box2">
			<span class="mysend">{{CardName}}</span>
			<span class="all" @click="goALL">全部 ></span>
			<swiper :indicator-dots="true" class="swiper" @change="changeCardName">
				<swiper-item>
					<u-grid :border="false">
						<u-grid-item :customStyle="{width:150+'rpx',height:150+'rpx',paddingLeft:80+'rpx'}"
							v-for="(item, index) in SendswiperList" :index="index" :key="index" @click="goItem(index)">
							<view :class="item.icon" id="Order_icon"></view>
							<text class="grid-text">{{ item.name }}</text>
						</u-grid-item>
					</u-grid>
				</swiper-item>
				<swiper-item>
					<u-grid :border="false">
						<u-grid-item :customStyle="{width:150+'rpx',height:120+'rpx',paddingLeft:50+'rpx'}"
							v-for="(item, index) in AcceptswiperList" :index="index" :key="index" @click="goItem(index)">
							<view :class="item.icon" id="Order_icon"></view>
							<text class="grid-text">{{ item.name }}</text>
						</u-grid-item>
					</u-grid>
				</swiper-item>

			</swiper>
		</view>


		<!-- 功能菜单 -->
		<view id="WhiteBox" class="box1">
			<view class="SettingItem item-bottom-solid" v-for="(item,index) in list" :key="index"
				@click="onClick(item)">
				<view>
					<view class="p-left">
						<view :class="item.icon"></view>
					</view>
				</view>

				<!-- 右边描述 -->
				<view class="p-right">
					<view class="p-right-main">
						<view class="p-right-main-name">{{item.name}}</view>

					</view>

					<!-- 进入下一级.右箭头 -->
					<view class="left-a">
						<u-icon name="arrow-right" size="22" class="p-right-icon"></u-icon>
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
				CardName: '我的发布', // 发布与接单切换
				user: {
					avatar: '', // 头像
					username: '', // 昵称
					phone: '13450209670', // 手机
					num: '',
					academy: '计算机学院',
					specialities: '软件工程',
					classes: '软工4班'
				},

				// 功能菜单
				list: [{
						name: '软件设置',
						id: 'Setting',
						icon: 'iconfont icon-shezhi',

					}, {
						name: '关于我们',
						id: 'About',
						icon: 'iconfont icon-guanyuwomen',

					}, {
						name: '推荐给好友',
						id: 'Share',
						icon: 'iconfont icon-fenxiang',

					},
					{
						name: '退出登录',
						id: '',
						icon: 'iconfont icon-tuichu',

					},


				],

				// 我的发布
				SendswiperList: [{
					name: '待审核',
					icon: 'Order_icon icon-daishenhe'
				}, {
					name: '待接取',
					icon: 'Order_icon icon-renwu'
				}, {
					name: '待完成',
					icon: 'Order_icon icon-jinxingzhong'
				}, {
					name: '已结束',
					icon: 'Order_icon icon-complete-fill'
				}, ],
				// 我的接单
				AcceptswiperList: [{
					name: '已接单',
					icon: 'Order_icon icon-daishenhe'
				}, {
					name: '进行中',
					icon: 'Order_icon icon-renwu'
				}, {
					name: '待验收',
					icon: 'Order_icon icon-jinxingzhong'
				}, {
					name: '已结束',
					icon: 'Order_icon icon-complete-fill'
				}, ],
			}
		},
		methods: {
			// 切换卡片名字
			changeCardName(e) {
				e.detail.current ? this.CardName = "我的接单" : this.CardName = "我的发布"
			},
			// 跳转到全部
			goALL() {
				let Pagename = "MySend"
				if (this.CardName == "我的接单") {
					Pagename = "MyOrder"
				} else {
					Pagename = "MySend"
				}
				let url = '/pages/Profile/SecondPages/' + Pagename + '?index'+0 // id判断进入哪个页面
				uni.navigateTo({
					url: url,
					animationType: 'slide-in-right',
					animationDuration: 100,
					success() {
					
					}
				})
			},
			// 跳转到我的发布/接单 任意模块
			goItem(index) {
				console.log('个人')
				let that = this
				let Pagename = "MySend"
				if (this.CardName == "我的接单") {
					Pagename = "MyOrder"
				} else {
					Pagename = "MySend"
					
				}
				let url = '/pages/Profile/SecondPages/' + Pagename + '?index=' + index // id判断进入哪个页面
				uni.navigateTo({
					url: url,
					animationType: 'slide-in-right',
					animationDuration: 100,
					success() {

					}
				})
			},
			// 点击跳转功能
			onClick(item) {
				let url = '/pages/Profile/SecondPages/' + item.id 
				if (item.id != '') {
					uni.navigateTo({
						url: url,
						animationType: 'slide-in-right',
						animationDuration: 100,
						success() {
							// console.log('成功打开页面',item.name)
						}
					})
				} else {
					// TODO: 退出软件  判断:安卓【ios]
					// #ifdef APP-PLUS
					if (plus.os.name.toLowerCase() === 'android') {
						plus.runtime.quit();
					} else {
						const threadClass = plus.ios.importClass("NSThread");
						const mainThread = plus.ios.invoke(threadClass, "mainThread");
						plus.ios.invoke(mainThread, "exit");
						// plus.ios.import('UIApplication').sharedApplication().performSelector('exit');
					}
					// #endif
				}

			},
			
			
			// 点击头像跳转个人信息
			NavToDetail() {
				let url = '/pages/Profile/SecondPages/UserDetail'
				uni.navigateTo({
					url: url,
					animationType: 'slide-in-right',
					animationDuration: 100,
				})
			}
		},

		onShow() {
			// 判断用户是否登录
			if(uni.getStorageSync('token')==null){
				uni.navigateTo({
					url:'/pages/Login/login'
				})
			}else{
				this.user = uni.getStorageSync('userInfo')	
			}
			
			
			
		}
	}
</script>

<style lang="scss">
	@import url('Profile.css');
</style>
