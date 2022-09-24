package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    /*
    原来的
    @RequestMapping("/")
    //去首页
    public String index(){
        return "frame/index";
    }*/

    @RequestMapping("/")
    //去首页
    public String index(Map map){
       /*
       未使用springSecurity
        1.设置默认的用户id
//        Long userId =1L;
        2.调用AdminService中查询的方法
//        Admin admin = adminService.getById(userId);

        */
        //通过springSecurity获取用户
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       //调用adminService中获取admin对象
        Admin admin = adminService.getAdminByUsername(user.getUsername());
        //根据用户id调用PermissionService中获取用户权限菜单的方法
        List<Permission> permissionList =permissionService.getMenuPermissionByAdminId(admin.getId());
        //将用户和用户菜单放到request域里面
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }

    //去主页面
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    //去登陆页面
    @RequestMapping("/login")
    public String goLoginPage(){
        return "frame/login";
    }

    //去没有权限的页面
    @RequestMapping("/auth")
    public String goAuthPage(){
        return "frame/auth";
    }

}
