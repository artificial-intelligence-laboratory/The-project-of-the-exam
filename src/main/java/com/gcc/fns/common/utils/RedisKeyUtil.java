package com.gcc.fns.common.utils;

import com.gcc.fns.common.constant.RedisConstant;

/**
 * @author xiaozhi
 * @description 获取rediskey工具类
 * @create 2022-10-2022/10/25 10:40
 */
public class RedisKeyUtil extends RedisConstant {

    /**
     * 获取登录验证码key
     */
    public static String getLoginCodeKey(String email) {
        return LOGIN_CODE_KEY + SPLIT + email;
    }

    /**
     * 获取登录IPkey
     */
    public static String getLoginIpKey(String ip) {
        return LOGIN_IP_KEY + SPLIT + ip;
    }

    /**
     * token获取用户key
     */
    public static String getUserTokenKey(String token) {
        return LOGIN_USER_KEY + SPLIT + token;
    }

    /**
     * 上传次数key
     */
    public static String getUploadCount(Long userId) {
        return UPLOAD_COUNT_KEY + SPLIT + userId;
    }
}
