<template>
	<view class="">
		<u-subsection :list="list" :current="index" @change="sectionChange"></u-subsection>
		<u-empty
				:show="IsEmpty"
		        mode="order"
		        icon="http://cdn.uviewui.com/uview/empty/order.png"
		>
		</u-empty>
	</view>
</template>

<script>
	export default {
		data() {

			return {
				index: 0,
				list: ['全部', '已接单', '完成中', '待验收','已完成'],
				itemList:[]
			}
		},
		methods: {
			sectionChange(index) {
				this.index = index;
			}
		},
		computed:{
			IsEmpty(){
				if (this.itemList.length==0){
					return true
				}else{
					return false
				}
				
			}
		},
		onLoad(data) {
			this.index = Number.parseInt(data.index)+1
		}
	}
</script>

<style>
</style>
