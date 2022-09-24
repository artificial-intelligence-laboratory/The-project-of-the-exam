package com.atguigu.dao;

import com.atguigu.entity.Dict;

import java.util.List;

//数据字典dao层
public interface DictDao {
    List<Dict> findListByParentId(Long id); //根据父节点来查询子节点

    Integer isDictParentNode(Long id);  //判断是否为父节点


    //根据编码获取Dict对象
    Dict getDictByDictCode(String dictCode);

    //根据id获取name
    String getnameById(Long id);
}
