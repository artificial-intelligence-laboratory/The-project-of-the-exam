<template>
	<view class="container">
	<!-- <u-sticky offset-top="0"></u-sticky> -->
<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" :bgColor="bgColor" :title="CurrentChatList.listName" @leftClick="backtoChatList"></u-navbar>
		</view>
			
		
		<!-- 聊天内容 -->
		<view class="chatContent">

			<!-- 加载图标 -->
					<u-loading-icon :show="IsLoading" mode="circle" text="内容加载中..."></u-loading-icon>
			        <!-- 聊天内容 -->
			        <scroll-view :style="'height:'+scrollHeight+'px'" class="chat" scroll-y="true" scroll-with-animation="true" :scroll-into-view="scrollToView">
						
						<!-- 兼职卡片 -->
						<view class="WorkCard">
							<view class="det_name">
								<span >发单人:</span>
								<u--text mode="name" :text="CurrentChatList.listName"></u--text>
							</view>
							
							<view class="det_name">
							<span >完成金额:</span>
							<u--text mode="price" text="999"></u--text>
							</view>
						<view class="det_name">
							<span >需求简述:</span>
							<view class="u-line-2">
								关于uView的取名来由，首字母u来自于uni-app首字母关于uView的取名来由，首字母u来自于uni-app首字母关于uView的取名来由，
								首字母u来自于uni-app首字母关于uView的取名来由，首字母u来自于uni-app首字母</u--text>
							</view>
							</view>
						
						</view>
						
			            <view class="chat-main" :style="{paddingBottom:inputh+'px'}">
			                <view class="chat-ls" v-for="(item,index) in CurrentMsgList" :key="index" :id="'msg'+index">
								
								<!-- 左边聊天内容 -->
			                    <view class="chat-time" v-if="item.createTime != ''">{{changeTime(item.createTime)}}</view>
			                    <view class="msg-m msg-left" v-if="item.fromUserId !=  CurrentChatList.userId">
			                        <image class="user-img" :src="receiverAvatar"></image>
			                        <view class="message">
			                            <!-- 文字 -->
			                            <view class="msg-text">{{item.content}}</view>
			                        </view>
			                        </view>
			                    
								<!-- 右边聊天内容  -->
			                    <view class="msg-m msg-right" v-if="item.fromUserId == CurrentChatList.userId" >
			                        <image class="user-img" :src="senderAvatar"></image>
			                        <view class="message">
			                            <view class="msg-text">{{item.content}}</view>
			                        </view>
			                       
			                    </view>
			                </view>
							</view>
							
			        </scroll-view>



			
		</view>

		<submit @inputs="inputs" @heights="heights"></submit>

	</view>
</template>

