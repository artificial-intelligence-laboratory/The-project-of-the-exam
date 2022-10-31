<template>
	<view>
		列表页
		<ul>
			<li v-for="(item,index) in resultList">{{item.cate_name}}</li>
		</ul>
		
		
		<button @click="setImage">设置照片</button>
		<image v-for="(item,index) in ImgArr" :src="item"></image>
	</view>
	
	
</template>

<script>
	export default{
		data(){
			return{ 
				resultList:[],
				ImgArr:[]
			}
		},
		methods:{
			setImage(){
				uni.chooseImage({
					count:3,
					success: (tempFilePaths) => {
						this.ImgArr = tempFilePaths.tempFilePaths
						console.log(this.ImgArr)
					}
				})
			},
			loadList(){
				let that = this
				uni.request({
					url:'https://www.fastmock.site/mock/61a2743573f209251bb4c2b2dd52ecf6/Bilibili/NavTitle',
					success(res){
						that.resultList = res.data.data.cateItems
				
					}
				})
						setTimeout(function () {
							uni.stopPullDownRefresh();
							// console.log('请求成功',that.resultList)
						}, 1000);
			}
		},
	
		onLoad() {
			this.loadList()

		},
		
		onReachBottom() {
			// this.resultList = [...this.resultList,{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'},{'cate_name':'123'}]
		},
		
		onPullDownRefresh() {
			console.log('下拉刷新')
			setTimeout(function () {
				uni.stopPullDownRefresh();
				// console.log('请求成功',that.resultList)
			}, 1000);
		}
	}
</script>

<style>
	li{
		margin-bottom: 150rpx;
	}
</style>
