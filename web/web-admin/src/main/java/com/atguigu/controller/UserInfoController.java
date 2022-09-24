package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController{

    @Reference
    private UserInfoService userInfoService;

    //分页及带条件查询
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters添加到请求域中
        map.put("filters",filters);
        //调用AdminService中分页的方法
        PageInfo<UserInfo> pageInfo = userInfoService.findPage(filters);
        //将pageInfo添加到请求域中
        map.put("page",pageInfo);
        return "userInfo/index";
    }

    //去添加的方法
    @RequestMapping("/create")
    public String goAddPage(){
        return "userInfo/create";
    }

    //保存添加的数据
    @RequestMapping("/save")
    public String  save(UserInfo userInfo){
        //从前端获取的密码
        String password = userInfo.getPassword();
        //将密码进行加密
        String encryptPassword = MD5.encrypt(password);
        userInfo.setPassword(encryptPassword);
        userInfoService.insert(userInfo);
        return "common/successPage";
    }

    //去编辑的页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Map map){
        //调用userInfoService中getById()
        UserInfo userInfo = userInfoService.getById(id);
        //将userInfo添加到request域中
        map.put("userInfo",userInfo);
        return "userInfo/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(UserInfo userInfo){
        userInfoService.update(userInfo);
        return "common/successPage";
    }



    //删除用户
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userInfoService.delete(id);
        return "redirect:/userInfo";
    }

}
