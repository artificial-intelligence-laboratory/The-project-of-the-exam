package com.gcc.fns.controller;

import com.gcc.fns.common.utils.RedisOperator;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 基础Controller
 * @create 2022-10-2022/10/25 10:57
 */
public class BaseController {

    @Resource
    public RedisOperator redis;
}
