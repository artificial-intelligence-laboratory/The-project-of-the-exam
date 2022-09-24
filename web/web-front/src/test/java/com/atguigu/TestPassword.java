package com.atguigu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 1bbd886460827015e5d605ed44252251
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/spring-mvc.xml")
public class TestPassword {

    @Reference
    private UserInfoService userInfoService;

    @Test
    public void test1(){


    }
}
