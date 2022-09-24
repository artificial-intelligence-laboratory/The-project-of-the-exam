package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    //查询出所有角色
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


    
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
       //查询出所有角色
        List<Role> roleList = roleDao.findAll();
        //查询出用户有的角色id，查询的是acl_admin_role
        List<Long> existsRoleIdList=adminRoleDao.findRoleByAdminId(adminId);
        //把所有角色分成两部分，一部分是有的角色，一部分是还没有的角色
        //新建两个list,分配好的角色
        List<Role> assginRoleList = new ArrayList<>();
        //没有的角色
        List<Role> noAssginRoleList =new ArrayList<>();
        //遍历所有角色
        for (Role role : roleList) {
            if (existsRoleIdList.contains(role.getId())){
                //表明拥有这个角色
                assginRoleList.add(role);
            }else {
                //表名没有这个角色
                noAssginRoleList.add(role);
            }
        }

        //新建一个map
        Map<String, Object> roleMap =new HashMap<>();
        //将这两个list添加到map里面,还需要controller层将这两个添加到request域中
        roleMap.put("assginRoleList",assginRoleList);
        roleMap.put("noAssginRoleList",noAssginRoleList);
        return roleMap;
    }

    //保存用户权限是先删除原有的用户权限再保存全部的用户角色
    @Override
    public void saveUserRole(Long adminId, Long[] roleIds) {
        //根据用户id删除权限
        adminRoleDao.deleteByAdminId(adminId);
        //遍历角色id
        for (Long roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) continue;
            AdminRole userRole = new AdminRole();
            userRole.setAdminId(adminId);
            userRole.setRoleId(roleId);
            //将角色保存
            adminRoleDao.insert(userRole);
        }
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }
}
