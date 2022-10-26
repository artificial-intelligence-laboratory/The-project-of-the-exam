package com.gcc.fns.common.utils.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xiaozhi
 * @description 阿里云配置资源
 * @create 2022-10-2022/10/26 8:22
 */
@PropertySource("classpath:aliyun-secret.properties")
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunSecretResource {

    private String accessKeyId;

    private String accessKeySecret;
}
