<template>
	<view>
		<u-cell-group>

			<u-cell class="whitebox" title="设置密码" isLink></u-cell>
		</u-cell-group>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				phone:''
			}
			
		},
		onLoad(data) {
			console.log(data)
			// const phone = JSON.parse(data) 	// 接收的字符串转换对象
			this.phone = data.phone
		}
		
		
		}
	
</script>
	
<style>
	page{
		background: #eeeeee; 
	},
	.whitebox{
		background: white;
	}
</style>