package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {

    @Reference
    private HouseUserService houseUserService;

    //去添加房东的页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map){
        //将房源Id放到request域中
        map.put("houseId",houseId);
        return "houseUser/create";
    }

    //添加房东
    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        //调用houseUserService方法
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id,Map map){
        //调用houseUserService中根据id查询的方法
        HouseUser houseUser = houseUserService.getById(id);
        //将houseUser添加到request域中
        map.put("houseUser",houseUser);
        //去往修改页面
        return "houseUser/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/successPage";
    }

    //删除,houseId使用来重定向
    @RequestMapping("/delete/{houseId}/{userId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("userId") Long userId){
        houseUserService.delete(userId);
        return "redirect:/house/"+houseId;
    }

}
