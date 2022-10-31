<template>
	<view>
		{{DadData.name}}
		<button type="default" @click="SendtoDad">向父组件发送</button>
	</view>
</template>

<script>
	export default {
		name:"child",
		data() {
			return {
				DadData:this.$props.DadSend,
				Mydata:{
					name:'我给爸爸的数据',
					age:25
				}
			};
		},
		props:{
			DadSend:{
				type:Object,
				default:{
					'name':'猪八戒'
				}
			}
		},
		methods:{
			SendtoDad(){
				this.$emit("SendtoDad",this.$data.Mydata)
			}
		}
	}
</script>

<style>

</style>
