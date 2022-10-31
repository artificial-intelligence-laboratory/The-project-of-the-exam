<template>
	<view class="title">
	<!-- 	<view class="box1" hover-class="active-box2" >
			<view class="box2" hover-class="active-box" hover-stop-propagation>1111</view>
			<child :DadSend="data1" @SendtoDad="ReceiveFromDad"></child>
		</view>
		
		<button size="mini" plain type="primary" @click="clickbtn1">我是按钮1</button>
		
		<ul>
			<li>可选人员名单</li>
			<li v-for="(item,index) in star" >{{item.sex}} -- {{item.name}} -- {{item.age}}</li>
		</ul>
		
		<input style="border: 1px solid black;" type="text" placeholder="请输入关键字" v-model="keyword"/>
		
		
		<view>以下是符合筛选:</view>
		<ul>
			<li v-for="(item,index) in filterStar" >{{item.sex}} -- {{item.name}} -- {{item.age}}</li>
		</ul>
	
		 -->
	</view>
		
		

</template>

<script>
	import child from '../../components/child.vue'
	export default {
		data() {
			return {
				data1:{
					'name':'孙悟空'
				},
				star:[
					
					        {id:'001', name:'马冬梅', age:18, sex:'女'},
					        {id:'002', name:'周冬雨', age:19, sex:'女'},
					        {id:'003', name:'周杰伦', age:20, sex:'男'},
					        {id:'004', name:'温兆伦', age:20, sex:'男'},
					      
				],
				keyword:'',
				filterStar:[]

			}
		},
		onLoad() {
			
		},

		methods: {
			ReceiveFromDad(data1){
				console.log(data1)
			},
			clickbtn1(){
				console.log('点击了按钮')
			}
		},
		computed:{
			// filterStar(){
			// 	return this.star.filter((one)=>{
			// 		return one.name.indexOf(this.keyword)
			// 	})
			// }
		},
		watch:{
			
			keyword:{
				immediate:true,
				handler(newval,oldval){
					this.filterStar = this.star.filter((item)=>{
						return item.name.indexOf(newval)!=-1
					})
				}
			}
		},
		
		components:{
			child
		}
	}
</script>

<style lang="scss">
	@import url("./a.css");
	// .title{
	// 	background: $uni-text-color;
	// }
</style>
