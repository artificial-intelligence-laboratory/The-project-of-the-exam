package com.gcc.fns.controller;

import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.exception.ThrowException;
import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.mapper.AppUserMapper;
import com.gcc.fns.mapper.SessionListMapper;
import com.gcc.fns.model.entity.AppUser;
import com.gcc.fns.model.entity.SessionList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snail
 * @create 2022-10-30  22:45
 */
@Api(value = "会话接口",tags = "会话接口")
@RequestMapping("/session")
@RestController
@Slf4j
public class SessionController {

    @Resource
    private AppUserMapper appUserMapper;

   @Resource
    private SessionListMapper sessionListMapper;

   @ApiOperation(value = "获取已建立的会话",notes = "获取已建立的会话" +
           "<br> 通过用户id获取用户已经建立的聊天列表")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "long",required = true)
   })
   @GetMapping("/alreadySessionList")
   public GraceJSONResult sessionListAlready(@RequestParam Long userId){
       List<SessionList> sessionLists = sessionListMapper.selectByUserId(userId);
//       return AjaxResult.success(sessionLists, sessionLists.size());
       return GraceJSONResult.ok(sessionLists);
   }

   @ApiOperation(value = "建立正在等待会话的用户列表",notes = "建立正在等待会话的用户列表" +
           "<br> 已通过注册的用户即为正在等待建立会话的用户")
   @ApiImplicitParams(
           @ApiImplicitParam(name = "userId",value = "会话发起者的ID",dataType = "long",required = true)
   )
   @GetMapping("/sessionListWait")
   public GraceJSONResult sessionListWait(@RequestParam Long userId){
       List<Long> list = sessionListMapper.selectUserIdByUserId(userId);
       list.add(userId);
       List<AppUser> cloudList = appUserMapper.getCloudList(list);
//       Long toUserId = sessionListMapper.selectByPrimaryKey(userId).getToUserId();
//       cloudList.add(appUserMapper.selectById(toUserId).getAvatar());

//       messageInfo.setAvatar(appUserMapper.selectById(sessionList.getUserId()).getAvatar());
//       return AjaxResult.success(cloudList,cloudList.size());
       return GraceJSONResult.ok(cloudList);
   }

   @ApiOperation(value = "创建一个会话",notes = "创建一个未建立的会话，如果对面也没建立，这也会给对方建立" +
           "<br> 传入三个参数,用户自己id,目标用户id,目标用户名称")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "long",required = true),
           @ApiImplicitParam(name = "toUserId",value = "目标用户ID",dataType = "long",required = true),
           @ApiImplicitParam(name = "toUserName",value = "目标用户姓名",dataType = "string",required = true)
   })
   @PostMapping("/createSession")
   public GraceJSONResult createSession(@RequestParam Long userId,@RequestParam Long toUserId,@RequestParam String toUserName){

        SessionList sessionList = new SessionList();
        sessionList.setUserId(userId);
        sessionList.setToUserId(toUserId);
        sessionList.setListName(toUserName);
        sessionList.setUnReadCount(0);
        //存对方的头像
       sessionList.setAvatar(appUserMapper.selectByIdAvatar(toUserId));

       // 判断会话存在不存在
       Long ifSession = sessionListMapper.selectIdByUser(userId,toUserId);
       Long ifToSession = sessionListMapper.selectIdByUser(toUserId,userId);
       if (ifSession ==null || ifSession <= 0) {
           sessionListMapper.insert(sessionList);
       }else {
           // 判断对方和我建立会话，如果没有，也要建立
           // 给对方建立会话
           if (ifToSession ==null || ifToSession <= 0){
               AppUser appUser = appUserMapper.selectByPrimaryUserKey(userId);
               sessionList.setUserId(toUserId);
               sessionList.setToUserId(userId);
               sessionList.setListName(appUser.getUsername());
               //自己的头像存给对方
               sessionList.setAvatar(appUserMapper.selectByIdAvatar(userId));
               sessionListMapper.insert(sessionList);
           }
           ThrowException.custom(ResponseStatusEnum.CHAR_ALREADY_EXISTS);
       }
       //判断对方和我建立会话，如果没有，也要建立
       //给对方建立会话
       if (ifToSession ==null || ifToSession <= 0){
           AppUser appUser = appUserMapper.selectByPrimaryUserKey(userId);
           sessionList.setUserId(toUserId);
           sessionList.setToUserId(userId);
           sessionList.setListName(appUser.getUsername());
           //自己的头像存给对方
           sessionList.setAvatar(appUserMapper.selectByIdAvatar(userId));
           sessionListMapper.insert(sessionList);
       }
       return GraceJSONResult.ok();
   }

    // 删除会话
    @ApiOperation(value = "删除一个会话",notes = "传入会话id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionId",value = "会话id",dataType = "long",required = true)
    })
    @DeleteMapping("/delSession")
    public GraceJSONResult delSession(@RequestParam Long sessionId){
        sessionListMapper.deleteByPrimaryKey(sessionId);
        return GraceJSONResult.ok();
    }

}
