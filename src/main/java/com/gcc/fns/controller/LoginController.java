package com.gcc.fns.controller;

import cn.hutool.core.util.RandomUtil;
import com.gcc.fns.common.constant.RedisConstant;
import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.utils.*;
import com.gcc.fns.model.dto.CodeLoginRequest;
import com.gcc.fns.model.dto.PwdLoginRequest;
import com.gcc.fns.model.dto.SendMailRequest;
import com.gcc.fns.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 23:21
 */
@Api(value = "登录接口", tags = "登录接口")
@RestController()
@RequestMapping("/passport")
@Slf4j
public class LoginController extends BaseController{

    @Resource
    private SendMailUtil sendMailUtil;

    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "发送验证码接口", notes = "传入邮箱账号发送验证码")
    @PostMapping("/sendCode")
    public GraceJSONResult sendCode(@RequestBody @Valid SendMailRequest sendMailRequest,
                                    HttpServletRequest request) {
        // 获取IP
        String ip = IpUtil.getIpAddress(request);
        // 限制同一个IP60秒内只能发送一次
        if (redis.keyIsExist(RedisKeyUtil.getLoginIpKey(ip))) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.MAIL_NEED_WAIT_ERROR);
        }
        // 设置ip过期时间为60s
        redis.set(RedisKeyUtil.getLoginIpKey(ip), ip, RedisConstant.EXPIRATION_SIXTY_SECONDS);
        String email = sendMailRequest.getEmail();
        String code = RandomUtil.randomNumbers(6);
        // 发送邮件
        sendMailUtil.sendCodeToMail(email, code);
        // 存入到数据库中，30分钟有效
        redis.set(RedisKeyUtil.getLoginCodeKey(email), code, RedisConstant.EXPIRATION_THIRTY_MINUTES);
        return GraceJSONResult.ok();
    }

    @ApiOperation(value = "验证码登录接口", notes = "邮箱和验证码的方式登录，登录成功返回token")
    @PostMapping("/codeLogin")
    public GraceJSONResult codeLogin(@RequestBody @Valid CodeLoginRequest codeLoginRequest) {
        String email = codeLoginRequest.getEmail();
        String code = codeLoginRequest.getCode();
        Map<String, Object> map = appUserService.loginForCode(email, code);
        return GraceJSONResult.ok(map);
    }

    @ApiOperation(value = "验证码登录接口", notes = "邮箱和密码的方式登录，登录成功返回token")
    @PostMapping("/pwdLogin")
    public GraceJSONResult pwdLogin(@RequestBody @Valid PwdLoginRequest pwdLoginRequest) {
        String email = pwdLoginRequest.getEmail();
        String password = pwdLoginRequest.getPassword();
        Map<String, Object> map = appUserService.loginForPwd(email, password);
        return GraceJSONResult.ok(map);
    }

    @ApiOperation(value = "退出登录接口", notes = "退出登录，删除token")
    @GetMapping("/logout")
    public GraceJSONResult logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userTokenKey = RedisKeyUtil.getUserTokenKey(token);
        // 删除缓存session
        redis.del(userTokenKey);
        return GraceJSONResult.ok();
    }
}
