package com.gcc.fns.controller;

import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.service.UploaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 文件上传
 * @create 2022-10-2022/10/26 1:50
 */
@Api(value = "文件服务接口", tags = "文件服务接口")
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Resource
    private UploaderService uploaderService;

    @ApiOperation(value = "上传头像接口", notes = "参数的形式上传头像")
    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("uploadFile") MultipartFile file) {
        String fileUrl = uploaderService.uploadFace(file);
        return GraceJSONResult.ok(fileUrl);
    }
}
