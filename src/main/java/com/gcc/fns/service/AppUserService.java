package com.gcc.fns.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.fns.model.dto.AppUserInfoDto;
import com.gcc.fns.model.entity.AppUser;
import com.gcc.fns.model.vo.AppUserInfoVo;

import java.util.Map;

public interface AppUserService extends IService<AppUser> {

    /**
     * 验证码登录
     */
    Map<String, Object> loginForCode(String email, String code);

    /**
     * 账号密码登录
     * @param email
     * @param password
     * @return
     */
    Map<String, Object> loginForPwd(String email, String password);

    /**
     * 获取用户基本信息
     * @return
     */
    AppUserInfoVo getUserInfo();

    AppUserInfoVo updateUserInfo(AppUserInfoDto appUserInfoDto, String token);
}
