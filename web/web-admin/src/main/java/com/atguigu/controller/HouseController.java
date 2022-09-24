package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    //注入houseService
    @Reference
    private HouseService houseService;

    //注入communityService,查询所有小区
    @Reference
    private CommunityService communityService;

    //注入dictService
    @Reference
    private DictService dictService;

    //注入houseBrokerService
    @Reference
    private HouseBrokerService houseBrokerService;

    //注入houseImageService查询房源照片
    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;




    //分页带条件的查询
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters添加到请求域中让前台共享
        map.put("filters",filters);

        //调用service中的findPage方法,得到一个PageInfo对象
        PageInfo<House> pageInfo = houseService.findPage(filters);
        //把pageInfo添加到request域中，让前台共享
        map.put("page",pageInfo);

        setRequestAttribute(map);

        return "house/index";
    }

    //去添加用户的页面
    @RequestMapping("/create")
    public String create(Map map){
        setRequestAttribute(map);
        return "house/create";
    }

    //保存新增数据
    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    //编辑页面
    @RequestMapping("/edit/{id}")
    public String edit(Map map, @PathVariable("id") Long id){
        House house = houseService.getById(id);
        setRequestAttribute(map);
        map.put("house",house);
        return "house/edit";
    }

    //更新好的数据保存
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    //发布和取消发布的方法
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        houseService.publish(id,status);
        return "redirect:/house"; //超链接直接就重定向就可以了，如果是弹框会刷新不了页面就是去成功页面
    }

    //查询房源详情
    @RequestMapping("/{houseId}")
    public String show(@PathVariable("houseId") Long houseId,Map map){
        //根据id信息来获取house信息
        House house = houseService.getById(houseId);
        //将房源信息存储到request域中
        map.put("house",house);
        //根据id信息来获取房源信息,并放到request域中
        map.put("community",communityService.getById(house.getCommunityId()));
        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 2);
        //将房源图片对象放到request域中共享
        map.put("houseImage1List",houseImage1List);
        //将房产图片对象放到request域中
        map.put("houseImage2List",houseImage2List);
        //查询经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(houseId);
        //将经纪人信息存放到request域中共享
        map.put("houseBrokerList",houseBrokerList);
        //查询房源房东信息
        List<HouseUser> houseUserList = houseUserService.getHouseUsersByHouseId(houseId);
        //将房源房东信息存储到request域中
        map.put("houseUserList",houseUserList);
        return "house/show";
    }



    //将所有小区和数据的数据放到request域中
    public void setRequestAttribute(Map map){
        //查询所有小区
        List<Community> communityList = communityService.findAll();
        //查询户型
        List<Dict> houseTypeList = dictService.findListDictCode("houseType");
        //查询楼层
        List<Dict> floorList = dictService.findListDictCode("floor");
        //查询建筑结构
        List<Dict> buildStructure = dictService.findListDictCode("buildStructure");
        //查询请朝向
        List<Dict> direction = dictService.findListDictCode("direction");
        //查询装修情况
        List<Dict> decoration = dictService.findListDictCode("decoration");
        List<Dict> houseUseList = dictService.findListDictCode("houseUse");
        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("floorList",floorList);
        map.put("buildStructureList",buildStructure);
        map.put("directionList",direction);
        map.put("decorationList",decoration);
        map.put("houseUseList",houseUseList);
    }






}
