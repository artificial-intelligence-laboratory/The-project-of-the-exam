package com.gcc.fns.controller;

import cn.hutool.core.bean.BeanUtil;
import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.pojo.AppUser;
import com.gcc.fns.pojo.dto.AppUserInfoDto;
import com.gcc.fns.pojo.vo.AppUserInfoVo;
import com.gcc.fns.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/25 18:44
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AppUserService appUserService;

    @GetMapping("/getUserInfo")
    public GraceJSONResult getUserInfo() {
        return GraceJSONResult.ok(appUserService.getUserInfo());
    }

    @PostMapping("/updateUserInfo")
    public GraceJSONResult updateUserInfo(@RequestBody @Valid AppUserInfoDto appUserInfoDto) {
        AppUser appUser = appUserService.updateUserInfo(appUserInfoDto);
        AppUserInfoVo appUserInfoVo = BeanUtil.copyProperties(appUser, AppUserInfoVo.class);
        return GraceJSONResult.ok(appUserInfoVo);
    }
}
