<template>
	<view >
		<view class="status_bar" style="height: var(--status-bar-height); width: 100%;">
		</view>
		<view class="cu-bar bg-white">
			<view class="Sreach">
				<u-search :actionStyle="rigthIcon" shape="round" actionText="" inputAlign="center" placeholder="搜索"
					title="" v-model="keyword" bgColor="#eeeeee" :clearabled="true"></u-search>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				keyword: '',
				rigthIcon: {
					display: 'none'
				},
				CustomBar: this.CustomBar,
			}
		},
		methods: {
			searchIcon(e) {
				let key = e.detail.value.toLowerCase();
				let list = this.cuIcon;
				for (let i = 0; i < list.length; i++) {
					let a = key;
					let b = list[i].name.toLowerCase();
					if (b.search(a) != -1) {
						list[i].isShow = true
					} else {
						list[i].isShow = false
					}
				}
				this.cuIcon = list
			}
		}
	}
</script>

<style scoped>
	page {
		/* padding-top: 50px; */	
			display: -webkit-box;
			width: 100%;
			height: 100%;	
	}
	.Sreach {
		width: 90%;
		margin: 0 auto;
	}
</style>
