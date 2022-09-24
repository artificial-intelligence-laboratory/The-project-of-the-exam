package com.atguigu.service;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

public interface HouseService extends BaseService<House> {

    void publish(Long id, Integer status);


    PageInfo<HouseVo> findPageList(Integer pageNUm, Integer pageSize, HouseQueryVo houseQueryVo);

}
