package com.gcc.fns.controller;

import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.service.UploaderService;
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
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Resource
    private UploaderService uploaderService;


    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("uploadFile") MultipartFile file) {
        String fileUrl = uploaderService.uploadFace(file);
        return GraceJSONResult.ok(fileUrl);
    }
}
