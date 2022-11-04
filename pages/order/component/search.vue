<template>
	<view>
	<!-- 	<view>
			<u-search placeholder="请输入任务名称" v-model="keyword" :clearabled="true" :show-action="false" height="40"
				margin="40px 25px 0px 25px" bgColor="#D8D8D8"></u-search>
		</view> -->
		<view class="cu-bar  search fixed" :style="[{top:CustomBar + 'px'}]" >
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input type="text" placeholder="请输入任务名称" confirm-type="search" @input="searchIcon"></input>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// keyword: '',
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
		padding-top: 50px;
	}

</style>
