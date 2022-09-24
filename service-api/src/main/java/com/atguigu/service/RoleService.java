package com.atguigu.service;

import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    //通过用户id查询出角色
    Map<String, Object> findRoleByAdminId(Long adminId);

    void saveUserRole(Long adminId, Long[] roleIds);
}
