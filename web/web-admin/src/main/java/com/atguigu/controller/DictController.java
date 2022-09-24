package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/*

[{ id:'01', name:'n1', isParent:true},{ id:'02', name:'n2', isParent:false},
{ id:'03', name:'n3', isParent:true},{ id:'04', name:'n4', isParent:false}]
数据字典使用的是List<Map()> 集合里面使用的是map
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    @RequestMapping
    //展示数据字典的页面
    public String index(){
        return "dict/index";
    }

    @ResponseBody
    //获取数据字典数据
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        //调用DictService查询数据字典中的数据
        List<Map<String,Object>> zNodes=dictService.findZnodes(id);
        //Result是统一封装结果,把zNodes(集合)返回给Result中的data
        return Result.ok(zNodes);
    }

    //这里前台响应的是json
    @ResponseBody
    //根据父id获取子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId){
        //调用dictService中根据父节点查询所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }
}
