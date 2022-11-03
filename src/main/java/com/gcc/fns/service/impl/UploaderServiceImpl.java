package com.gcc.fns.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.exception.ThrowException;
import com.gcc.fns.common.utils.MD5Util;
import com.gcc.fns.common.utils.RedisKeyUtil;
import com.gcc.fns.common.utils.RedisOperator;
import com.gcc.fns.common.utils.UserHolder;
import com.gcc.fns.common.utils.aliyun.AliyunOSSUtil;
import com.gcc.fns.model.vo.AppUserInfoVo;
import com.gcc.fns.service.UploaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/26 1:53
 */
@Service
@Slf4j
public class UploaderServiceImpl implements UploaderService {

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;

    @Resource
    private RedisOperator redis;

    @Value("${custom.upload.count}")
    private Integer uploadCount;

    @Override
    public String uploadFace(MultipartFile file){
        AppUserInfoVo user = UserHolder.getUser();
        Long id = user.getId();
        // 用户一天只能修改三次
//        String count = redis.get(RedisKeyUtil.getUploadCount(id));
//        if (count != null) {
//            if (Integer.valueOf(count) >= 3) {
//                ThrowException.custom(ResponseStatusEnum.FILE_UPLOAD_COUNT_OVER);
//            }
//        } else {
//            redis.set(RedisKeyUtil.getUploadCount(id), "0",RedisConstant.EXPIRATION_ONE_DAY);
//        }
        // 文件不能为空
        if (file == null) {
            ThrowException.custom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }
        // 文件名不能为空
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            ThrowException.custom(ResponseStatusEnum.FILE_NAME_NOT);
        }
        // 获取扩展名
        String extName = FileNameUtil.getSuffix(filename);
        if (!extName.equalsIgnoreCase("jpg")
                && !extName.equalsIgnoreCase("png")
                && !extName.equalsIgnoreCase("jpeg")) {
            ThrowException.custom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
        }
        // 生成全局唯一文件名
        String filePath = "";
        try {
            String uploadFilename = MD5Util.filenameByMD5(file.getInputStream()) + "." + extName;
            filePath = aliyunOSSUtil.uploadFile(file.getInputStream(), uploadFilename);
            log.error("文件上传成功，ID为{}的用户上传了文件：{}", user.getId(), filePath);
        } catch (IOException e) {
            log.error("文件上传异常：{}", e.getMessage());
            ThrowException.custom(ResponseStatusEnum.FILE_UPLOAD_ERROR);
        }
        // 上传成功+1
        redis.increment(RedisKeyUtil.getUploadCount(id), 1);
        return filePath;
    }
}
