package com.gcc.fns.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaozhi
 * @description 上传服务
 * @create 2022-10-2022/10/26 1:53
 */
public interface UploaderService {

    /**
     * 上传头像
     * @param file      输入流
     * @return
     */
    String uploadFace(MultipartFile file);
}
