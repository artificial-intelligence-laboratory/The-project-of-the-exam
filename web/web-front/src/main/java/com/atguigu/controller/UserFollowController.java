package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;


    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session){
        //获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //关注房源
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }

    //查看我的关注
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result seeMyFollow(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,
                              HttpSession session){
        //获取该用户
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //获取用户Id
        Long userId = userInfo.getId();
        /*
         1.新建pageInfo对象，分页查询关注房源对象
         2.关注房源对象封装成UserFollowVo对象
         */
        PageInfo<UserFollowVo> pageInfo = userFollowService.findMyFollowPageList(pageNum,pageSize,userId);
        //返回前台数据
        return Result.ok(pageInfo);
    }

    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result canselFollow(@PathVariable("id") Long id){
        //取消关注
        userFollowService.canselFollow(id);
        return Result.ok();
    }
}
