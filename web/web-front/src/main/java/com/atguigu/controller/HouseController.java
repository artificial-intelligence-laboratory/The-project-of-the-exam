package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;


    //分页及带条件的方法
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNUm, @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        //调用houseService中前端分页及带条件查询的方法
        PageInfo<HouseVo> pageInfo= houseService.findPageList(pageNUm,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }
    //查询房源详情
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){
        //查询房源信息
        House house = houseService.getById(id);
        //查询小区信息
        Community community = communityService.getById(house.getCommunityId());
        //查询房源经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(id);
        //查询房源图片信息
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        //新建一个map对象
        Map map=new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
//        map.put("isFollow",false);
        //设置一个变量
        Boolean isFollowed=false;
        //从session中获取userInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //判断是否登陆过
        if(userInfo!=null){
            //证明已经登录,判断是否关注房源
            isFollowed= userFollowService.isFollowed(userInfo.getId(),id);
        }
        //将isFollowed放到map中
        map.put("isFollow",isFollowed);
        //返回结果
        return Result.ok(map);
    }
}