<script>
	import dateTime from "../datetime.js"
	import submit from '../submit/submit.vue'; //底部输入框
	export default {

		data() {
			return {
				senderAvatar:'',
				receiverAvatar:'',
				socket:"",
				socketOpen:false, //是否连接
				scrollHeight:0,
				bgColor: '#eee',
				CurrentChatList: {},
				CurrentMsgList: [//当前聊天信息列表
					//{
											// "id":"122",
					      //                   "fromUserId": "12",
					      //                   "toUserId": "34",
					      //                   "createTime": "2022-01-06 12:40:12",
											// "unReadState":"1",
											// "content":"汉堡肉多多汉堡肉多多汉堡肉多多汉堡肉多多",
					                        //"TextType": 0		// 类型控制(以后拓展)
					                    //},
										
										
				],
				scrollToView: '',
				inputh: '60'			// 高度默认60
			}
		},

		methods: {
			changeTime(date) {
					return dateTime.dateTime1(date);
				},
				
				//接受输入内容
				inputs(e) {
					//时间间隔处理
					let data = {
						"fromUserId": uni.getStorageSync('ChatDetail').userId,
						"toUserId": uni.getStorageSync('ChatDetail').toUserId,
						"content": e.message,
						"createTime": new Date(),
						// "TextType": e.type
						// "unReadState": 1
					};
					// 发送给服务器消息
					// s'f(JSON.stringify(data));
					this.sendSocketMessage(e.message)
					// this.heart()
			
					this.CurrentMsgList.push(data);
					// 跳转到最后一条数据 与前面的id进行对照
					this.goBottom();
					
					console.log(e)
					
				},
				//输入框高度
				heights(e) {
					// console.log("高度:", e)
					this.inputh = e;
					this.goBottom();
				},
				// 滚动到底部
				goBottom() {
					this.$nextTick(function() {				// 完成渲染后再更新		另一种方法是延时异步
								this.scrollToView = 'msg' + (this.CurrentMsgList.length-1)
					})
				},
				
				// 返回聊天列表
					backtoChatList(){
						console.log('回退聊天列表')
						uni.switchTab({
							url:'/pages/Contact/ContactList'
						})
					},
					
					// 加载聊天消息列表
					LoadMessageList(){
						let that = this
						uni.request({
							url: that.$baseUrl + '/message/msgList?sessionId='+uni.getStorageSync('ChatDetail').id,
							method:"PUT",
							header:{
								'Content-Type': 'application/json',
							},
							success(res) {
								
								that.CurrentMsgList = res.data.data
								// console.log(that.CurrentMsgList)
							}
						})
					},
					
					
					//创建websocket连接
					sockcet(id,session_id){
									var that = this;
									uni.closeSocket();
									this.socketOpen = false;
									try{
										//WebSocket的地址
										var url = 'ws://120.24.226.87:8888/webSocket/'+id+'/'+session_id;
										// 连接
										uni.connectSocket({
											url: url,
										});
										// 监听WebSocket连接已打开
										uni.onSocketOpen(function (res) {
										  console.log(res,'WebSocket连接已打开！');
										  that.socketOpen = true;
										});
										// 监听连接失败
										uni.onSocketError(function (err) {
										    console.log('WebSocket连接打开失败，请检查！',err);
											 // e.code === 1000  表示正常关闭。 无论为何目的而创建, 该链接都已成功完成任务。
											 // e.code !== 1000  表示非正常关闭。
											// if(err && err.code!== 1000){
											// 	setTimeout(function() {
											// 	  that.socketOpen = true;
											// 	  uni.connectSocket({
											// 			url: url,
											// 	  });
											// 	}, 5 * 1000)
											// }
										   
										   
										});
										// 监听连接关闭
										uni.onSocketClose(function (err) {
											console.log('WebSocket连接关闭！',err);
											// if(err && err.code!== 1000){
											// 	setTimeout(function() {
											// 	  that.socketOpen = true;
											// 	  uni.connectSocket({
											// 			url: url,
											// 	  });
											// 	}, 5 * 1000)
											// }
										});
										// 监听收到信息
										uni.onSocketMessage(function (res) {
											// console.log('WebSocket监听收到信息！',res.data.content);
											uni.hideLoading()
											//与后端规定好返回值分别代表什么，写业务逻辑
											// JSON.parse()
											let serverData = JSON.parse(res.data); //这是字符串，如果要对象记得转换一下
											console.log(serverData)
											that.CurrentMsgList.push(serverData)			// 从服务器获取的添加到新消息
										
										})	
									}catch(e){
										console.log(e)
									}
								},
				
					
					//向后端发送消息
					
					sendSocketMessage(data){
						console.log('socket发送信息',data)
						let that =this
						if(this.socketOpen==false){
							return
						}
						let msg = JSON.stringify(data);
						try{
							//通过 WebSocket 连接发送数据
							uni.sendSocketMessage({
								data: msg
							});
						}catch(e){
							console.log(e,'断线啦')
							uni.closeSocket();
						}
				},
								
				
				
			},
			

		onReady() {
			let systemInfo = uni.getSystemInfoSync();
			this.scrollHeight = systemInfo.windowHeight *0.86; // -卡片
		},
			
		onLoad() {
			const userInfo = uni.getStorageSync('userInfo')
			this.senderAvatar = userInfo.avatar
			this.CurrentChatList = uni.getStorageSync('ChatDetail')
			this.receiverAvatar = this.CurrentChatList.avatar
			// this.initWebSocket(this.CurrentChatList.userId,this.CurrentChatList.id)
			this.sockcet(this.CurrentChatList.userId,this.CurrentChatList.id)
			this.LoadMessageList()
			this.goBottom() 
		
		},
		onShow() {
				this.goBottom()       
		},
		onUnload(){
			console.log('退出页面')
			if(this.socketOpen==true){
				uni.closeSocket();
			}
		},	


		computed:{
			IsLoading(){
				if(this.CurrentMsgList.length==0){

					return true
				}
				else{

					return false
				}
			}
		},
		components: {
			submit,
		}

	}
</script>

