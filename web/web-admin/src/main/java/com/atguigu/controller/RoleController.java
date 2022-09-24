package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    public static final String SUCCESS_PAGE="common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping
//    public String index(Map map){
//        //调用service中方法
//        List<Role> roleList = roleService.findAll();
//        //将所有角色放到request域中
//       map.put("list",roleList);
//        //去往目标页面
//        return "role/index";
//    }

    //分页查询和条件查询
    @PreAuthorize("hasAuthority('role.show')") //配置权限
    @RequestMapping
    public String index(Map map,HttpServletRequest request){
        //获取页面请求参数
        Map<String, Object> filters = getFilters(request);
        //controller调用PageInfo,PageInfo里面有更多方法，提供给前端使用(方便使用)。
        PageInfo<Role> page =roleService.findPage(filters);

        //把filters,PageInfo对象添加到request域中
        map.put("filters",filters);
        map.put("page",page);
        return "role/index";
    }

    //新增跳转
    @PreAuthorize("hasAuthority('role.create')") //配置权限
    @RequestMapping("/create")
    public String goAddPage(){
        return "role/create";
    }

    @PreAuthorize("hasAuthority('role.create')") //配置权限
    @RequestMapping("/save")
    public String saveRole(Role role){
        //添加角色
        roleService.insert(role);
        //重定向回到查询所有数据,不是很友好，使用H+的方法
//        return "redirect:/role";
        //去成功页面
        return SUCCESS_PAGE;
    }

    //删除角色
    @PreAuthorize("hasAuthority('role.delete')") //此时只有Delete权限的时候才可以调用该方法
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
        //没有提示框，所以就不用使用那个成功页面
        return "redirect:/role";
    }

    //去修改页面的方法
    @PreAuthorize("hasAuthority('role.edit')") //配置权限
    @RequestMapping("/edit/{id}")
    public String goEdit(@PathVariable("id") Long id,Map map){
       Role role=roleService.getById(id);
       //添加到请求域中
      map.put("role",role);
//        model.addAllAttributes("role",role)
       return "role/edit";
    }

    //修改的方法
    @PreAuthorize("hasAuthority('role.edit')") //配置权限
    @RequestMapping("/update")
    public String update(Role role){
    roleService.update(role);
    return SUCCESS_PAGE;
    }

    //去往分配权限的页面
    @PreAuthorize("hasAuthority('role.assgin')") //配置权限
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(Map map,@PathVariable("roleId") Long roleId){
        //前端需要的数据类型是： { id:1, pId:0, name:"随意勾选 1", open:true},因此就是list<map>
      List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
      //将角色id添加到request域中
        map.put("roleId",roleId);
        //将zNodes添加到request域中
        map.put("zNodes",zNodes);
        //去往分配权限的页面
        return "role/assginShow";
    }

    //分配权限
    @PreAuthorize("hasAuthority('role.assign')") //配置权限
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId ,
                                   @RequestParam("permissionIds") Long[] permissionIds){
        //分配权限
        permissionService.savePermission(roleId,permissionIds);
        //返回成功页面
        return "common/successPage";
    }


}
