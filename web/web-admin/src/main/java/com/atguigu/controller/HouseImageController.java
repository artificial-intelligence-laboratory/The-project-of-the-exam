package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController{

    @Reference
    private HouseImageService houseImageService;

    //去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId,@PathVariable("type") Integer type, Map map){
        //将房源Id和type放到request域里面
        map.put("houseId",houseId);
        map.put("type",type);
        return "house/upload";
    }


    @ResponseBody
    //实现上传房源或房产图片,前端使用的是rest风格传递过来的
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,@PathVariable("type") Integer type,
    @RequestParam("file") MultipartFile[] files){
                try {
                    if (files.length > 0 && files != null) {
                        //遍历每一个MultipartFile文件
                        for (MultipartFile file : files) {
                            //获取字节数组
                            byte[] bytes = file.getBytes();
                            //获取图片名字
                            String originalFilename = file.getOriginalFilename();
                            //通过UUID随机生成一个随机字符串作为上传七牛云的图片和名字，防止图片名字重复被覆盖
                            String newFileName = UUID.randomUUID().toString();
                            //通过七牛云工具类来把图片上传到七牛云上
                            QiniuUtils.upload2Qiniu(bytes,newFileName);
                            //创建houseimage对象
                            HouseImage houseImage = new HouseImage();
                            houseImage.setHouseId(houseId);
                            //数据库名字可以是真实名字
                            houseImage.setImageName(originalFilename);
                            houseImage.setHouseId(houseId);
                            houseImage.setType(type);
                            //设置图片的路径，路径的格式：http://七牛云的域名/随机生成图片的名字
                            houseImage.setImageUrl("http://rianplkps.hn-bkt.clouddn.com/"+newFileName);
                            //调用houseimageService保存的方法
                            houseImageService.insert(houseImage);
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        return Result.ok();
    }

    //删除房源或房产图片
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseImageService.delete(id);
        //重定向到详情页面
        return "redirect:/house/"+houseId;

    }
}
