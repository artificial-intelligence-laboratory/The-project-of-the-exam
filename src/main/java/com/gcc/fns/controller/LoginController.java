package com.gcc.fns.controller;

import cn.hutool.core.util.RandomUtil;
import com.gcc.fns.common.constant.RedisConstant;
import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.utils.GraceJSONResult;
import com.gcc.fns.common.utils.IpUtil;
import com.gcc.fns.common.utils.RedisKeyUtil;
import com.gcc.fns.common.utils.SendMailUtil;
import com.gcc.fns.model.dto.CodeLoginRequest;
import com.gcc.fns.model.dto.PwdLoginRequest;
import com.gcc.fns.model.dto.SendMailRequest;
import com.gcc.fns.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 23:21
 */
@RestController()
@RequestMapping("/passport")
@Slf4j
public class LoginController extends BaseController{

    @Resource
    private SendMailUtil sendMailUtil;

    @Autowired
    private AppUserService appUserService;

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


    @PostMapping("/codeLogin")
    public GraceJSONResult codeLogin(@RequestBody @Valid CodeLoginRequest codeLoginRequest) {
        String email = codeLoginRequest.getEmail();
        String code = codeLoginRequest.getCode();
        // 生成token并写入到数据库中
        String token = appUserService.loginForCode(email, code);
        return GraceJSONResult.ok(token);
    }

    @PostMapping("/pwdLogin")
    public GraceJSONResult pwdLogin(@RequestBody @Valid PwdLoginRequest pwdLoginRequest) {
        String email = pwdLoginRequest.getEmail();
        String password = pwdLoginRequest.getPassword();
        String token = appUserService.loginForPwd(email, password);
        return GraceJSONResult.ok(token);
    }
}
