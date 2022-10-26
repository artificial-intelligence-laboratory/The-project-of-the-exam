package com.gcc.fns.controller;

import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.model.dto.AppUserInfoDto;
import com.gcc.fns.model.vo.AppUserInfoVo;
import com.gcc.fns.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/25 18:44
 */
@Api(value = "用户服务接口", tags = "用户服务接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AppUserService appUserService;

    @ApiOperation(value = "获取用户基本信息接口", notes = "获取用户基本信")
    @GetMapping("/getUserInfo")
    public GraceJSONResult getUserInfo() {
        return GraceJSONResult.ok(appUserService.getUserInfo());
    }

    @ApiOperation(value = "更新用户基本信息接口", notes = "更新用户基本信息")
    @PostMapping("/updateUserInfo")
    public GraceJSONResult updateUserInfo(@RequestBody @Valid AppUserInfoDto appUserInfoDto,
                                          HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        AppUserInfoVo appUserInfoVo = appUserService.updateUserInfo(appUserInfoDto, token);
        return GraceJSONResult.ok(appUserInfoVo);
    }
}
