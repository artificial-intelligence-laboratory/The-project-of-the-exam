package com.gcc.fns.common.utils;

import com.gcc.fns.service.impl.WebSocket;
import com.gcc.fns.model.entity.AppUser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author snail
 * @create 2022-10-30  16:06
 */
public class SessionUtil {

    //   public static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    public static Map<Long, WebSocket> webSockets = new ConcurrentHashMap<Long, WebSocket>();
    // list 里面第一个存sessionId，第二个存session
    public static Map<Long, List<Object>> sessionPool = new ConcurrentHashMap<>();
    // 当前登录用户x
    public static Map<String, AppUser> curUserPool = new ConcurrentHashMap<>();
}
