package com.atguigu.service;

import com.atguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    //通过房源id查询出房源的房东
    List<HouseUser> getHouseUsersByHouseId(Long houseId);
}
