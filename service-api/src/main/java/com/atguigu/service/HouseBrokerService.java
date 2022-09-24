package com.atguigu.service;

import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    //根据房源id查询查询出该房源经纪人的名字
    List<HouseBroker> getHouseBrokersByHouseId(Long houseId);
}
