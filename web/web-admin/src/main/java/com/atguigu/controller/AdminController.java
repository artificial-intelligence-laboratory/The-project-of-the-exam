package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
    public static final String  SUCCESS_PAGE="common/successPage";

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    //注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder;

    //分页及带条件查询
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters添加到请求域中
        map.put("filters",filters);
        //调用AdminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        //将pageInfo添加到请求域中
        map.put("page",pageInfo);
        return "admin/index";
    }

    //去添加用户的页面
    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }

    //保存用户
    @RequestMapping("/save")
    public String save(Admin admin){
        //使用密码加密器对密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //调用adminService中保存的方法
        adminService.insert(admin);
        return SUCCESS_PAGE;
    }

    //删除用户
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId){
        //调用AdminService中删除方法
        adminService.delete(adminId);
        //重定向
        return "redirect:/admin";
    }

    //去更新的页面，更新需要查找到该用户，然后放到请求域进行共享让其修改
    @RequestMapping("/edit/{adminId}")
    public String goEditPage(@PathVariable("adminId") Long adminId,Map map){
        //调用adminService中根据id查询对象的方法
        Admin admin = adminService.getById(adminId);
        //将admin对象放到request域中
        map.put("admin",admin);
        return "admin/edit";
    }

    //修改用户
    @RequestMapping("/update")
    public String update(Admin admin){
        //调用adminService更新的方法
        adminService.update(admin);
        return SUCCESS_PAGE;
    }

    //去上传头像
    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(@PathVariable("id") Long id,Map map){
        //将用户id放到request域中
        map.put("id",id);
        //去往上传头像页面
        return "admin/upload";
    }

    //上传头像
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file){
        try {
            //根据用户id查询AdminService中查询用户的方法
            Admin admin = adminService.getById(id);
            //获取字节数组
            byte[] bytes = file.getBytes();
            //使用UUID随机生成一个文件名
            String fileName = UUID.randomUUID().toString();
            //通过七牛工具类将文件上传到七牛云
            QiniuUtils.upload2Qiniu(bytes,fileName);
            //给用户设置头像地址
            admin.setHeadUrl("http://rianplkps.hn-bkt.clouddn.com/"+fileName);
            //更新用户信息
            adminService.update(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "common/successPage";
    }

    //去往分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String assignRoles(@PathVariable("adminId") Long adminId, ModelMap modelMap){
        //通过用户id查询有哪些角色
        Map<String,Object> roleMap=roleService.findRoleByAdminId(adminId);
        //将adminId添加到request域中
        modelMap.addAttribute("adminId",adminId);
        //将roleMap添加到request域里面,roleMap里面再把那两个list添加到request域里面
         modelMap.addAllAttributes(roleMap);
        //去往分配角色的页面
        return "admin/assignShow";
    }

    /**
     * 根据用户分配角色
     */
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        //给用户分配角色
        roleService.saveUserRole(adminId,roleIds);
        return "common/successPage";

    }
}
