<template>
	<view class="container">
		<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" :bgColor="bgColor" rightIcon="plus">
				<view class="u-nav-slot" slot="left">
					　　　　消息
				</view>
				<view class="u-nav-slot" slot="center">
					<!-- 中间插槽空 -->
				</view>
			</u-navbar>
		</view>

		<!-- 搜索框 -->
		<view class="Sreach">
			<u-search :actionStyle="rigthIcon" shape="round" actionText="" inputAlign="center" placeholder="搜索"
				v-model="keyword" bgColor="#eeeeee" :clearabled="true"></u-search>
		</view>

		<!-- 聊天列表 -->
		<view class="ChatList">
			<uni-list>
				<u-swipe-action>
					<u-swipe-action-item :options="options1" v-for="(item,index) in filterList" @click="delelteChat">
						<view class="swipe-action u-border-top u-border-bottom">
							<uni-list-chat  :title="item.listName"
								:avatar="item.avatar" :showBadge="showBadge(item)" :note="item.lastContent"
								:time="item.lastTime" :badge-text="item.unReadCount"
								:badge-style="{backgroundColor:'#FF80AB'}" clickable @click="ToChat(item,index)">

							</uni-list-chat>

						</view>
					</u-swipe-action-item>
				</u-swipe-action>

			</uni-list>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				options1: [{
					text: '删除',
					style: {
						backgroundColor: '#f56c6c'
					}
				}],

				ChatLists: [{
					id: '0',
					sessionid: '',
					avatar: '',
					listName: '汉堡肉多多1',
					unReadCount: 1,
					time: '2020-02-02 20:20'
				}],
				rigthIcon: {
					display: 'none'
				},
				bgColor: '#eeeeee',
				keyword: '',
				dataList: []
			}
		},
		methods: {
			// 删除聊天
			delelteChat(item){
				let that = this
				console.log('删除第',item.index,'个')
				// TODO:删除后同步服务端
				uni.request({
					url: that.$baseUrl + '/session/delSession?sessionId='+that.ChatLists[item.index].id,
					method: "DELETE",
					header: {
						'Content-Type': 'application/json',
					},
					success(res) {
						that.ChatLists.splice(item.index,1)
						console.log(res.data)
					}
				})
			},
			ToChat(item, index) {
				let that = this
				console.log(item)
				uni.setStorageSync('ChatDetail', this.ChatLists[index])
				uni.navigateTo({
					url: '/pages/Contact/SecondPage/Chat?index=' + that.ChatLists[index].id,
				})
			},
			getChatList() {
				let that = this
				let user_id = uni.getStorageSync('userInfo').id
				uni.request({
					url: that.$baseUrl + '/session/alreadySessionList',
					method: "get",
					header: {
						'Content-Type': 'application/json',
					},
					data: {
						userId: user_id
					},
					success(res) {
						console.log(res.data)
						that.ChatLists = res.data.data
					}
				})
			},
			// 展示未读信息徽标
			showBadge(val) {
				// console.log(val.unReadCount)
				if (val.unReadCount > 0) {
					return true
				} else {
					return false
				}
			}

		},

		computed: {
			// 搜索聊天列表
			filterList() {
				return this.ChatLists.filter((item) => {
					return item.listName.indexOf(this.keyword) != -1
				})


			},

		},

		onShow() {
			this.getChatList()



		}
	}
</script>

<style lang="scss" scoped>
	@import url('ContactList.css');
</style>
