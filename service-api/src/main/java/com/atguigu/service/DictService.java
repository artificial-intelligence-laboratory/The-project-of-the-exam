package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    //查询数据字典中的数据，通过Ztree实现
    List<Map<String, Object>> findZnodes(Long id);

    List<Dict> findListByParentId(Long parentId);

    //根据编码获取该节点下所有子节点
    List<Dict> findListDictCode(String dictCode);

    //通过id查询名字
    String getNameById(Long id);
}
