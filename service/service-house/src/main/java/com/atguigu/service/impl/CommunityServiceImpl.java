package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community>  implements CommunityService{

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return this.communityDao;
    }

    //重写getById方法是为了给区域和板块设置值
    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        //获取区域
        String AreaName = dictDao.getnameById(community.getAreaId());
        //获取板块
        String plateName = dictDao.getnameById(community.getPlateId());
        community.setAreaName(AreaName);
        community.setPlateName(plateName);
        return community;
    }

    //重写分页的方法，目的是为了给小区中的区域和板块的名字赋值
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);

        //调用communityDao中分页及带条件的方法
        Page<Community> page =communityDao.findPage(filters);
        //遍历page对象
        for (Community community : page) {
            //根据区域的id获取区域的名字
            String areaName = dictDao.getnameById(community.getAreaId());
            String plateName = dictDao.getnameById(community.getPlateId());
            //给community对象的区域和板块赋值
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<>(page,10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }
}
