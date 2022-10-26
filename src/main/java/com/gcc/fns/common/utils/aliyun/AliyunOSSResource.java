package com.gcc.fns.common.utils.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 * @description 阿里云OSS配置
 * @create 2022-10-2022/10/26 8:25
 */
@PropertySource("classpath:file.properties")
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
@Data
public class AliyunOSSResource {

    /**
     * 端点
     */
    private String endpoint;

    /**
     * bucket名
     */
    private String bucketName;

    /**
     * 对象名，存储在那个目录下
     */
    private String objectName;

    /**
     * oss域名
     */
    private String ossHost;

}
