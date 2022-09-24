package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    //注入PermissionService
    @Reference
    private PermissionService permissionService;

    //去往菜单页面的首页
    @RequestMapping
    public String index(Map map){
       List<Permission> permissionList = permissionService.findAllMenu();
        map.put("list",permissionList);
        return "permission/index";
    }

    //去往新增页面
    @RequestMapping("/create")
    public String create(Map map,Permission permission){
        map.put("permission",permission);
        return "permission/create";
    }

    //保存新增的数据
    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.insert(permission);
        return "common/successPage";
    }

    //编辑
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Map map){
        Permission permission = permissionService.getById(id);
        map.put("permission",permission);
        //去往编辑页面
        return "permission/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.update(permission);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        permissionService.delete(id);
        return "redirect:/permission";
    }

}
