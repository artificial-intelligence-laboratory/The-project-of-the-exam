package com.atguigu.dao;

import com.atguigu.entity.AdminRole;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);
}
