package com.gcc.fns.interceptors;

import com.gcc.fns.common.utils.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/26 1:15
 */
@Component
public class VisitLoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisOperator redis;

    @Resource
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        Long userId = jwtUtil.getUserId(token);
        boolean keyIsExist = redis.keyIsExist(RedisKeyUtil.getUserTokenKey(token));
        if (keyIsExist) {
            String json = JsonUtils.objectToJson(GraceJSONResult.ok());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}
