package com.gcc.fns.common.constant;

/**
 * @author xiaozhi
 * @description redis的通用常量
 * @create 2022-10-2022/10/25 10:04
 */
public class RedisConstant {

    public final static String SPLIT = ":";

    /**
     * 用户token
     */
    public final static String USER_TOKEN_KEY = "user:token";

    /**
     * 登录验证码
     */
    public final static String LOGIN_CODE_KEY = "login:code";

    /**
     * 登录用户
     */
    public final static String LOGIN_USER_KEY = "login:user";

    /**
     * 登录IP
     */
    public final static String LOGIN_IP_KEY = "login:ip";

    /**
     * 过期时间 (7天)
     */
    public final static Integer EXPIRATION_SEVEN_DAY = 60 * 60 * 24 * 7;

    /**
     * 过期时间 (60s)
     */
    public final static Integer EXPIRATION_SIXTY_SECONDS = 60;

    /**
     * 过期时间（30分钟）
     */
    public final static Integer EXPIRATION_THIRTY_MINUTES = 30 * 60;

}
