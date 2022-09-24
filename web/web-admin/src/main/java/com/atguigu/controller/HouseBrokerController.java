package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    //注入adminService
    @Reference
    private AdminService adminService;

    //注入houseBrokerService
    @Reference
    private HouseBrokerService houseBrokerService;

    //去添加经纪人的页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map){
       //将房源id放到request域中
        map.put("houseId",houseId);
       //调用AdminService中获取所有用户的方法
        List<Admin> adminList = adminService.findAll();
        //将所有的用户放到request域里面
        map.put("adminList",adminList);
        return "houseBroker/create";
    }

    //保存添加经纪人的信息
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        //调用AdminService中的方法根据经纪人的id查询经纪人的信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        //调用houseBrokerService中保存的方法
        houseBrokerService.insert(houseBroker);
        //去成功页面
        return "common/successPage";
    }

    //去修改的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id,Map map){
        //调用HouseBrokerService中根据id查询经纪人的方法
        HouseBroker houseBroker = houseBrokerService.getById(id);
        //将经纪人添加到request域中
        map.put("houseBroker",houseBroker);
        //查询所有经纪人
        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);
        return "houseBroker/edit";
    }

    //更新保存的数据
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        //调用AdminService中的方法根据经纪人的id查询出需要更新经纪人的信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("brokerId") Long brokerId){
        //调用删除的方法
        houseBrokerService.delete(brokerId);
        //重定向到查询HouseController房源详情的方法
        return "redirect:/house/"+houseId;
    }

}
