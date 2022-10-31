<template>
	<view>
		<!-- 自定义导航 -->
		<view class="nav">
			<u-navbar :placeholder="true" bgColor="#eee" @leftClick="backInfo">
				<view class="u-nav-slot" slot="center">
					个人信息
				</view>
			</u-navbar>
		</view>

		<u-cell-group>

			<u-cell class="avatar-item" title="头像" :border="false" :isLink="true" @click="chooseAvaters=true">

				<view slot="value">
					<u-action-sheet :actions="lists" :show="chooseAvaters" :closeOnClickAction="true"
						@close="closeSheet" @select="editAvater" cancelText="取消" :closeOnClickOverlay="true">
					</u-action-sheet>
					<u-avatar :src="user.avatar" size="50"></u-avatar>
				</view>

			</u-cell>
			<u-cell name="username" title="昵称" :value="user.username" :isLink="true" @click="AlterUserInfo">
			</u-cell>
			<u-cell name="num" title="学号" :value="user.num" :isLink="true" @click="AlterUserInfo"></u-cell>
			<u-cell name="academy" title="学院" :value="user.academy" :isLink="true" @click="AlterUserInfo">
			</u-cell>
			<u-cell name="specialities" title="专业" :value="user.specialities" :isLink="true" @click="AlterUserInfo">
			</u-cell>
			<u-cell name="classes" title="班级" :value="user.classes" :isLink="true" @click="AlterUserInfo">
			</u-cell>
			<u-cell name="phone" title="手机号" :value="user.phone" :isLink="true" @click="AlterUserInfo">
			</u-cell>

			<view style="width: 70%;margin: 80rpx auto;">
				<u-button shape="circle" type="error" @click="commitInfo">保存修改</u-button>
			</view>
		</u-cell-group>





		<u-popup :show="show" @close="close" mode="center" round="10">
			<view class="pop-input">
				<u-input placeholder="请输入内容" border="surround" v-model="userInsert" :focus="true"></u-input>
			</view>
			<view class="u-popup-close">
				<u-button type="primary" text="修改" @click="commitText"></u-button>
			</view>


		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				AlterName: '', // 用户现在正修改的字段
				show: false,
				userInsert: '', // 用户在弹出窗口输入的需要修改的文本
				PreviewImageList: [],
				user: {
					avatar: '', // 头像
					username: '不爱吃湘菜', // 昵称
					phone: '13450209670', // 手机
					num: '学号123',
					academy: '计算机学院',
					specialities: '软件工程',
					classes: '软工5班',
					status: ''
				},
				chooseAvaters: false, // 操作菜单的显示隐藏
				lists: [ // 操作菜单的列表内容
					{
						name: '更换头像',
						index: 0
					},
					{
						name: '查看大图',
						index: 1
					}
				],
			}
		},

		methods: {
			// 返回时判断用户是否填完信息
			backInfo() {
				this.getUserInfo()
				if (this.user.status == '1') {
					uni.switchTab({
						url: '/pages/Profile/Profile'
					})

				} else {
					uni.showToast({
						icon: 'error',
						title: '您的信息输入不完整'
					})

				}
			},

			// 提交更改到后端
			commitInfo() {
				let that = this
				uni.request({
					url: '/user/updateUserInfo',
					method: 'POST',
					data: {
						classes: that.user.classes,
						academy: that.user.academy,
						avatar: that.user.avatar,
						specialities: that.user.specialities,
						username: that.user.username,
						phone: that.user.phone,
						num: that.user.num
					},
					header: {
						'Content-Type': 'application/json',
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						console.log("更改发送", res)

					},
					fail(err) {
						console.log('发送失败', err)
					}

				})

				this.getUserInfo()
			},


			// 输入内容
			commitText() {
				console.log(this.userInsert)
				let that = this
				this.show = false;
				if (this.userInsert == '' || this.userInsert == null || this.userInsert == undefined) {
					uni.showToast({
						icon: 'error',
						title: '请正确填写用户信息',
						duration: 5000
					});
				} else {
					switch (this.AlterName) {
						case 'username':
							that.user.username = this.userInsert
							break;
						case 'num':
							that.user.num = this.userInsert
							break;
						case 'phone':
							var phoneReg = /^1[3-9][0-9]{9}$/
							if (phoneReg.test(this.userInsert)) {
								that.user.phone = this.userInsert
								break;
							} else {
								uni.showToast({
									icon: 'error',
							 	title: '输入错误的手机号',
									duration: 5000
								});
								break;
							}
							case 'academy':
								that.user.academy = this.userInsert
								break;
							case 'classes':
								that.user.classes = this.userInsert
								break;
							case 'specialities':
								that.user.specialities = this.userInsert
								break;
							default:
								break;
					}
					this.userInsert = ''
				}


			},

			// 更改用户信息时
			AlterUserInfo(e) {
				this.AlterName = e.name
				this.show = true



			},
			close() {
				this.show = false
				// console.log('close');
			},
			closeSheet() {
				this.chooseAvaters = false
			},
			chooseAvater() {
				this.chooseAvaters = true
			},
			editAvater(item) {
				if (item.index == 0) {
					this.UserchooseAvatar()
				} else {
					this.previewImage()
				}
			},
			// 用户更换头像
			UserchooseAvatar() {
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //album 从相册选图，camera 使用相机
					success: function(res) {
						that.PreviewImageList = res.tempFilePaths
						console.log(that.PreviewImageList[0]); //若是数组就需要转成json字符串，其实现在一个头像不用
						// 上传服务器
						const uploadTask = uni.uploadFile({
							url: '/file/uploadFace',
							filePath: that.PreviewImageList[0], // 做成数组为了以后能拓展
							header: {
								'Authorization': uni.getStorageSync('token'),
							},
							name: 'file',
							success(uploadFileRes) {
								let url = JSON.parse(uploadFileRes.data) // 获取网络url
								console.log(url)
								that.user.avatar = url.data
								uni.setStorageSync('userInfo', that.user) //改完头像把user存储
							}
						});
					}
				});

			},

			// 查看大图
			previewImage() {
				this.PreviewImageList = [this.user.avatar]
				uni.previewImage({
					current: 0,
					urls: this.PreviewImageList, //拿头像地址
				})
			},

			// 封装查看用户数据库方法    从服务器 => Local_Storage
			getUserInfo() {
				let that = this
				uni.request({
					url: '/user/getUserInfo',
					method: 'GET',
					header: {
						'Authorization': uni.getStorageSync('token')
					},
					success(res) {
						console.log("获取用户信息", res.data.data)
						if (res.data != null) {
							uni.setStorageSync('userInfo', res.data.data)
							that.user = uni.getStorageSync('userInfo')
						}

					}

				})
			},
		},

		onShow() {
			this.getUserInfo()
		},


	}
</script>

<style>
	.avatar-item {
		border-bottom: 1rpx solid #eeeeee;
	}

	.pop-input {
		width: 400rpx;
		height: 120rpx;
	}
</style>
