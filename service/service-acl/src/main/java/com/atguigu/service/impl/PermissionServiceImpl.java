package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //获取全部权限列表
       List<Permission> permissionList =  permissionDao.findAll();
       //获取角色获得的权限列表id
        List<Long> permissionIdList =rolePermissionDao.findPermissionIdListByRoleId(roleId);

        //新建一个list
        List<Map<String, Object>> zNodes =new ArrayList<>();

        //遍历全部权限列表
        for (Permission permission : permissionList) {
            //构建ztree数据
            //前端需要的数据是： { id:1, pId:0, name:"随意勾选 1", open:true},因此就是list<map>
            Map<String,Object> map=new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            //遍历判断该角色拥有的权限是否包含在全部权限里
            if (permissionIdList.contains(permission.getId())){
                map.put("checked",true);
            }
            //将map添加到list里面
            zNodes.add(map);
        }
        return zNodes;
    }

    //分配权限
    @Override
    public void savePermission(Long roleId, Long[] permissionIds) {
        //通过角色id删除权限
        rolePermissionDao.deleteByRoleId(roleId);
        //遍历权限id
        for (Long permissionId : permissionIds) {
            if (permissionId!=null) {
                //调用rolePermissionDao中保存权限的方法
                rolePermissionDao.addRoleIdAndPermission(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList =null;
        //判断是否是系统管理员
        if (userId==1){
            //证明是系统管理员，获取所有的权限
            permissionList= permissionDao.findAll();
        }else {
            //根据用户id查询权限菜单
            permissionList=permissionDao.getMenuPermissionByAdminId(userId);
        }
        //通过PermissionHelper 工具类将list转换成树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);
        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        //获取全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty(permissionList)){
            return null;
        }
        //调用工具类来让权限数据变成树形结构数据
        List<Permission> permissionTreeList = PermissionHelper.bulid(permissionList);
        return permissionTreeList;
    }

    @Override
    public List<String> getCodesByAdminId(Long id) {
        List<String> permissionCodes=null;
        if (id==1){
            //证明是系统管理员
            permissionCodes=permissionDao.getAllPermissionCodes();
        }else {
            permissionCodes=permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}
