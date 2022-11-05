package com.gcc.fns.controller;

import com.gcc.fns.common.utils.AjaxResult;
import com.gcc.fns.mapper.MessageInfoMapper;
import com.gcc.fns.mapper.SessionListMapper;
import com.gcc.fns.model.entity.MessageInfo;
import com.gcc.fns.model.entity.SessionList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snail
 * @create 2022-10-30  23:20
 */
@Api(value = "消息接口",tags = "消息接口")
@RequestMapping("/message")
@RestController
public class MessageInfoController {

    @Resource
    private MessageInfoMapper messageInfoMapper;

    @Resource
    private SessionListMapper sessionListMapper;

    //消息列表（聊天页面）
    @ApiOperation(value = "加载消息列表",notes = "加载消息列表" +
            "<br> 传入会话ID，获取用户和目标用户的聊天信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionId",value = "会话ID",dataType = "long",required = true)
    })
    @PutMapping("/msgList")
    public AjaxResult<?> msgList(@RequestParam Long sessionId){
        SessionList sessionList = sessionListMapper.selectByPrimaryKey(sessionId);
        if (sessionList == null){
            return AjaxResult.success();
        }
        Long fromUserId = sessionList.getUserId();
        Long toUserId = sessionList.getToUserId();
        List<MessageInfo> messageInfoList = messageInfoMapper.selectMsgList(fromUserId,toUserId);
        String avatar = sessionList.getAvatar();
        //更新消息已读
        messageInfoMapper.msgRead(fromUserId,toUserId);
        //更新会话里面的未读消息
        sessionListMapper.updateUnReadCount(fromUserId,toUserId);
        return AjaxResult.success(messageInfoList,messageInfoList.size());
    }

}
