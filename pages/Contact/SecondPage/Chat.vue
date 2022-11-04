<template>
	<view class="container">
	<u-sticky offset-top="0">
<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" :bgColor="bgColor" :title="CurrentChatList.username" autoBack></u-navbar>
		</view>
			</u-sticky>
		


		<!-- 聊天内容 -->
		<view class="chatContent">
			<!-- 兼职卡片 -->
		

			<view class="WorkCard">
				<view class="det_name">
					<span >发单人:</span>
					<u--text mode="name" :text="CurrentChatList.username"></u--text>
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

			        <!-- 聊天内容 -->
			        <scroll-view class="chat" scroll-y="true" scroll-with-animation="true" :scroll-into-view="scrollToView">
			            <view class="chat-main" :style="{paddingBottom:inputh+'px'}">
			                <view class="chat-ls" v-for="(item,index) in imgMsg" :key="index" :id="'msg'+ index">
								
								<!-- 左边聊天内容 -->
			                    <view class="chat-time" v-if="item.createTime != ''">{{changeTime(item.createTime)}}</view>
			                    <view class="msg-m msg-left" v-if="item.sendName !=  CurrentChatList.username">
			                        <image class="user-img" src="../../../static/logo.png"></image>
			                        <view class="message" v-if="item.TextType == 0">
			                            <!-- 文字 -->
			                            <view class="msg-text">{{item.sendText}}</view>
			                        </view>
			                        </view>
			                    
								<!-- 右边聊天内容  -->
			                    <view class="msg-m msg-right" v-if="item.sendName == CurrentChatList.username">
			                        <image class="user-img" src="../../../static/logo.png"></image>
			                        <view class="message" v-if="item.TextType == 0">
			                            <view class="msg-text">{{item.sendText}}</view>
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
				scrollheight: 200,
				lines: 2,
				bgColor: '#eee',
				CurrentChatList: {
					id: '0',
					sessionid: '',
					Avatarurl: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/460d46d0-4fcc-11eb-8ff1-d5dcf8779628.png',
					username: '张学友',
					time: '2020-02-02 20:20'
				},

				CurrentMsgList: [], //当前聊天信息列表
				imgMsg: [
					{
					                        "sendName": "张学友",
					                        "receviceName": "",
					                        "createTime": "2022-01-06 12:40:12",
					                        "updateTime": null,
					                        "chatmState": 1,
											"sendText":"汉堡肉多多汉堡肉多多汉堡肉多多汉堡肉多多",
					                        "TextType": 0		// 只有文本
					                    },
										
										{
										                        "sendName": "汉堡肉多多1",
										                        "receviceName": "",
										                        "createTime": "2022-01-06 12:40:12",
										                        "updateTime": null,
										                        "chatmState": 1,
																"sendText":"汉堡肉多多汉堡肉多多汉堡肉多多汉堡肉多多",
										                        "TextType": 0
										                    },
				],
				scrollToView: '',
				index: 0,
				inputh: '60'
			}
		},


		onShow() {
			this.CurrentChatList = uni.getStorageSync('ChatDetail')
		},

		methods: {
			changeTime(date) {
				return dateTime.dateTime1(date);
			},
			//接受输入内容
			inputs(e) {
				//时间间隔处理
				let data = {
					"sendName": "",
					"receviceName": "",
					"sendText": e.message,
					"createTime": new Date(),
					"updateTime": new Date(),
					"chatmState": 1,
					"TextType": e.type
				};
				// 发送给服务器消息
				// s's'f(JSON.stringify(data));

				this.CurrentMsgList.push(data);
				// 跳转到最后一条数据 与前面的:id进行对照
				this.$nextTick(function() {
					this.scrollToView = 'msg' + (this.CurrentMsgList.length - 1)
				})
				if (e.type == 1) {
					this.imgMsg.push(e.message);
				}
				console.log(e)
			},
			//输入框高度
			heights(e) {
				console.log("高度:", e)
				this.inputh = e;
				this.goBottom();
			},
			// 滚动到底部
			goBottom() {
				this.scrollToView = '';
				this.$nextTick(function() {
					this.scrollToView = 'msg' + (this.CurrentMsgList.length - 1)
				})
			}
		},
		components: {
			submit,
		},

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
		height: 300rpx;
		border-radius: 20rpx;
		margin: 0 auto;
		padding: 20rpx;
	}
	
	.chat {
	           height: 100%;
	           .chat-main {
	               padding-left: 32rpx;
	               padding-right: 32rpx;
	               padding-top: 20rpx;
	               padding-bottom: 120rpx;  //获取动态高度
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
