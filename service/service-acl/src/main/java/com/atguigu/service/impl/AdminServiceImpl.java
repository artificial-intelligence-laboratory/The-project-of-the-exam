package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

   @Resource
    private AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return this.adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminDao.getAdminByUsername(username);
    }
}
