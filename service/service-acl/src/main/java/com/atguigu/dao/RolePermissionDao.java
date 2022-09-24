package com.atguigu.dao;

import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission>{
    List<Long> findPermissionIdListByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void addRoleIdAndPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
