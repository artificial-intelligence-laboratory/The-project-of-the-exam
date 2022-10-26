package com.gcc.fns.common.utils.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author xiaozhi
 * @description 阿里云OSS工具类
 * @create 2022-10-2022/10/26 8:14
 */
@Component
public class AliyunOSSUtil {

    private final String SLASH = "/";

    @Resource
    private AliyunSecretResource aliyunSecretResource;

    @Resource
    private AliyunOSSResource aliyunOSSResource;

    public OSS getOssClient() {
        String accessKeyId = aliyunSecretResource.getAccessKeyId();
        String accessKeySecret = aliyunSecretResource.getAccessKeySecret();
        String endpoint = aliyunOSSResource.getEndpoint();
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public String uploadFile(InputStream file, String filename) {
        String bucketName = aliyunOSSResource.getBucketName();
        String objectName = aliyunOSSResource.getObjectName();
        // 域名地址，oss返回的是一部分地址，需要加上域名的才是完整的url
        String ossHost = aliyunOSSResource.getOssHost();
        OSS ossClient = getOssClient();
        // 拼接文件路径
        objectName = objectName + SLASH + filename;
        // 完整路径
        String filePath = ossHost + SLASH + objectName;
        ossClient.putObject(bucketName, objectName, file);
        // 关闭流
        ossClient.shutdown();
        return filePath;
    }

}
