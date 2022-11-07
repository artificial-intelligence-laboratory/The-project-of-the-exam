package com.gcc.fns.service.impl;

import com.gcc.fns.common.utils.JsonUtils;
import com.gcc.fns.common.utils.SessionUtil;
import com.gcc.fns.common.utils.SpringContextUtil;
import com.gcc.fns.mapper.AppUserMapper;
import com.gcc.fns.mapper.MessageInfoMapper;
import com.gcc.fns.mapper.SessionListMapper;
import com.gcc.fns.model.entity.AppUser;
import com.gcc.fns.model.entity.MessageInfo;
import com.gcc.fns.model.entity.SessionList;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author snail
 * @create 2022-10-30  17:28
 */
@Component
@Api(value = "websocket连接接口")
@ServerEndpoint("/webSocket/{userId}/{sessionId}")
@Slf4j
public class WebSocket {

    @Resource
    private SessionListMapper sessionListMapper;

    @Resource
    private AppUserMapper  appUserMapper ;

    @Resource
    private MessageInfoMapper messageInfoMapper;

    private Session session;

    /**
     * 开启连接
     * @param session session 对象
     * @param userId 该用户id
     * @param sessionId 会话id
     * @return void
     * @author Snail
     * @date 2022/11/2 22:47
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId,@PathParam(value = "sessionId") String sessionId){
        this.session = session;
        SessionUtil.webSockets.put(userId, WebSocket.this);
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        SessionUtil.sessionPool.put(userId,list);
        log.info("【websocket消息】有新的连接，总数为:"+SessionUtil.webSockets.size());
    }

    /**
     * 关闭连接
     * @param
     * @return void
     * @author Snail
     * @date 2022/11/2 22:48
     */
    @OnClose
    public void onClose(){
        //断开链接删除用户的session
        Long userId = Long.parseLong(this.session.getRequestParameterMap().get("userId").get(0));
        SessionUtil.sessionPool.remove(userId);
        SessionUtil.webSockets.remove(userId);

        if (appUserMapper == null){
            this.appUserMapper = (AppUserMapper) SpringContextUtil.getBean("appUserMapper");
        }
        AppUser user = appUserMapper.selectByPrimaryUserKey(userId);
        SessionUtil.curUserPool.remove(user.getUsername());
        log.info("【websocket消息】连接断开，总数为:" + SessionUtil.webSockets.size());
    }

    /**
     * 消息处理
     * @param message 消息
     * @return void
     * @author Snail
     * @date 2022/11/2 22:48
     */
    @OnMessage
    public void OnMessage(String message){
        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);

        String substring = message.substring(1, message.length() - 3);

        if (sessionId == null){
            System.out.println("sessionId 错误");
        }
        // 在这里无法注入Mapper所以使用这种方式注入Mapper
        if (sessionListMapper == null){
            this.sessionListMapper = (SessionListMapper) SpringContextUtil.getBean("sessionListMapper");
        }
        if (appUserMapper == null){
            this.appUserMapper = (AppUserMapper) SpringContextUtil.getBean("appUserMapper");
        }
        if (messageInfoMapper == null){
            this.messageInfoMapper = (MessageInfoMapper) SpringContextUtil.getBean("messageInfoMapper");
        }

        //获取本用户的id 将消息发送过去
        SessionList sessionList = sessionListMapper.selectByPrimaryKey(Long.parseLong(sessionId));

        AppUser user = appUserMapper.selectByPrimaryUserKey(sessionList.getUserId());
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent(substring);
        messageInfo.setCreateTime(new Date());
        messageInfo.setFromUserId(sessionList.getUserId());
        messageInfo.setToUserId(sessionList.getToUserId());
        messageInfo.setUnReadState(0);

        //消息持久化
        messageInfoMapper.insert(messageInfo);

        //判断用户是否在线，不存在就结束
        List<Object> list = SessionUtil.sessionPool.get(sessionList.getToUserId());
        if (list == null || list.isEmpty()){
            //不存在，更新未读数和未读消息和时间
            sessionListMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId(),messageInfo.getContent(),messageInfo.getCreateTime());
        }else {
            //存在，判断会话存在不存在
            String id = sessionListMapper.selectIdByUser(sessionList.getToUserId(),sessionList.getUserId() ) + "";
            String temp = list.get(0) + "";
            if (id.equals(temp)){
                //存在会话，直接发消息
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(messageInfo));
            }else {
                //判断会话列表是否存在
                if (id == null || "".equals(id) || "null".equals(id)){
                    //不存在，则新增会话列表,列出对应消息
                    SessionList tempSessionList = new SessionList();
                    tempSessionList.setUserId(sessionList.getToUserId());
                    tempSessionList.setListName(user.getUsername());
                    tempSessionList.setUnReadCount(1);
                    sessionListMapper.insert(tempSessionList);
                }else {
                    //更新未读消息数量
                    sessionListMapper.addUnReadCount(sessionList.getToUserId(),sessionList.getUserId(),messageInfo.getContent(),messageInfo.getCreateTime());
                }

                //会话不存在发送列表消息
                List<SessionList> sessionLists = sessionListMapper.selectByUserId(sessionList.getToUserId());

                sendTextMessage(sessionList.getToUserId(),JsonUtils.objectToJson(sessionLists));
            }
        }
        log.info("【websocket消息】收到客户端消息:"+substring);
    }



    /**
     * 单点消息，发送文本
     * @param userId
     * @param message
     * @return void
     * @author Snail
     * @date 2022/11/2 22:49
     */
    public void sendTextMessage(Long userId,String message){

        Session session = (Session) SessionUtil.sessionPool.get(userId).get(1);
        if (session != null){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
