package com.gcc.fns.common.utils;

import com.gcc.fns.common.constant.RedisConstant;

/**
 * @author xiaozhi
 * @description 获取rediskey工具类
 * @create 2022-10-2022/10/25 10:40
 */
public class RedisKeyUtil extends RedisConstant {

    public static String getLoginCodeKey(String email) {
        return LOGIN_CODE_KEY + SPLIT + email;
    }

    public static String getLoginIpKey(String ip) {
        return LOGIN_IP_KEY + SPLIT + ip;
    }

    public static String getUserTokenKey(String token) {
        return LOGIN_USER_KEY + SPLIT + token;
    }
}
