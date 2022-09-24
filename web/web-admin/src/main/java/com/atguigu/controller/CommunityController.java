package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController  extends BaseController{

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    //分页带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将请求参数放到request中去
        map.put("filters",filters);
        //使用PageInfo对象进行分页
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        //将pageInfo对象放到request域中去
        map.put("page",pageInfo);
        //根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListDictCode("beijing");
        //将区域放到request域中
        map.put("areaList",areaList);
        return "community/index";
    }


    //去新增的页面
    @RequestMapping("/create")
    public String goAddPage(Map map){
        //获取区域内容，需要向request域中添加areaList对象进行共享
        List<Dict> areaList = dictService.findListDictCode("beijing");
        map.put("areaList",areaList);
        return "community/create";
    }

    //保存新增的数据
    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return "common/successPage";
    }

    //去修改页面
    @RequestMapping("/edit/{id}")
    public String goeditPage(@PathVariable("id") Long id,Map map){
        Community community = communityService.getById(id);
        List<Dict> areaList = dictService.findListDictCode("beijing");
        map.put("community",community);
        map.put("areaList",areaList);
        return "community/edit";
    }

    //更新后的数据保存
    @RequestMapping("/update")
    public String update(Community community){
       communityService.update(community);
       return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        communityService.delete(id);
        return "common/successPage";
    }



}
