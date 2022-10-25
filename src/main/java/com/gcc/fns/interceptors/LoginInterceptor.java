package com.gcc.fns.interceptors;

import com.gcc.fns.common.constant.RedisConstant;
import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.exception.ThrowException;
import com.gcc.fns.common.utils.JsonUtils;
import com.gcc.fns.common.utils.RedisKeyUtil;
import com.gcc.fns.common.utils.RedisOperator;
import com.gcc.fns.common.utils.UserHolder;
import com.gcc.fns.pojo.vo.AppUserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 0:28
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redis;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 获取请求头中的Token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            ThrowException.custom(ResponseStatusEnum.UN_LOGIN);
        }
        // redis中不存在表示已过期，重新登录
        String userTokenKey = RedisKeyUtil.getUserTokenKey(token);
        String jsonUser = redis.get(userTokenKey);
        if (StringUtils.isBlank(jsonUser)) {
            ThrowException.custom(ResponseStatusEnum.TICKET_INVALID);
        }
        AppUserInfoVo appUserInfoVo = JsonUtils.jsonToPojo(jsonUser, AppUserInfoVo.class);
        // 保存到ThreadLocal
        UserHolder.saveUser(appUserInfoVo);
        // token还有30分钟过期就进行刷新
        if (redis.ttl(userTokenKey) <= RedisConstant.EXPIRATION_THIRTY_MINUTES) {
            // 刷新令牌
            redis.expire(userTokenKey, RedisConstant.EXPIRATION_SEVEN_DAY);
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
