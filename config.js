export const baseUrl = "120.24.226.87:8888"


			// initWebSocket: function (userId,sessionId) {
			// 	// WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https                
			// 	this.websock = new WebSocket("ws://120.24.226.87:8888/webSocket/"+userId+"/"+sessionId);             
			// 	this.websock.onopen = this.websocketonopen;   
			// 	this.websock.onerror = this.websocketonerror;                
			// 	this.websock.onmessage = this.websocketonmessage;                
			// 	this.websock.onclose = this.websocketclose;
			// },              
			// websocketonopen: function () {                
			// 	console.log("WebSocket连接成功");    
			// },              
			// websocketonerror: function (e) { 
			// 	console.log("WebSocket连接发生错误",e);              
			// },              
			// websocketonmessage: function (e) {  
			// 	let data = JSON.parse(e.data);
			// 	console.log(e)
			// 	// if(data instanceof Array){
			// 	// 	// 列表数据
			// 	// 	this.sessionList_already = data
			// 	// }else{
			// 	// 	// 消息数据
			// 	// 	this.list.push(data)
			// 	// }
			// },              
			// websocketclose: function (e) {
			// 	if(this.curUserId != null){
			// 		if(this.curSessionId != null){
			// 			this.initWebSocket(this.curUserId, this.curSessionId)
			// 		}else{
			// 			this.initWebSocket(this.curUserId, 9999999)
			// 		}
			// 	}
			// 	console.log("connection closed",e);             
			// },
			