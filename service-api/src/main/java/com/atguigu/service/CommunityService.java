package com.atguigu.service;

import com.atguigu.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community>{
    //community社区查询所有数据
    List<Community> findAll();

}
