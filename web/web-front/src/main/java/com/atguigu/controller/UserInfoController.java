package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpSession httpSession){
        //设置验证码,随机生成验证码,这里不使用redis来进行存储了
        String code = "8888";
        //将验证码放到session域里面
        httpSession.setAttribute("code",code);
        //将验证码响应到前端
        return Result.ok(code);
    }

    //注册
    @RequestMapping("/register")
    public Result register(@RequestBody  RegisterVo registerVo,HttpSession session){
        //获取手机号、密码、昵称和验证码
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        //验空,使用commons-lang3的工具类
        if (StringUtils.isAnyBlank(phone,password,nickName,code)){
            //返回参数错误的信息
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //从Session域里面获取验证码
        String sessionCode = (String) session.getAttribute("code");
        //判断验证码是否正确
        if (!code.equals(sessionCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        //调用userInfoService中的方法判断该手机号是否已经注册
        UserInfo userInfo =userInfoService.getUserInfoByPhone(phone);
        if (userInfo != null){
            //返回手机号已注册的消息
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //创建一个userInfo对象，然后插入到数据库中
        UserInfo userInfo1 = new UserInfo();
        //将数据插入到数据库里面
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setPhone(phone);
        userInfo1.setStatus(1);
        //调用userInfoService中插入的方法
        userInfoService.insert(userInfo1);
        return Result.ok();
    }

    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpSession session){
        //获取手机号
        String phone = loginVo.getPhone();
        //获取密码
        String password = loginVo.getPassword();
        //判断空，返回对应的值给前端
        if(StringUtils.isAnyBlank(phone,password)){
            //返回给前端参数错误
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        //调用service中根据phone来查询userInfo
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        //校验用户
        if (userInfo == null){
            //账号不正确
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码,使用MD5加密是一样的
        if (!MD5.encrypt(password).equals(userInfo.getPassword())){
            //密码不正确
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验用户是否被禁用
        if(userInfo.getStatus()==0){
            //用户被锁定
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //登陆成功
        //把用户信息放到session域，目的是为了给后台判断用户是否登陆的状态，跟前台是分开的
        session.setAttribute("user",userInfo);

        //创建Map,Map必须存放nickName的key，值是用户的昵称
        Map map =new HashMap<>();
        map.put("phone",userInfo.getPhone()); //不是必要的
        map.put("nickName",userInfo.getNickName());
        //返回结果
        return Result.ok(map);
    }

    //退出登陆
    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        //将session域中的userInfo对象移除
        session.removeAttribute("user");
        return Result.ok();
    }




}
