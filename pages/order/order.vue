<template>
	<view>
		<!-- 搜索 -->
		<Search></Search>
		<!-- 轮播 -->
		<Swipers></Swipers>
		<!-- 卡片 -->
		<Cards></Cards>
	</view>
</template>
<script>
	//搜索
	import Search from './component/search.vue'
	//轮播
	import Swipers from './component/swiper.vue'
	//卡片
	import Cards from './component/card.vue'
	export default {
		//注册父组件
		components: {Search,Swipers,Cards}
	}
</script>

<style lang="scss">
	@import url("../../colorui/main.css");
	@import url("../../colorui/icon.css");
</style>
