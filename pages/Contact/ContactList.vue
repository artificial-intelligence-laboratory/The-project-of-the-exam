<template>
	<view class="container">
		<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" :height="navHeight" :bgColor="bgColor" rightIcon="plus">
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

			<u-search :actionStyle="rigthIcon" shape="round" actionText="" inputAlign="center" placeholder="搜索" title=""
				v-model="keyword" bgColor="#eeeeee" :clearabled="true"></u-search>
		</view>

		<!-- 聊天列表 -->
		<view class="ChatList">
			<uni-list>
				
				<uni-list-chat v-for="(item,index) in ChatLists" :title="item.username" :avatar="item.Avatarurl" note="您收到一条新的消息" :time="item.time" badge-text="12" :badge-style="{backgroundColor:'#FF80AB'}" clickable @click="ToChat(item)">
				
				</uni-list-chat>
			</uni-list>
		</view>






	</view>
</template>

<script>
	export default {
		data() {
			return {
				ChatLists: [{
					id:'1',
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多',
					time:'2020-02-02 20:20'
				}, {
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多',
					time:'2020-02-02 20:20'
				}, {
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多',
					time:'2020-02-02 20:20'
				}],
				rigthIcon: {
					display: 'none'
				},
				navHeight: '60rpx',
				bgColor: '#eeeeee',
				keyword: '',
				dataList: []
			}
		},
		methods:{
			ToChat(e){
				console.log(e)
			}
		},
		onLoad() {
			
		}
	}
</script>

<style lang="scss" scoped>
	@import url('ContactList.css');
</style>
