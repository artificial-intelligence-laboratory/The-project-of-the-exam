<template>
	<view class="">
		

		<u-subsection :list="list" :current="index"  @change="sectionChange"></u-subsection>
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
				index:0,
				list: ['全部', '待审核', '待接取', '待完成', '已结束'],
				itemList:[]
			}
		},
		methods:{
			sectionChange(index) {
				this.index = index;
			}
		},
		onLoad(data) {
			this.index = Number.parseInt(data.index)+1
		},
		computed:{
			IsEmpty(){
				if (this.itemList.length==0){
					return true
				}else{
					return false
				}
				
			}
		}
	}
</script>

<style>
</style>