<style lang="scss" scoped>
	@import url('Chat.scss');
	page{
		background: #eee;
	}
	.WorkCard{
		background: white;
		width: 85%;
		height: 325rpx;
		border-radius: 20rpx;
		margin: 0 auto;
		padding: 20rpx;
	}
	
	.chat {
	           .chat-main {
	               padding-left: 32rpx;
	               padding-right: 32rpx;
	               padding-top: 20rpx;
	               padding-bottom: 120rpx;  
	               display: flex;
	               flex-direction: column;
	           }
	    
	           .chat-ls {
	               .chat-time {
	                   font-size: 24rpx;
	                   color: rgba(39, 40, 50, 0.3);
	                   line-height: 34rpx;
	                   padding: 10rpx 0rpx;
	                   text-align: center;
	               }
	    
	               .msg-m {
	                   display: flex;
	                   padding: 20rpx 0;
	    
	                   .user-img {
	                       flex: none;
	                       width: 80rpx;
	                       height: 80rpx;
	                       border-radius: 20rpx;
	                   }
	    
	                   .message {
	                       flex: none;
	                       max-width: 480rpx;
	                   }
	    
	                   .msg-text {
	                       font-size: 32rpx;
	                       color: rgba(39, 40, 50, 1);
	                       line-height: 44rpx;
	                       padding: 18rpx 24rpx;
	                   }
	    
	                   .msg-img {
	                       max-width: 400rpx;
	                       border-radius: 20rpx;
	                   }
	    
	                   .msg-map {
	                       background: #fff;
	                       width: 464rpx;
	                       height: 284rpx;
	                       overflow: hidden;
	    
	                       .map-name {
	                           font-size: 32rpx;
	                           color: rgba(39, 40, 50, 1);
	                           line-height: 44rpx;
	                           padding: 18rpx 24rpx 0 24rpx;
	                           //下面四行是单行文字的样式
	                           display: -webkit-box;
	                           -webkit-box-orient: vertical;
	                           -webkit-line-clamp: 1;
	                           overflow: hidden;
	                       }
	    
	                       .map-address {
	                           font-size: 24rpx;
	                           color: rgba(39, 40, 50, 0.4);
	                           padding: 0 24rpx;
	                           //下面四行是单行文字的样式
	                           display: -webkit-box;
	                           -webkit-box-orient: vertical;
	                           -webkit-line-clamp: 1;
	                           overflow: hidden;
	                       }
	    
	                       .map {
	                           padding-top: 8rpx;
	                           width: 464rpx;
	                           height: 190rpx;
	                       }
	                   }
	    
	                   .voice {
	                       // width: 200rpx;
	                       min-width: 100rpx;
	                       max-width: 400rpx;
	                   }
	    
	                   .voice-img {
	                       width: 28rpx;
	                       height: 36rpx;
	                   }
	               }
	    
	               .msg-left {
	                   flex-direction: row;
	    
	                   .msg-text {
	                       margin-left: 16rpx;
	                       background-color: #fff;
	                       border-radius: 0rpx 20rpx 20rpx 20rpx;
	                   }
	    
	                   .ms-img {
	                       margin-left: 16rpx;
	                   }
	    
	                   .msh-map {
	                       margin-left: 16rpx;
	                       border-radius: 0rpx 20rpx 20rpx 20rpx;
	                   }
	    
	                   .voice {
	                       text-align: right;
	    
	                   }
	    
	                   .voice-img {
	                       float: left;
	                       transform: rotate(180deg);
	                       width: 28rpx;
	                       height: 36rpx;
	                       padding-bottom: 4rpx;
	                   }
	               }
	    
	               .msg-right {
	                   flex-direction: row-reverse;
	    
	                   .msg-text {
	                       margin-right: 16rpx;
	                       background-color: rgba(255, 228, 49, 0.8);
	                       border-radius: 20rpx 0rpx 20rpx 20rpx;
	                   }
	    
	                   .ms-img {
	                       margin-right: 16rpx;
	                   }
	    
	                   .msh-map {
	                       margin-left: 16rpx;
	                       border-radius: 20rpx 0rpx 20rpx 20rpx;
	                   }
	    
	                   .voice {
	                       text-align: left;
	    
	                   }
	    
	                   .voice-img {
	                       float: right;
	                       padding: 4rpx;
	                       width: 28rpx;
	                       height: 36rpx;
	                   }
	               }
	           }
	       }
</style>
