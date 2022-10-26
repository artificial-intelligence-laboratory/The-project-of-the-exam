package com.gcc.fns.controller;

import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.pojo.dto.AppUserInfoDto;
import com.gcc.fns.pojo.vo.AppUserInfoVo;
import com.gcc.fns.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public GraceJSONResult updateUserInfo(@RequestBody @Valid AppUserInfoDto appUserInfoDto,
                                          HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        AppUserInfoVo appUserInfoVo = appUserService.updateUserInfo(appUserInfoDto, token);
        return GraceJSONResult.ok(appUserInfoVo);
    }
}
