package com.gcc.fns.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.fns.pojo.AppUser;
import com.gcc.fns.pojo.dto.AppUserInfoDto;
import com.gcc.fns.pojo.vo.AppUserInfoVo;

public interface AppUserService extends IService<AppUser> {

    /**
     * 验证码登录
     */
    String loginForCode(String email, String code);

    /**
     * 账号密码登录
     * @param email
     * @param password
     * @return
     */
    String loginForPwd(String email, String password);

    /**
     * 获取用户基本信息
     * @return
     */
    AppUserInfoVo getUserInfo();

    AppUserInfoVo updateUserInfo(AppUserInfoDto appUserInfoDto, String token);
}
