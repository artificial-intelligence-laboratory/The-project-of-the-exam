
var isReady=false;var onReadyCallbacks=[];
var isServiceReady=false;var onServiceReadyCallbacks=[];
var __uniConfig = {"pages":["pages/order/order","pages/Profile/Profile","pages/Profile/SecondPages/MySend","pages/Profile/SecondPages/MyOrder","pages/Profile/SecondPages/About","pages/Profile/SecondPages/Setting","pages/Profile/SecondPages/Share","pages/Profile/ThirdPage/ProfileSetting","pages/Profile/ThirdPage/MessageSetting","pages/Profile/SecondPages/UserDetail","pages/issue/issue","pages/issue/wait","pages/Contact/ContactList","pages/Contact/SecondPage/Chat","pages/Contact/SecondPage/Chat","pages/Login/login"],"window":{"navigationBarTextStyle":"black","navigationBarBackgroundColor":"#eeeeee"},"tabBar":{"color":"#000000","borderStyle":"black","backgroundColor":"white","iconfontSrc":"http://at.alicdn.com/t/c/font_2609616_ug660m936il.ttf?t=1666315561882","list":[{"pagePath":"pages/order/order","text":"接单","iconfont":{"text":"","selectedText":""}},{"pagePath":"pages/issue/issue","text":"发单","iconfont":{"text":"","selectedText":""}},{"pagePath":"pages/Contact/ContactList","text":"聊天","iconfont":{"text":"","selectedText":""}},{"pagePath":"pages/Profile/Profile","text":"我的","iconfont":{"text":"","selectedText":""}}]},"nvueCompiler":"uni-app","nvueStyleCompiler":"uni-app","renderer":"auto","splashscreen":{"alwaysShowBeforeRender":true,"autoclose":false},"appname":"helloUniapp","compilerVersion":"3.6.4","entryPagePath":"pages/order/order","networkTimeout":{"request":60000,"connectSocket":60000,"uploadFile":60000,"downloadFile":60000}};
var __uniRoutes = [{"path":"/pages/order/order","meta":{"isQuit":true,"isTabBar":true},"window":{"enablePullDownRefresh":true,"navigationStyle":"custom"}},{"path":"/pages/Profile/Profile","meta":{"isQuit":true,"isTabBar":true},"window":{}},{"path":"/pages/Profile/SecondPages/MySend","meta":{},"window":{"navigationBarTitleText":"我的发布"}},{"path":"/pages/Profile/SecondPages/MyOrder","meta":{},"window":{"navigationBarTitleText":"我的接单"}},{"path":"/pages/Profile/SecondPages/About","meta":{},"window":{}},{"path":"/pages/Profile/SecondPages/Setting","meta":{},"window":{"navigationBarTitleText":"账户设置"}},{"path":"/pages/Profile/SecondPages/Share","meta":{},"window":{"navigationBarTitleText":"推荐给好友"}},{"path":"/pages/Profile/ThirdPage/ProfileSetting","meta":{},"window":{"navigationBarTitleText":"账户设置"}},{"path":"/pages/Profile/ThirdPage/MessageSetting","meta":{},"window":{"navigationBarTitleText":"消息设置"}},{"path":"/pages/Profile/SecondPages/UserDetail","meta":{},"window":{"navigationStyle":"custom"}},{"path":"/pages/issue/issue","meta":{"isQuit":true,"isTabBar":true},"window":{}},{"path":"/pages/issue/wait","meta":{},"window":{}},{"path":"/pages/Contact/ContactList","meta":{"isQuit":true,"isTabBar":true},"window":{"backgroundColor":"white","navigationStyle":"custom"}},{"path":"/pages/Contact/SecondPage/Chat","meta":{},"window":{"navigationStyle":"custom"}},{"path":"/pages/Login/login","meta":{},"window":{}}];
__uniConfig.onReady=function(callback){if(__uniConfig.ready){callback()}else{onReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"ready",{get:function(){return isReady},set:function(val){isReady=val;if(!isReady){return}const callbacks=onReadyCallbacks.slice(0);onReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
__uniConfig.onServiceReady=function(callback){if(__uniConfig.serviceReady){callback()}else{onServiceReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"serviceReady",{get:function(){return isServiceReady},set:function(val){isServiceReady=val;if(!isServiceReady){return}const callbacks=onServiceReadyCallbacks.slice(0);onServiceReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
service.register("uni-app-config",{create(a,b,c){if(!__uniConfig.viewport){var d=b.weex.config.env.scale,e=b.weex.config.env.deviceWidth,f=Math.ceil(e/d);Object.assign(__uniConfig,{viewport:f,defaultFontSize:Math.round(f/20)})}return{instance:{__uniConfig:__uniConfig,__uniRoutes:__uniRoutes,global:void 0,window:void 0,document:void 0,frames:void 0,self:void 0,location:void 0,navigator:void 0,localStorage:void 0,history:void 0,Caches:void 0,screen:void 0,alert:void 0,confirm:void 0,prompt:void 0,fetch:void 0,XMLHttpRequest:void 0,WebSocket:void 0,webkit:void 0,print:void 0}}}});
