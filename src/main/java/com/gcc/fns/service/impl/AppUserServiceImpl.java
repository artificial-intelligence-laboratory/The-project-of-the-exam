package com.gcc.fns.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcc.fns.common.constant.RedisConstant;
import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.enums.UserStatus;
import com.gcc.fns.common.exception.ThrowException;
import com.gcc.fns.common.utils.*;
import com.gcc.fns.mapper.AppUserMapper;
import com.gcc.fns.model.dto.AppUserInfoDto;
import com.gcc.fns.model.entity.AppUser;
import com.gcc.fns.model.vo.AppUserInfoVo;
import com.gcc.fns.service.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 0:29
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    @Resource
    private AppUserMapper appUserMapper;

    @Resource
    private JWTUtil jwtUtil;

    @Resource
    private RedisOperator redis;

    @Transactional
    @Override
    public Map<String, Object> loginForCode(String email, String code) {
        if (StringUtils.isAnyBlank(email, code)) {
            ThrowException.custom(ResponseStatusEnum.MAIL_CODE_NOT_EMPTY);
        }
        // 缓存中获取code
        String cacheCode = redis.get(RedisKeyUtil.getLoginCodeKey(email));
        if (cacheCode == null) {
            ThrowException.custom(ResponseStatusEnum.MAIL_CODE_EXPIRE);
        }
        if (!code.equalsIgnoreCase(cacheCode)) {
            ThrowException.custom(ResponseStatusEnum.MAIL_CODE_ERROR);
        }
        // 获取用户
        AppUser appUser = getUser(email);
        // 不存在就创建
        if (appUser == null) {
            appUser = new AppUser();
            appUser.setEmail(email);
            appUser.setCreateTime(new Date());
            appUser.setUpdateTime(new Date());
            // 插入数据库
            appUserMapper.insert(appUser);
        }
        return getTokenAndUserInfo(appUser);
    }

    @Transactional
    @Override
    public Map<String, Object> loginForPwd(String email, String password) {
        if (StringUtils.isAnyBlank(email, password)) {
            ThrowException.custom(ResponseStatusEnum.USER_NOT_NULL);
        }
        // 根据email查询用户
        AppUser appUser = getUser(email);
        // 不存在用户
        if (appUser == null) {
            ThrowException.custom(ResponseStatusEnum.USER_NOT_EXIST);
        }
        // 用户密码功能未开启
        if (StringUtils.isBlank(appUser.getPassword())) {
            ThrowException.custom(ResponseStatusEnum.USER_NOT_PASSWORD);
        }
        // 密码加密
        password = MD5Util.encodeByMD5(appUser.getSalt(), password);
        // 账号或密码不一致
        if (!StringUtils.equals(email, appUser.getEmail()) || !StringUtils.equals(password, appUser.getPassword())) {
            ThrowException.custom(ResponseStatusEnum.USER_LOGIN_PWD_ERROR);
        }
        return getTokenAndUserInfo(appUser);
    }

    /**
     * 获取token和用户信息
     */
    private Map<String, Object> getTokenAndUserInfo(AppUser appUser) {
        Map<String, Object> map = new HashMap<>();
        String token = getToken(appUser);
        AppUserInfoVo appUserInfoVo = BeanUtil.copyProperties(appUser, AppUserInfoVo.class);
        map.put("token", token);
        map.put("user", appUserInfoVo);
        return map;
    }

    private String getToken(AppUser user) {
        AppUserInfoVo appUserInfoVo = BeanUtil.copyProperties(user, AppUserInfoVo.class);
        // 生成token
        String token = jwtUtil.createToken(user.getId(), user.getEmail());
        // 放入redis中，7天
        redis.set(RedisKeyUtil.getUserTokenKey(token),
                JsonUtils.objectToJson(appUserInfoVo), RedisConstant.EXPIRATION_SEVEN_DAY);
        return token;
    }

    public AppUser getUser(String email) {
        QueryWrapper<AppUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return appUserMapper.selectOne(wrapper);
    }

    @Override
    public AppUserInfoVo getUserInfo() {
        return UserHolder.getUser();
    }

    @Override
    public AppUserInfoVo updateUserInfo(AppUserInfoDto appUserInfoDto, String token) {
        if (appUserInfoDto == null) {
            ThrowException.custom(ResponseStatusEnum.USER_UPLOAD_NULL_ERROR);
        }
        AppUser appUser = BeanUtil.copyProperties(appUserInfoDto, AppUser.class);
        appUser.setStatus(UserStatus.ACTIVE.type);
        AppUserInfoVo appUserInfoVo = UserHolder.getUser();
        appUser.setId(appUserInfoVo.getId());
        // 更新到数据库
        appUserMapper.updateById(appUser);
        BeanUtil.copyProperties(appUser, appUserInfoVo);
        redis.set(RedisKeyUtil.getUserTokenKey(token), JsonUtils.objectToJson(appUserInfoVo),
                RedisConstant.EXPIRATION_SEVEN_DAY);
        return appUserInfoVo;
    }
}
