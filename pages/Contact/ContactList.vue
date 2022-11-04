<template>
	<view class="container">
		<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true"  :bgColor="bgColor" rightIcon="plus">
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
				
				<uni-list-chat v-for="(item,index) in ChatLists" :title="item.username" :avatar="item.Avatarurl" note="您收到一条新的消息" :time="item.time" badge-text="12" :badge-style="{backgroundColor:'#FF80AB'}" clickable @click="ToChat(item,index)">
				
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
					id:'0',
					sessionid:'',
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多1',
					time:'2020-02-02 20:20'
				}, {
					id:'1',
					sessionid:'',
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多2',
					time:'2020-02-02 20:20'
				}, {
					id:'2',
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username:'汉堡肉多多3',
					time:'2020-02-02 20:20'
				}],
				rigthIcon: {
					display: 'none'
				},
				bgColor: '#eeeeee',
				keyword: '',
				dataList: []
			}
		},
		methods:{
			ToChat(item,index){
				let that = this
				console.log(item)
				uni.setStorageSync('ChatDetail',this.ChatLists[index])
				uni.navigateTo({
					url:'/pages/Contact/SecondPage/Chat?index='+that.ChatLists[index].id,
				})
			}
		},
		onLoad() {
			
		}
	}
</script>

<style lang="scss" scoped>
	@import url('ContactList.css');
</style>
