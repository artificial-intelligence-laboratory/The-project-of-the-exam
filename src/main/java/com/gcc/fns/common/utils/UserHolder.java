package com.gcc.fns.common.utils;


import com.gcc.fns.pojo.vo.AppUserInfoVo;

public class UserHolder {
    private static final ThreadLocal<AppUserInfoVo> tl = new ThreadLocal<>();

    public static void saveUser(AppUserInfoVo user){
        tl.set(user);
    }

    public static AppUserInfoVo getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
