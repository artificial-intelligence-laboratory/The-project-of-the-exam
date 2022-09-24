package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //根据父id查询该节点下的子节点
        List<Dict> dictList=dictDao.findListByParentId(id);
        //创建返回的list
        List<Map<String,Object>> zNodes =new ArrayList<>();
        //遍历dictList
        for (Dict dict : dictList) {
            // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}] 需要拼接
           Map map=new HashMap();
           map.put("id",dict.getId());
           map.put("name",dict.getName());
           //调用dictDao中判断该节点是否是父节点的方法,返回的是isParent:true
            Integer count=dictDao.isDictParentNode(dict.getId());
            map.put("isParent",count>0 ? true : false);
            //将map返回给list
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        //根据父id查询该节点下的子节点
        List<Dict> dictList=dictDao.findListByParentId(parentId);
        return dictList;
    }

    @Override
    public List<Dict> findListDictCode(String dictCode) {
        Dict dict = dictDao.getDictByDictCode(dictCode);
        if (dict==null)
            return null;
        //调用根据父Id查询所有子节点下的方法
        return this.findListByParentId(dict.getId());
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getnameById(id);
    }
}
