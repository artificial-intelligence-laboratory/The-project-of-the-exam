package com.atguigu.dao;

import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {

    //根据房源id查询查询出该房源经纪人的名字
    List<HouseBroker> getHouseBrokersByHouseId(Long houseId);

}
