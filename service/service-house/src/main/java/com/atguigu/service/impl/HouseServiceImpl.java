package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;



    @Override
    protected BaseDao<House> getEntityDao() {
        return this.houseDao;
    }


    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setStatus(status);
        house.setId(id);
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNUm, Integer pageSize, HouseQueryVo houseQueryVo) {
        //开启分页
        PageHelper.startPage(pageNUm,pageSize);
        //调用HouseDao中前端分页及带条件查询的方法
        Page<HouseVo> page =houseDao.findPageList(houseQueryVo);
        //遍历Page
        for (HouseVo houseVo : page) {
            //获取房屋类型
            String houseTypeName = dictDao.getnameById(houseVo.getHouseTypeId());
            //获取楼层
            String FloorName = dictDao.getnameById(houseVo.getFloorId());
            //获取朝向
            String directionName = dictDao.getnameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(FloorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page,5);
    }

    //重写该方法是为了显示房源中户型、楼层、朝向等信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //获取户型
        String houseTypeName = dictDao.getnameById(house.getHouseTypeId());
        //获取楼层
        String floorName = dictDao.getnameById(house.getFloorId());
        //获取建筑结构
        String buildStructureName = dictDao.getnameById(house.getBuildStructureId());
        //获取房屋朝向
        String directionName = dictDao.getnameById(house.getDirectionId());
        //获取装修情况
        String decorationName = dictDao.getnameById(house.getDecorationId());
        //获取房屋用途
        String HouseUseName = dictDao.getnameById(house.getHouseUseId());
        //设置值进去
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(HouseUseName);
        return house;
    }
}
