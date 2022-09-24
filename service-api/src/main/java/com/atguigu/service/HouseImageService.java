package com.atguigu.service;

import com.atguigu.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    //根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type);
}
