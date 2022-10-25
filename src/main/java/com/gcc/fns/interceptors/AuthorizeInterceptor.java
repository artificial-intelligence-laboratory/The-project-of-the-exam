package com.gcc.fns.interceptors;

import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.enums.UserStatus;
import com.gcc.fns.common.exception.ThrowException;
import com.gcc.fns.common.utils.UserHolder;
import com.gcc.fns.pojo.vo.AppUserInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaozhi
 * @description 认证拦截器 -> 补全用户信息才能执行下单操作
 * @create 2022-10-2022/10/25 18:03
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppUserInfoVo appUser = UserHolder.getUser();
        if (appUser.getStatus() == UserStatus.INACTIVE.type) {
            ThrowException.custom(ResponseStatusEnum.USER_INACTIVE_ERROR);
        } else if (appUser.getStatus() == UserStatus.FROZEN.type) {
            ThrowException.custom(ResponseStatusEnum.USER_FROZEN_ERROR);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
